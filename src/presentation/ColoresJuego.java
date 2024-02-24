package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import domain.DAPOOSException;

/**
 * Configuración de colores del juego.
 * @author Jefer Gonzalez
 * @version 1.0 (10/12/2022)
 */
public class ColoresJuego extends JDialog {
	
	private JButton seleccionar1, seleccionar2, seleccionar3;
    private JComboBox<String> seleccionar4, seleccionar5;
    private JButton guardar, cancelar, restaurar;
    private Juego juego;
    private Color casillaBlanca, casillaNegra, borde;
    private JPanel configuracion;
    private Fondo colores;
    private Font fuente;
    private Cursor cursor;
    private Border borderLine;
    
    /**
     * Constructor para objetos de clase ColoresJuego.
     */
    public ColoresJuego(Juego juego) {
        super(juego, Dialog. ModalityType.DOCUMENT_MODAL);
        casillaBlanca = juego.casillaBlanca();
        casillaNegra = juego.casillaNegra();
        borde = juego.borde();
        this.juego = juego;
        prepararElementos();
        prepararAcciones();
        setVisible(true);
    } 
    
    /*
     * Preapara los elementos.
     */
    private void prepararElementos() {   
        setTitle("Configurar Colores");
        setSize(430, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 1));
        colores = new Fondo("FondoBlanco", true);
        colores.setLayout(new BorderLayout());
        configuracion = new JPanel();
        configuracion.setLayout(new GridLayout(2, 1));
        configuracion.setOpaque(false);
        borderLine = new LineBorder(Color.black, 2);
        TitledBorder titulo = new TitledBorder(borderLine, "Configurar Colores");
        fuente = new Font("Perpetua", Font.BOLD, 20);
        titulo.setTitleFont(fuente);
        cursor = new Cursor(Cursor.HAND_CURSOR);
        configuracion.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), titulo));
        prepararElementosTablero();
        prepararElementosJugadores();
        prepararElementosOpciones();
        colores.add(configuracion, BorderLayout.CENTER);
        add(colores);
    } 
    
    /*
     * Prepara los elementos de las opciones de colores del tablero.
     */
    private void prepararElementosTablero() {   
        JPanel tablero = new JPanel();
        tablero.setOpaque(false);
        tablero.setLayout(null);
        TitledBorder titulo = new TitledBorder(borderLine, "Tablero");
        fuente = new Font("Perpetua", Font.PLAIN, 18);
        titulo.setTitleFont(fuente);
        tablero.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), titulo));
        JLabel casillasNegras = new JLabel("Casillas Negras:");
        casillasNegras.setBounds(30, 20, 200, 30);
        casillasNegras.setFont(fuente);
        JLabel casillasBlancas = new JLabel("Casillas Blancas:");
        casillasBlancas.setBounds(30, 55, 200, 30);
        casillasBlancas.setFont(fuente);
        JLabel bordes = new JLabel("Bordes:");
        bordes.setBounds(30, 90, 200, 30);
        bordes.setFont(fuente);
        seleccionar1 = new JButton();
        seleccionar1.setCursor(cursor);
        seleccionar1.setBackground(casillaNegra);
        seleccionar1.setBounds(240, 20, 150, 30);
        seleccionar1.setBorder(borderLine);
        seleccionar2 = new JButton();
        seleccionar2.setCursor(cursor);
        seleccionar2.setBackground(casillaBlanca);
        seleccionar2.setBounds(240, 55, 150, 30);
        seleccionar2.setBorder(borderLine);
        seleccionar3 = new JButton();
        seleccionar3.setCursor(cursor);
        seleccionar3.setBackground(borde);
        seleccionar3.setBounds(240, 90, 150, 30); 
        seleccionar3.setBorder(borderLine);
        tablero.add(casillasNegras);
        tablero.add(seleccionar1);
        tablero.add(casillasBlancas);
        tablero.add(seleccionar2);
        tablero.add(bordes);
        tablero.add(seleccionar3);
        configuracion.add(tablero);
    }
    
    /*
     * Prepara los elementos de las opciones de colores de los jugadores.
     */
    private void prepararElementosJugadores() {   
        JPanel jugadores = new JPanel();
        jugadores.setOpaque(false);
        jugadores.setLayout(null);
        TitledBorder titulo = new TitledBorder(borderLine, "Jugadores");
        titulo.setTitleFont(fuente);
        jugadores.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), titulo));
        JLabel norte = new JLabel("Jugador Norte:");
        norte.setBounds(30, 30, 200, 30);
        norte.setFont(fuente);
        JLabel sur = new JLabel("Jugador Sur:");
        sur.setBounds(30, 85, 200, 30);
        sur.setFont(fuente);
        seleccionar4 = new JComboBox<String>(OpcionesJuego.COLORES);
        seleccionar4.setBounds(240, 30, 150, 35);
        seleccionar4.setFont(fuente);
        seleccionar4.setCursor(cursor);
        seleccionar4.setSelectedItem(juego.dapoos().jugadorNorte().color());
        seleccionar4.setBackground(Color.white);
        seleccionar4.setBorder(borderLine);
        seleccionar5 = new JComboBox<String>(OpcionesJuego.COLORES);
        seleccionar5.setBounds(240, 85, 150, 35);
        seleccionar5.setFont(fuente);
        seleccionar5.setCursor(cursor);
        seleccionar5.setSelectedItem(juego.dapoos().jugadorSur().color());
        seleccionar5.setBackground(Color.white);
        seleccionar5.setBorder(borderLine);   
        jugadores.add(norte);
        jugadores.add(seleccionar4);
        jugadores.add(sur);
        jugadores.add(seleccionar5);
        configuracion.add(jugadores);
    }
    
    /*
     * Prepara las opciones de configuración de colores.
     */
    private void prepararElementosOpciones() {   
        JPanel options = new JPanel();
        options.setOpaque(false);
        options.setLayout(new GridLayout(1, 3, 10, 10));;
        guardar = new JButton("Guardar");
        guardar.setCursor(cursor);
        guardar.setFont(fuente);
        guardar.setBackground(Color.white);
        guardar.setBorder(borderLine);
        cancelar = new JButton("Cancelar");
        cancelar.setCursor(cursor);
        cancelar.setFont(fuente);
        cancelar.setBackground(Color.white);
        cancelar.setBorder(borderLine);
        restaurar = new JButton("Restaurar");
        restaurar.setCursor(cursor);
        restaurar.setFont(fuente);
        restaurar.setBackground(Color.white);
        restaurar.setBorder(borderLine);
        options.add(guardar);
        options.add(restaurar);
        options.add(cancelar);
        colores.add(options, BorderLayout.SOUTH);
    }
    
    /*
     * Prepara las acciones.
     */
    private void prepararAcciones() { 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                actionExit();
            }
        });
        seleccionar1.addActionListener(e -> accionCasillasNegras());
        seleccionar2.addActionListener(e -> accionCasillasBlancas());
        seleccionar3.addActionListener(e -> accionBordes());
        guardar.addActionListener(e -> accionGuardar());
        cancelar.addActionListener(e -> accionCancelar());
        restaurar.addActionListener(e -> accionRestaurar());
    } 
    
    /*
     * Sale de la configuración de color.
     */
    private void actionExit(){   
        int answer = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir de la configuración de color?", "Salir configuración de color", JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.OK_OPTION){
            setVisible(false);
            dispose();
        }
    }
    
    /*
     * Cambiar de color las casillas negras.
     */
    private void accionCasillasNegras() {
        Color color = JColorChooser.showDialog(null,"Color de las casillas negras", casillaNegra);
        if (color != null) {
            casillaNegra = color;
            seleccionar1.setBackground(casillaNegra);
        }
    }
    
    /*
     * Cambiar de color las casillas blancas.
     */    
    private void accionCasillasBlancas() {
        Color color = JColorChooser.showDialog(null,"Color de las casillas blancas", casillaBlanca);
        if (color != null) {
            casillaBlanca = color;
            seleccionar2.setBackground(casillaBlanca);
        }
    }
    
    /*
     * Cambiar de color los bordes.
     */    
    private void accionBordes() {
        Color color = JColorChooser.showDialog(null,"Color de los bordes", borde);
        if (color != null) {
            borde = color;
            seleccionar3.setBackground(borde);
        }
    }
    
    /*
     * Guarda los cambios de los colores.
     */
    private void accionGuardar() {
        try {
			juego.cambiarColores(casillaNegra, casillaBlanca, borde, (String)seleccionar4.getSelectedItem(), (String)seleccionar5.getSelectedItem());
			 setVisible(false);
		       dispose();
		} catch (DAPOOSException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
    }
    
    /*
     * No hace ningún cambio en los colores.
     */    
    private void accionCancelar() {
        actionExit();
    }
    
    /*
     * Los colores vuelven a los valores por defecto.
     */    
    private void accionRestaurar() {
        casillaBlanca = Color.white;
        seleccionar2.setBackground(casillaBlanca);
        casillaNegra = Color.lightGray;
        seleccionar1.setBackground(casillaNegra);
        borde = Color.black;
        seleccionar3.setBackground(borde);
        seleccionar4.setSelectedItem(OpcionesJuego.COLORES[0]);
        seleccionar5.setSelectedItem(OpcionesJuego.COLORES[1]);
    }
}
