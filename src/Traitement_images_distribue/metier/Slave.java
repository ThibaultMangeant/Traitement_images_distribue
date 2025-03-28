import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Slave implements Runnable
{
	private Socket socket;

	public Slave(Socket socket)
	{
		this.socket = socket;
	}

	public Socket getSocket()
	{
		return this.socket;
	}

	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

				byte[] imageBytes = (byte[]) in.readObject(); // Réception du tableau d'octets
				ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
				BufferedImage image = ImageIO.read(bais); // Conversion en BufferedImage
				bais.close();

				String traitement = Math.random() < 0.5 ? "Permutation" : "Inversion";
				System.out.println("Traitement de l'image par l'esclave: " + traitement);

				BufferedImage processedImage = traiterImage(image, traitement);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(processedImage, "png", baos);
				baos.flush();
				byte[] processedImageBytes = baos.toByteArray();
				baos.close();

				out.writeObject(processedImageBytes); // Envoi du tableau d'octets
				out.flush();

				Thread.sleep(1000);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public BufferedImage traiterImage(BufferedImage image, String traitement)
	{

		switch (traitement)
		{
		case "Inversion" -> this.invertColors(image);
		case "Permutation" -> this.permuteRedBlueGreen(image);
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

	public static void main(String[] args)
	{

		if ( args.length < 2 )
		{
			System.out.println("Syntaxe invalide : java Slave <adresse> <port>");
		}
		else
		{
			int port  = Integer.parseInt(args[1]);
			String ip = args[0];

			try (Socket socket = new Socket(ip, port))
			{
				Slave slave = new Slave(socket);
				slave.run();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		
	}
}
