package Traitement_images_distribue.ihm;
import Traitement_images_distribue.Controleur;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;

public class FrameGrille extends JFrame
{
	private Controleur ctrl;

	public FrameGrille(Controleur ctrl)
	{
		this.ctrl = ctrl;
	}

	public BufferedImage[] getImages()
	{
		return ctrl.getImages();
	}
}
