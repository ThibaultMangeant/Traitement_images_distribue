package Traitement_images_distribue.metier;

import java.awt.image.BufferedImage;

public class Slave
{

	private TraitementImage traitementImage;

	public Slave(BufferedImage image)
	{
	}

	public BufferedImage traiterImage(BufferedImage image, String traitement)
	{
		return traitementImage.traiterImage(image, traitement);
	}


}
