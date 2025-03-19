package Traitement_images_distribue.metier;

import Traitement_images_distribue.Controleur;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Master
{
	private Controleur ctrl;
	private List<Slave> slavesDispo;

	private Socket connexion;
	private ServerSocket server;

	private BufferedImage[] images;
	private BufferedImage imageTraite;

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
				System.out.println("En attente de connexion");

				this.connexion = this.server.accept();

				System.out.println("Nouvelle connexion");

				Slave slave = new Slave(this.connexion);

				System.out.println("Nouvel esclave connecté");

				slavesDispo.add(slave);
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
			for (BufferedImage image : images)
			{
				if (slavesDispo.isEmpty())
				{
					System.out.println("Aucun esclave disponible");
					this.imageTraite = null;
					return;
				}

				Slave slave = slavesDispo.remove(0);
				Thread slaveThread = new Thread(() -> {
					try
					{
						Socket slaveSocket = slave.getSocket();
						ObjectOutputStream out = new ObjectOutputStream(slaveSocket.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(slaveSocket.getInputStream());

						out.writeObject(image);

						BufferedImage processedImage = (BufferedImage) in.readObject();
						synchronized (this)
						{
							this.imageTraite = processedImage;
						}

						slavesDispo.add(slave);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				});
				slaveThread.start();
				slaveThread.join();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public BufferedImage getImageTraite()
	{
		return this.imageTraite;
	}
}
