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

			new Thread(() -> runAccept()).start();

			this.traiterImage();


			
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
				{
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
					Thread.sleep(1000);
					continue;
				}
	
				int index = (int) (Math.random() * images.length);
				BufferedImage image = images[index];
	
				Slave slave = slavesDispo.remove(0);
	
				new Thread(() ->
				{
					try
					{
						Socket slaveSocket = slave.getSocket();
						ObjectOutputStream out = new ObjectOutputStream(slaveSocket.getOutputStream());
						ObjectInputStream in   = new ObjectInputStream(slaveSocket.getInputStream());
	
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(image, "png", baos);
						baos.flush();
						byte[] imageBytes = baos.toByteArray();
						baos.close();
	
						out.writeObject(imageBytes);
						out.flush();
	
						byte[] processedImageBytes = (byte[]) in.readObject();
	
						ByteArrayInputStream bais = new ByteArrayInputStream(processedImageBytes);
						BufferedImage processedImage = ImageIO.read(bais);
						bais.close();
	
						synchronized (this)
						{
							images[index] = processedImage;
							ctrl.changerImage(images);
						}
	
						synchronized (slavesDispo)
						{
							slavesDispo.add(slave);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}).start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
