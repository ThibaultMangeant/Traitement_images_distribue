package Traitement_images_distribue;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Traitement_images_distribue.ihm.FrameGrille;
import Traitement_images_distribue.metier.TraitementImage;

public class Controleur
{
	BufferedImage[] images;

	FrameGrille frame;

	public Controleur()
	{
		frame = new FrameGrille(this);
	}

	private BufferedImage selectImage()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			try
			{ 
				BufferedImage originalImage = ImageIO.read(selectedFile);
				BufferedImage resizedImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(originalImage, 0, 0, 800, 800, null);
				g.dispose();
				return resizedImage;
			}
			catch (IOException e) { e.printStackTrace(); }
		}

		return null;
	}

	public void chooseImage(int rows, int cols)
	{
		this.images = new BufferedImage[rows * cols];
		BufferedImage image = selectImage();

		while (image == null)
			System.exit(0);

		this.images = TraitementImage.decouperImage(image, rows, cols);
	}
	public BufferedImage[] getImages()
	{
		return this.images;
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
