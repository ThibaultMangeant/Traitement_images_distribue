package Traitement_images_distribue.metier;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

	public static void main(String[] args)
	{
		
		try
		{
		} catch (Exception e) {
			System.out.println(e);
		}
	}


}
