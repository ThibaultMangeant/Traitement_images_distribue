package Traitement_images_distribue.ihm;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;



public class PanelGrille extends JPanel
{
	private FrameGrille frame;
	
	public PanelGrille(FrameGrille frame)
	{
		this.frame = frame;
		this.setLayout(new GridLayout(4, 8));
		BufferedImage[] images = new BufferedImage[32];

		images = frame.getImages();

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				JLabel labelTemp = new JLabel(new ImageIcon(images[i * 4 + j]));
				labelTemp.setSize(10, 10);
				this.add(labelTemp);
			}
		}

		this.setVisible(true);
	}
}
