package Traitement_images_distribue.metier;

import Traitement_images_distribue.Controleur;


import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Master extends Thread
{
	private Controleur ctrl;
	private List<Slave> slavesDispo;

	private BufferedImage[] images;
	private BufferedImage imageTraite;


	public Master(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.slavesDispo = new ArrayList<Slave>();
		this.images = this.ctrl.getImages();

		// Ajout des slaves

		
	}

	public void addSlave(Slave slave)
	{
		slavesDispo.add(slave);
	}

	public void removeSlave(Slave slave)
	{
		slavesDispo.remove(slave);
	}

	public void traiterImage()
	{
		String traitement;
		Slave slave;


		if (slavesDispo.size() == 0)
		{
			System.out.println("Aucun esclave disponible");
			this.imageTraite = null;
		}

		if (Math.random() < 0.5)
			traitement = "Permutation";
		else
			traitement = "Inversion";

		slave = slavesDispo.get((int) (Math.random() * slavesDispo.size()));
		imageTraite = null;
		try
		{
			for (int cptLig = 0 ; cptLig < images.length ; cptLig++)
			{
				this.imageTraite = slave.traiterImage(images[cptLig], traitement);
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


	public void run()
	{
		while (true)
		{
			this.traiterImage();
			this.ctrl.changerImage();
		}
	}
}
