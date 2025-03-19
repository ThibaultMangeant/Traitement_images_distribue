package Traitement_images_distribue.ihm;

import Traitement_images_distribue.Controleur;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;

public class FrameGrille extends JFrame
{
	private Controleur ctrl;
	private PanelGrille panel;

	private int rows;
	private int cols;
	public FrameGrille(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setTitle("Grille d'images");
		this.setSize(850, 850);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.setVisible(true);

		this.popup(ctrl);

		this.panel = new PanelGrille(this, rows, cols);

		this.add(panel);

		this.setVisible(true);
	}

	public BufferedImage[] getImages()
	{
		return ctrl.getImages();
	}

	public void popup(Controleur ctrl)
	{

		String rowsInput = JOptionPane.showInputDialog(this, "Entrez le nombre de lignes:",
			"Configuration de la grille", JOptionPane.INFORMATION_MESSAGE);
		String colsInput = JOptionPane.showInputDialog(this, "Entrez le nombre de colonnes:",
			"Configuration de la grille", JOptionPane.INFORMATION_MESSAGE);
		
		try
		{
			rows = Integer.parseInt(rowsInput);
			cols = Integer.parseInt(colsInput);

		} catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Entrée invalide.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			
			this.popup(ctrl);
			return;
		}

		this.ctrl.chooseImage(rows, cols);
		
	}
}
