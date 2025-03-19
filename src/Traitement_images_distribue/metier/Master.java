package Traitement_images_distribue.metier;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Master
{
	private List<Slave> slavesDispo;


	public Master()
	{
		slavesDispo = new ArrayList<Slave>();

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

	public BufferedImage traiterImage(BufferedImage image)
	{
		if (slavesDispo.size() == 0)
		{
			System.out.println("Aucun esclave disponible");
			return null;
		}

		String traitement;
		if (Math.random() < 0.5)
			traitement = "Permutation";
		else
			traitement = "Inversion";

		Slave slave = slavesDispo.get((int) (Math.random() * slavesDispo.size()));
		BufferedImage imageTraite = null;
		try
		{
			imageTraite = slave.traiterImage(image, traitement);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return imageTraite;
	}


}