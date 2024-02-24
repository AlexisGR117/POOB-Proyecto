package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 * Pantalla de ayuda del juego.
 * @author Jefer Gonzalez
 * @version 1.0 (10/12/2022)
 */
public class Ayuda extends JDialog {
	
    private JButton aceptar;
    
    /**
     * Constructor para objetos de clase Ayuda.
     * @param gui Juego en el que se abre la ayuda.
     */
    public Ayuda(Juego gui) {
        super(gui, Dialog. ModalityType.DOCUMENT_MODAL);
        prepararElementos();
        prepararAcciones();
        setVisible(true);
    } 
    
    /**
     * Constructor para objetos de clase Ayuda.
     * @param gui Pantalla de inicio donde se abre la ayuda.
     */
    public Ayuda(DAPOOSGUI gui) {
        super(gui, Dialog. ModalityType.DOCUMENT_MODAL);
        prepararElementos();
        prepararAcciones();
        setVisible(true);
    } 
    
    /*
     * Prepara los elementos.
     */
    private void prepararElementos() {   
        setSize(535, 560);
        setTitle("Ayuda");
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Fondo fondo = new Fondo("Ayuda", false);
        fondo.setPreferredSize(new Dimension(500, 1000)); 
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(fondo);
        scrollPane.getVerticalScrollBar().setBackground(Color.white);
        Fondo fondo2 = new Fondo("FondoBlanco", true);
        aceptar = new JButton("Aceptar");
        aceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aceptar.setFont(new Font("Perpetua", 0, 30));
        aceptar.setBackground(Color.white);
        aceptar.setBorder(new LineBorder(Color.black, 2));
        fondo2.add(aceptar);
        add(scrollPane, BorderLayout.CENTER);
        add(fondo2, BorderLayout.SOUTH);
    } 
    
    /*
     * Prepara las acciones.
     */
    private void prepararAcciones(){   
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aceptar.addActionListener(e -> accionAceptar());
    } 

    /*
     * Sale de la pantalla de ayuda.
     */
    private void accionAceptar() {   
        dispose();
    }    
    
}
