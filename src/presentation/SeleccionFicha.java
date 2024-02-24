package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import domain.DAPOOS;

/**
 * Cuadro de diálogo para seleccionar el tipo de ficha.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public class SeleccionFicha extends JDialog{
	
    private ArrayList<BotonFicha> botones = new ArrayList<BotonFicha>();
    private String seleccion, color;
    
    /**
     * Constructor para objetos de clase SeleccionFicha.
     * @param gui Juego en el que se va a cambiar el tipo.
     * @param color Color del jugador que va a seleccionar.
     */
    public SeleccionFicha(Juego gui, String color) {
        super(gui, Dialog. ModalityType.DOCUMENT_MODAL);
        this.color = color;
        prepararElementos();
        prepararAcciones();
        setVisible(true);
    } 
    
    /*
     * Prepara los elementos.
     */
    private void prepararElementos() {   
    	String[] fichasEspeciales = DAPOOS.FICHASESPECIALES;
        setSize(fichasEspeciales.length*150, 230);
        setTitle("Selección de ficha");
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,1));
        Fondo fondo = new Fondo("FondoBlanco", true);
        Border borde = new LineBorder(Color.black, 2);
        TitledBorder titulo = new TitledBorder(borde, "Selecciona la ficha");
        Font fuente = new Font("Perpetua", Font.BOLD, 35);
        titulo.setTitleFont(fuente);
        fondo.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), titulo));
        fondo.setLayout(new GridLayout(1,3)); 
        for (String f:fichasEspeciales) {
        	BotonFicha boton = new BotonFicha(f, color);
        	botones.add(boton);
        	fondo.add(boton);
        }
        add(fondo);
    } 
    
    /*
     * Prepara las acciones,
     */
    private void prepararAcciones(){   
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        for (BotonFicha b:botones) b.addActionListener(e -> accionBoton(e));
    } 

    /*
     * Selecionnar tipo de ficha.
     */
    private void accionBoton(ActionEvent e) {   
        seleccion = ((BotonFicha)e.getSource()).tipo();
        dispose();
    }    
    
    /**
     * Da el tipo de ficha seleccionada.
     * @return Tipo de ficha especial.
     */
    public String seleccion() {
    	return seleccion;
    }
}

/**
 * Botón para selecciona un tipo de ficha.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
class BotonFicha extends JButton {
	
	String tipo, color;
	
	/**
	 * Constructor para objetos de tipo BotonFicha.
	 * @param tipo Tipo de la ficha.
	 * @param color Color de la ficha.
	 */
	public BotonFicha(String tipo, String color) {
		this.tipo = tipo;
		this.color = color;
        setOpaque(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(Color.white);
	}    
	
    @Override
    public void paint(Graphics g){
		ImageIcon f = new ImageIcon(getClass().getResource("Imagenes/"+tipo+color+"G.png"));
		ImageIcon fondo = new ImageIcon(f.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
		setIcon(fondo);
		super.paint(g);
    }
    
    /**
     * Da el tipo de la ficha especial.
     * @return Tipo de la ficha.
     */
    public String tipo() {
    	return tipo;
    }
}