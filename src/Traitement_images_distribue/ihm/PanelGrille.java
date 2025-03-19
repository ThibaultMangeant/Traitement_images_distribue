package Traitement_images_distribue.ihm;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;



public class PanelGrille extends JPanel
{
	private FrameGrille frame;
	private BufferedImage[] images;

	public PanelGrille(FrameGrille frame, int rows, int cols)
	{
		this.frame = frame;

		this.setLayout(new GridLayout(rows, cols));
		BufferedImage[] images = new BufferedImage[rows * cols];

		this.afficherImages(rows, cols);

		this.setVisible(true);
	}

	public void afficherImages(int rows, int cols)
	{
		images = frame.getImages();

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				JLabel labelTemp = new JLabel(new ImageIcon(images[i * cols + j]));
				labelTemp.setSize(10, 10);
				this.add(labelTemp);
			}
		}
	}
	
	public void updatePanel()
	{
		this.removeAll();
		this.repaint();
		this.revalidate();
	}

	
}
