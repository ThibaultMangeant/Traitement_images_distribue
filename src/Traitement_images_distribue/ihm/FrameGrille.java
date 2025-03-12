package Traitement_images_distribue.ihm;

import Traitement_images_distribue.Controleur;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;

public class FrameGrille extends JFrame
{
	private Controleur ctrl;
	private PanelGrille panel;

	public FrameGrille(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setTitle("Grille d'images");
		this.setSize(850, 850);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panel = new PanelGrille(this);

		this.add(panel);
		this.setVisible(true);
	}

	public BufferedImage[] getImages()
	{
		return ctrl.getImages();
	}
}
