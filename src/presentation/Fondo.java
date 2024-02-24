package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * JPanel con una imágen de fondo.
 * @author Jefer Gonzalez
 * @version 2.0 (10/12/2022)
 */
class Fondo extends JPanel {
	
	String nombreImagen;
	boolean escalar;
	
	/**
	 * Constructor para objetos de clase Fondo.
	 * @param nombreImagen Nombre de la igagen que está en la carpeta imagenes.
	 * @param escalar True si se desea escalar la imagen de fondo.
	 */
	public Fondo(String nombreImagen, boolean escalar) {
		this.nombreImagen = nombreImagen;
		this.escalar = escalar;
	}
	
	@Override
	public void paint(Graphics g) {
		Image fondo = new ImageIcon(getClass().getResource("Imagenes/"+nombreImagen+".png")).getImage();
		if (escalar) fondo = new ImageIcon(fondo.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)).getImage();
		g.drawImage(fondo, 0, 0, this);
		setOpaque(false);
		super.paint(g);
	}
}