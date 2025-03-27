package Traitement_images_distribue.metier;

import Traitement_images_distribue.Controleur;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Master
{
	private Controleur ctrl;
	private List<Slave> slavesDispo;

	private Socket connexion;
	private ServerSocket server;

	private BufferedImage[] images;

	public Master(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.slavesDispo = new ArrayList<Slave>();
		this.images = this.ctrl.getImages();
		
		System.out.println("Master initialisé");
		// Ajout des slaves au master
		try
		{
			this.server = new ServerSocket(4444);


			System.out.println("En attente de connexion");
			this.connexion = this.server.accept();

			System.out.println("Nouvelle connexion");
			Slave slave = new Slave(this.connexion);

			System.out.println("Nouvel esclave connecté");
			slavesDispo.add(slave);

			this.traiterImage();


			new Thread( () -> runAccept() ).start();
		}
		catch(IOException e)
		{
			System.out.println( e.getMessage());
		}
	}

	public void runAccept()
	{
		try
		{
			while (true)
			{
				System.out.println("En attente de connexion...");
				Socket slaveSocket = this.server.accept();
				System.out.println("Nouvelle connexion détectée");

				synchronized (slavesDispo)
				{ // Sécurisation pour éviter conflits de threads
					slavesDispo.add(new Slave(slaveSocket));
				}

				System.out.println("Esclave ajouté. Nombre d'esclaves actifs : " + slavesDispo.size());
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void traiterImage()
	{
		try
		{
			while (true)
			{
				if (slavesDispo.isEmpty())
				{
					System.out.println("Aucun esclave disponible, attente...");
					Thread.sleep(1000); // Attendre un moment avant de réessayer
					continue;
				}
	
				// Sélection aléatoire d'une image
				int index = (int) (Math.random() * images.length);
				BufferedImage image = images[index];
	
				// Sélectionner un esclave disponible
				Slave slave = slavesDispo.remove(0);
	
				// Exécuter le traitement dans un thread sans bloquer la boucle principale
				new Thread(() ->
				{
					try
					{
						Socket slaveSocket = slave.getSocket();
						ObjectOutputStream out = new ObjectOutputStream(slaveSocket.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(slaveSocket.getInputStream());
	
						// Convertir BufferedImage en byte[]
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(image, "png", baos);
						baos.flush();
						byte[] imageBytes = baos.toByteArray();
						baos.close();
	
						// Envoi du tableau d'octets au Slave
						out.writeObject(imageBytes);
						out.flush();
	
						// Réception de l'image traitée sous forme de tableau d'octets
						byte[] processedImageBytes = (byte[]) in.readObject();
	
						// Conversion du tableau d'octets en BufferedImage
						ByteArrayInputStream bais = new ByteArrayInputStream(processedImageBytes);
						BufferedImage processedImage = ImageIO.read(bais);
						bais.close();
	
						synchronized (this)
						{
							images[index] = processedImage;
							ctrl.changerImage(images);
						}
	
						// Remettre le Slave dans la liste des disponibles
						synchronized (slavesDispo)
						{
							slavesDispo.add(slave);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}).start(); // Lancer le thread sans join() pour permettre plusieurs connexions
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
