package metier;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TraitementImage
{
	public TraitementImage()
	{
	}

	public BufferedImage[] decouperImage(BufferedImage image)
	{
		BufferedImage[] images = new BufferedImage[32];
		int width = image.getWidth() / 4;
		int height = image.getHeight() / 8;
		int index = 0;
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				images[index] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = images[index].createGraphics();
				g.drawImage(image, 0, 0, width, height, width * x, height * y, width * x + width, height * y + height, null);
				g.dispose();
				index++;
			}
		}

		return images;
	}
	
	public BufferedImage traiterImage(BufferedImage image, String traitement)
	{

		switch (traitement)
		{
			case "Inversion":
				invertColors(image);
				break;
			case "Permutation":
				permuteRedBlueGreen(image);
				break;
			default:
				break;
		}

		return image;
	}

	private BufferedImage invertColors(BufferedImage image)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				int rgb = image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				red = 255 - red;
				blue = 255 - blue;
				green = 255 - green;
				int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
				image.setRGB(x, y, newRgb);
			}
		}

		return image;
	}

	private BufferedImage permuteRedBlueGreen(BufferedImage image)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				int rgb = image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				int newRgb = (alpha << 24) | (green << 16) | (blue << 8) | red;
				image.setRGB(x, y, newRgb);
			}
		}

		return image;
	}
}
