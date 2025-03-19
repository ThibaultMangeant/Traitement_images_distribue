package Traitement_images_distribue.metier;

import java.awt.image.BufferedImage;

public class Slave implements Runnable
{
	private TraitementImage traitementImage;
	private BufferedImage   image;
	private String          traitement;
	private BufferedImage   result;

	public Slave()
	{
		this.traitementImage = new TraitementImage();
	}

	public synchronized BufferedImage traiterImage(BufferedImage image, String traitement) throws InterruptedException
	{
		this.image      = image;
		this.traitement = traitement;
		this.result     = null;

		Thread thread = new Thread(this);
		thread.start();

		wait(); // Attend que le traitement soit terminé (attend le notify)

		return result;
	}

	@Override
	public synchronized void run()
	{
		BufferedImage processedImage = traitementImage.traiterImage(image, traitement);

		this.result = processedImage;
		notify(); // Notifie que le traitement est terminé
	}
}
