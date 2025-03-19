package Traitement_images_distribue.metier;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TraitementImage
{
	public TraitementImage()
	{

	}

	public static BufferedImage[] decouperImage(BufferedImage image, int rows, int cols)
	{
		BufferedImage[] images = new BufferedImage[rows * cols];

		int width  = image.getWidth () / cols;
		int height = image.getHeight() / rows;
		int index  = 0;

		for (int y = 0; y < rows; y++)
		{
			for (int x = 0; x < cols; x++)
			{
				images[index] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g  = images[index].createGraphics();

				g.drawImage
				(
					image,
					0,
					0,
					width,
					height,
					width  * x,
					height * y,
					width  * x + width,
					height * y + height,
					null
				);
				
				g.dispose();
				index++;
			}
		}

		return images;
	}

	
}
