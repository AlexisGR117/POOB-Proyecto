package presentation;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.*;

/**
 * Interfaz gráfica de DAPOOS.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class DAPOOSGUI extends JFrame{
	
	JButton nuevo, abrir, opciones, salir, ayuda;
	
	/*
	 * Constructor para objetos de calse DAPOOSGUI.
	 */
	private DAPOOSGUI() {
		super("Inicio daPOOs");
		prepararElementos();
		prepararAcciones();
	}
	
    /*
     * Prepara los elementos con los que el usuario interactua.
     */
    private void prepararElementos() {   
    	Fondo fondo = new Fondo("Inicio", false);
    	setSize(1050, 715);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        int x = (int)((double)getWidth()/(double)2)-140;
        int y = (int)(getHeight()*0.4);
        Font fuente = new Font("Perpetua", 0, 30);
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        LineBorder borde = new LineBorder(Color.black, 2);
        fondo.setLayout(null);
        nuevo = new JButton("Nuevo Juego");
        nuevo.setBounds(x, y, 260, 45);
        nuevo.setCursor(cursor);
        nuevo.setBackground(Color.white);
        nuevo.setBorder(borde);
        nuevo.setFont(fuente);
        abrir = new JButton("Abrir Juego");
        abrir.setBounds(x, y+55, 260, 45);
        abrir.setCursor(cursor);
        abrir.setBackground(Color.white);
        abrir.setBorder(borde);
        abrir.setFont(fuente);
        opciones = new JButton("Opciones");
        opciones.setBounds(x, y+110, 260, 45);
        opciones.setCursor(cursor);
        opciones.setBackground(Color.white);
        opciones.setBorder(borde);
        opciones.setFont(fuente);
        salir = new JButton("Salir");
        salir.setBounds(x, y+165, 260, 45);
        salir.setCursor(cursor);
        salir.setBackground(Color.white);
        salir.setBorder(borde);
        salir.setFont(fuente);
        ayuda = new JButton("Ayuda");
        ayuda.setBounds(x, y+220, 260, 45);
        ayuda.setCursor(cursor);
        ayuda.setBackground(Color.white);
        ayuda.setBorder(borde);
        ayuda.setFont(fuente);
        fondo.add(nuevo);
        fondo.add(abrir);
        fondo.add(salir);
        fondo.add(opciones);
        fondo.add(ayuda);  
        prepararPuntuaciones();
        add(fondo);
    } 
    
    /*
     * Prepara las puntuaciones.
     */
    private void prepararPuntuaciones() {   
    	Fondo fondo = new Fondo("FondoBlanco", true);
    	fondo.setBounds(55, (int)(getHeight()*0.4) - 45, 265, 350);
        fondo.setLayout(new GridLayout(11, 3));
        Font fuente = new Font("Perpetua", Font.BOLD, 25);
        LineBorder borde = new LineBorder(Color.black, 2);
        TitledBorder titulo = new TitledBorder(borde, "Mejores Puntuaciones");
        titulo.setTitleFont(fuente);
        fondo.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), titulo));
        fuente = new Font("Perpetua", 0, 20);
        JLabel texto;
		texto = new JLabel("Posición", SwingConstants.CENTER);
		texto.setFont(fuente);
		fondo.add(texto);
		texto = new JLabel("Nombre", SwingConstants.CENTER);
		texto.setFont(fuente);
		fondo.add(texto);
		texto = new JLabel("Puntaje", SwingConstants.CENTER);
		texto.setFont(fuente);
		fondo.add(texto);
        try {
	        FileReader fileReader = new FileReader("Puntuaciones.txt");
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();
			String[] puntuacion;
			int c = 1;
			while (line != null) {
				puntuacion = line.trim().split(" ");
				texto = new JLabel(c+"", SwingConstants.CENTER);
				texto.setFont(fuente);
				fondo.add(texto);
				if (puntuacion[0].length() > 8) texto = new JLabel(puntuacion[0].substring(0, 8)+"", SwingConstants.CENTER);
				else texto = new JLabel(puntuacion[0], SwingConstants.CENTER);
				texto.setFont(fuente);
				fondo.add(texto);
				texto = new JLabel(puntuacion[1]+"", SwingConstants.CENTER);
				texto.setFont(fuente);
				fondo.add(texto);
				line = reader.readLine();
				c++;
			}
			reader.close();	
		} catch (Exception e) {
			Log.record(e);
			JOptionPane.showMessageDialog(null,"Ha ocurrido un error inesperado.");
		} 
        add(fondo);
    } 
    
    
    /*
     * Prepara los acciones de DAPOOSGUI.
     */
    private void prepararAcciones(){   
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                accionSalir();
            }
        });
        nuevo.addActionListener(e -> accionNuevo());
        salir.addActionListener(e -> accionSalir());
        abrir.addActionListener(e -> accionAbrir());
        opciones.addActionListener(e -> accionOpciones());
        ayuda.addActionListener(e -> accionAyuda());
    } 
    
    /*
     * Salir de DAPOOSGUI.
     */
    private void accionSalir() {   
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir del juego?", "Salir del juego", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta == JOptionPane.OK_OPTION){
            setVisible(false);
            System.exit(0);
        }
    } 
    
    /*
     * Abre un nuevo juego de DAPOOS.
     */
    private void accionNuevo() {   
        new OpcionesJuego(this);
    } 
    
    /*
     * Abre un juego de dapoos desde un archivo.
     */
    private void accionAbrir() {   
    	try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".dat", "dat");
            chooser.setDialogTitle("Abrir AManufacturing");
            chooser.setFileFilter(filter);
            int open = chooser.showOpenDialog(this);
            if (open == JFileChooser.APPROVE_OPTION) {
            	DAPOOS dapoos = DAPOOS.abra(chooser.getSelectedFile());
            	new Juego(dapoos, this);
            }    		
		} catch (DAPOOSException e) { 
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (Exception e) {
			Log.record(e);
			JOptionPane.showMessageDialog(null,"Ha ocurrido un error inesperado.");
		}
    }
    
    /*
     * Abre las opciones de DAPOOS.
     */
    private void accionOpciones() {   
    	JOptionPane.showMessageDialog(null, "La funcionalidad de opciones está en construcción.");
    } 
    
    /*
     * Abre la yuda del juego DAPOOS.
     */
    private void accionAyuda() {   
    	new Ayuda(this);
    } 
    
	 /**
	 * Método principal de DAPOOS.
	 */
	public static void main(String[] args) {
		DAPOOSGUI gui = new DAPOOSGUI();
	    gui.setVisible(true);
	} 
}

