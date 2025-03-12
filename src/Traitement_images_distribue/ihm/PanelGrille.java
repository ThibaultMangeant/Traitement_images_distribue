package Traitement_images_distribue.ihm;
import Traitement_images_distribue.ihm.FrameGrille;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;



public class PanelGrille extends JPanel
{
	private FrameGrille frame;
	
	public PanelGrille( FrameGrille frame)
	{
		this.frame = frame;
		this.setLayout(new GridLayout(4, 8));
		BufferedImage[] images = new BufferedImage[32];

		images = frame.getImages();

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				new JLabel(new ImageIcon(images[i * 8 + j]));
			}
			
		}
	}
}
