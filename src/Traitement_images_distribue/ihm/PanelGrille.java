package Traitement_images_distribue.ihm;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;



public class PanelGrille extends JPanel
{
	private FrameGrille frame;

	private int rows;
	private int cols;

	public PanelGrille(FrameGrille frame, int rows, int cols)
	{
		this.frame = frame;

		this.setLayout(new GridLayout(rows, cols));
		
		this.rows = rows;
		this.cols = cols;

		this.afficherImages();

		this.setVisible(true);
	}

	public void afficherImages()
	{
		BufferedImage[] images = new BufferedImage[this.rows * this.cols];
		images = frame.getImages();

		for (int i = 0; i < this.rows; i++)
		{
			for (int j = 0; j < this.cols; j++)
			{
				JLabel labelTemp = new JLabel(new ImageIcon(images[i * this.cols + j]));
				labelTemp.setSize(10, 10);
				this.add(labelTemp);
			}
		}
	}

	public void afficherImage(BufferedImage[] images)
	{
		this.removeAll();
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				JLabel labelTemp = new JLabel(new ImageIcon(images[i * cols + j]));
				labelTemp.setSize(10, 10);
				this.add(labelTemp);
			}
		}
		this.revalidate();
	}

	
}
