package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import domain.DAPOOSException;
import domain.Log;

/**
 * Interfaz gráfica de las opciones de juego.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class OpcionesJuego extends JDialog{
	
    private JComboBox<String> seleccionarOponente, seleccionarTipoMaquina, seleccionarVisualizacion, seleccionarDificultad, seleccionarLado, seleccionarColorUno, seleccionarColorDos;
    private JCheckBox fichasEspeciales, comodines;
    private JTextField campoUno, campoDos;
    public static final String[] COLORES = {"Negro", "Blanco", "Rojo"};
    private JSpinner seleccionarPorcentage, seleccionarTiempo;
    private JButton jugar, cancelar;
    private JLabel tiempo, tipoMaquina, lado;
    private Font fuente;
    private Cursor cursor;
    private Border borde;
    private Fondo fondo;
    private Jugadores jugadores;
    private DAPOOSGUI gui;
    
    /**
     * Constructor para objetos de clase OpcionesJuego.
     */
    public OpcionesJuego(DAPOOSGUI gui) {
        super(gui, Dialog. ModalityType.DOCUMENT_MODAL);
        this.gui = gui;
        prepararElementos();
        prepararAcciones();
        setVisible(true);
    } 
    
    /*
     * Prepara los elementos de las opciones de juego.
     */
    private void prepararElementos() {   
        setSize(1000, 665);
        setTitle("Opciones de juego");
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,1));
        setResizable(false);
        fondo = new Fondo("FondoCuadrados", true);
        borde = new LineBorder(Color.black, 2);
        fondo.setLayout(null);
        TitledBorder titulo = new TitledBorder(borde, "Opciones de juego");
        fuente = new Font("Perpetua", Font.BOLD, 35);
        titulo.setTitleFont(fuente);
        fondo.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), titulo));
        cursor = new Cursor(Cursor.HAND_CURSOR);
        fuente = new Font("Perpetua", 0, 30);
        prepararElementosJugadores();
        prepararElementosJuego();
        jugar = new JButton("Jugar");
        jugar.setBounds(340, 550, 150, 50);    
        jugar.setCursor(cursor);
        jugar.setFont(fuente);
        jugar.setBackground(Color.white);
        jugar.setBorder(borde);
        cancelar = new JButton("Cancelar");
        cancelar.setBounds(510, 550, 150, 50);    
        cancelar.setCursor(cursor);
        cancelar.setFont(fuente);
        cancelar.setBackground(Color.white);
        cancelar.setBorder(borde);
        fondo.add(jugar);
        fondo.add(cancelar);
        add(fondo);
    } 
    
    /*
     * Prepara los elementos de las opciones de los jugadores.
     */
    private void prepararElementosJugadores() {   
    	jugadores = new Jugadores(COLORES[0], COLORES[1]);
        TitledBorder titulo = new TitledBorder(borde, "Jugadores");
        titulo.setTitleFont(fuente);
        jugadores.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), titulo));
        jugadores.setLayout(null);
        jugadores.setOpaque(false);
        jugadores.setBounds(40, 40, 910, 250);
        JLabel oponente = new JLabel("Selecciona tu oponente: ");
        oponente.setBounds(20, 60, 300, 30);
        oponente.setFont(fuente);
        String[] oponentes = {"Jugador", "Máquina"};
        seleccionarOponente = new JComboBox<String>(oponentes);
        seleccionarOponente.setCursor(cursor);
        seleccionarOponente.setSelectedItem(oponentes[0]);
        seleccionarOponente.setBounds(290, 55, 130, 40);  
        seleccionarOponente.setFont(fuente);
        seleccionarOponente.setBackground(Color.white);
        seleccionarOponente.setBorder(borde);
        jugadores.add(oponente);
        jugadores.add(seleccionarOponente);
        prepararElementosMaquinas();
        prepararElementosJugadorNorte();
        prepararElementosJugadorSur();
        fondo.add(jugadores);
    }
    
    /*
     * Prepara los elementos de las opciones de maquinas.
     */
    private void prepararElementosMaquinas() {   
        tipoMaquina = new JLabel("Tipo: ");
        tipoMaquina.setBounds(430, 60, 80, 30);
        tipoMaquina.setFont(fuente);
        lado = new JLabel("Lado: ");
        lado.setBounds(690, 60, 80, 30);
        lado.setFont(fuente);
        String[] machineTypes = {"Aprendiz", "Principiante"}, sides = {"Norte", "Sur"};
        seleccionarTipoMaquina = new JComboBox<String>(machineTypes);
        seleccionarTipoMaquina.setCursor(cursor);
        seleccionarTipoMaquina.setSelectedItem(machineTypes[0]);
        seleccionarTipoMaquina.setBounds(500, 55, 180, 40);  
        seleccionarTipoMaquina.setFont(fuente);
        seleccionarTipoMaquina.setBackground(Color.white);
        seleccionarTipoMaquina.setBorder(borde);
        seleccionarLado = new JComboBox<String>(sides);
        seleccionarLado.setCursor(cursor);
        seleccionarLado.setSelectedItem(sides[1]);
        seleccionarLado.setBounds(770, 55, 110, 40);  
        seleccionarLado.setFont(fuente);
        seleccionarLado.setBackground(Color.white);
        seleccionarLado.setBorder(borde);
        tipoMaquina.setVisible(false);
        seleccionarTipoMaquina.setVisible(false);
        lado.setVisible(false);
        seleccionarLado.setVisible(false);
        jugadores.add(tipoMaquina);
        jugadores.add(seleccionarTipoMaquina);
        jugadores.add(lado);
        jugadores.add(seleccionarLado);
    }    
    
    /*
     * Prepara los elementos de las opciones del jugador norte.
     */
    private void prepararElementosJugadorNorte() {   
        JLabel jugador = new JLabel("Jugador Norte");
        jugador.setBounds(175, 100, 200, 35);
        jugador.setFont(fuente);
        JLabel nombre = new JLabel("Nombre:");
        nombre.setBounds(65, 145, 100, 35);
        nombre.setFont(fuente);
        campoUno = new JTextField();
        campoUno.setBounds(185, 145, 210, 35);
        campoUno.setFont(fuente);
        JLabel color = new JLabel("Color:");
        color.setBounds(65, 190, 100, 35);
        color.setFont(fuente);
        seleccionarColorUno = new JComboBox<String>(COLORES);
        seleccionarColorUno.setBounds(185, 190, 210, 35);
        seleccionarColorUno.setFont(fuente);
        seleccionarColorUno.setCursor(cursor);
        seleccionarColorUno.setSelectedItem(COLORES[0]);
        seleccionarColorUno.setBackground(Color.white);
        seleccionarColorUno.setBorder(borde);
        jugadores.add(jugador);
        jugadores.add(campoUno);
        jugadores.add(nombre);
        jugadores.add(color);
        jugadores.add(seleccionarColorUno);
    }
    
    /*
     * Prepara los elementos de las opciones del jugador sur.
     */
    private void prepararElementosJugadorSur() {   
        JLabel jugador = new JLabel("Jugador Sur");
        jugador.setBounds(640, 100, 200, 35);
        jugador.setFont(fuente);
        JLabel nombre = new JLabel("Nombre:");
        nombre.setBounds(515, 145, 100, 35);
        nombre.setFont(fuente);
        campoDos = new JTextField();
        campoDos.setBounds(635, 145, 210, 35);
        campoDos.setFont(fuente);
        JLabel color = new JLabel("Color:");
        color.setBounds(515, 190, 100, 35);
        color.setFont(fuente);
        seleccionarColorDos = new JComboBox<String>(COLORES);
        seleccionarColorDos.setBounds(635, 190, 210, 35);
        seleccionarColorDos.setFont(fuente);
        seleccionarColorDos.setCursor(cursor);
        seleccionarColorDos.setSelectedItem(COLORES[1]);
        seleccionarColorDos.setBackground(Color.white);
        seleccionarColorDos.setBorder(borde);
        jugadores.add(jugador);
        jugadores.add(campoDos);
        jugadores.add(nombre);
        jugadores.add(color);
        jugadores.add(seleccionarColorDos);
    }
    
    /*
     * Prepara los elementos de las opciones del juego.
     */
	private void prepararElementosJuego() {   
    	JLabel juego = new JLabel();
        TitledBorder titulo = new TitledBorder(borde, "Juego");
        titulo.setTitleFont(fuente);
        juego.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), titulo));
        juego.setLayout(null);
        juego.setOpaque(false);
        juego.setBounds(40, 290, 910, 250);
        fichasEspeciales = new JCheckBox("Fichas Especiales");
        fichasEspeciales.setCursor(cursor);
        fichasEspeciales.setBounds(20, 40, 210, 40);  
        fichasEspeciales.setFont(fuente);
        fichasEspeciales.setOpaque(false);
        fichasEspeciales.setSelected(true);
        comodines = new JCheckBox("Comodines");
        comodines.setCursor(cursor);
        comodines.setBounds(250, 40, 210, 40);  
        comodines.setFont(fuente);
        comodines.setOpaque(false);
        comodines.setSelected(true);
        prepararElementosDificultad(juego);
        prepararElementosCasillasEspeciales(juego);
        juego.add(fichasEspeciales);
        juego.add(comodines);
        fondo.add(juego);
    }
    
    /*
     * Prepara los elementos de las opciones del juego.
     */
	private void prepararElementosDificultad(JLabel juego) {   
        JLabel dificultad = new JLabel("Dificultad:");
        tiempo = new JLabel("Tiempo limite en segundos:"); 
        dificultad.setBounds(20, 190, 200, 30);
        dificultad.setFont(fuente);
        tiempo.setBounds(400, 190, 400, 30);
        tiempo.setFont(fuente);
        String[] difficulties = {"Normal", "QuickTime"};
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1);
        seleccionarDificultad = new JComboBox<String>(difficulties);
        seleccionarDificultad.setCursor(cursor);
        seleccionarDificultad.setSelectedItem(difficulties[0]);
        seleccionarDificultad.setBounds(160, 185, 210, 40);  
        seleccionarDificultad.setFont(fuente);
        seleccionarDificultad.setBackground(Color.white);
        seleccionarDificultad.setBorder(borde);
        seleccionarTiempo = new JSpinner(model);
        seleccionarTiempo.setCursor(cursor);
        seleccionarTiempo.setBounds(730, 185, 148, 40);  
        seleccionarTiempo.setFont(fuente);
        seleccionarTiempo.setBackground(Color.white);
        seleccionarTiempo.setBorder(borde);
        seleccionarTiempo.setVisible(false);
        seleccionarTiempo.setValue(20);
        tiempo.setVisible(false);
        juego.add(dificultad);
        juego.add(seleccionarDificultad);
        juego.add(tiempo);
        juego.add(seleccionarTiempo);
    }
	
    /*
     * Prepara los elementos de las opciones del juego.
     */
	private void prepararElementosCasillasEspeciales(JLabel juego) {   
        comodines.setSelected(true);
        JLabel porcentaje = new JLabel("Porcentaje casillas Especiales: "), visualizacion = new JLabel("Visualización casillas Especiales: ");
        porcentaje.setBounds(20, 90, 400, 30);
        porcentaje.setFont(fuente);
        visualizacion.setBounds(20, 140, 400, 30);
        visualizacion.setFont(fuente);
        String[] visualizaciones = {"Permanente", "Relámpago"};
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1);
        seleccionarPorcentage = new JSpinner(model);
        seleccionarPorcentage.setCursor(cursor);
        seleccionarPorcentage.setBounds(400, 85, 210, 40);  
        seleccionarPorcentage.setFont(fuente);
        seleccionarPorcentage.setBackground(Color.white);
        seleccionarPorcentage.setBorder(borde);
        seleccionarPorcentage.setValue(50);
        seleccionarVisualizacion = new JComboBox<String>(visualizaciones);
        seleccionarVisualizacion.setCursor(cursor);
        seleccionarVisualizacion.setSelectedItem(visualizaciones[0]);
        seleccionarVisualizacion.setBounds(400, 135, 210, 40);  
        seleccionarVisualizacion.setFont(fuente);
        seleccionarVisualizacion.setBackground(Color.white);
        seleccionarVisualizacion.setBorder(borde);
        juego.add(porcentaje);
        juego.add(seleccionarPorcentage);
        juego.add(visualizacion);
        juego.add(seleccionarVisualizacion);
    }
	
    /*
     * Prepara las acciones de las opciones de juego.
     */
    private void prepararAcciones(){   
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                accionSalir();
            }
        });
        seleccionarOponente.addItemListener(e -> accionOponente());
        seleccionarLado.addItemListener(e -> accionLado());
        seleccionarDificultad.addItemListener(e -> accionDificultad());
        seleccionarColorUno.addItemListener(e -> accionColorUno());
        seleccionarColorDos.addItemListener(e -> accionColorDos());
        jugar.addActionListener(e -> accionJugar());
        cancelar.addActionListener(e -> accionSalir());
        
    } 
    
    /*
     * Seleccionar un oponente.
     */
    private void accionOponente() {   
    	if (seleccionarOponente.getSelectedItem().equals("Máquina")) {
            tipoMaquina.setVisible(true);
            seleccionarTipoMaquina.setVisible(true);
            lado.setVisible(true);
            seleccionarLado.setVisible(true);
    		campoUno.setText("Máquina");
    		campoUno.setEnabled(false);
    	} else {
    		tipoMaquina.setVisible(false);
            seleccionarTipoMaquina.setVisible(false);
            lado.setVisible(false);
            seleccionarLado.setVisible(false);
            if (campoUno.getText().equals("Máquina")) campoUno.setText("");
    		if (campoDos.getText().equals("Máquina")) campoDos.setText("");
    		campoDos.setEnabled(true);
    		campoUno.setEnabled(true);
    	}
    }
    
    /*
     * Seleccionar una dificultad.
     */
    private void accionDificultad() {   
    	if (seleccionarDificultad.getSelectedItem().equals("QuickTime")) {
            tiempo.setVisible(true);
            seleccionarTiempo.setVisible(true);
    	} else {
    		tiempo.setVisible(false);
            seleccionarTiempo.setVisible(false);
    	}
    }
    
    /*
     * Seleccionar un lado.
     */
    private void accionLado() {   
    	if (seleccionarLado.getSelectedItem().equals("Sur")) {
    		campoDos.setText("");
    		campoDos.setEnabled(true);
    		campoUno.setText("Máquina");
    		campoUno.setEnabled(false);	
    	} else {
    		campoUno.setText("");
    		campoUno.setEnabled(true);
    		campoDos.setText("Máquina");
    		campoDos.setEnabled(false);	
    	}
    }
    
    /*
     * Seleccionar el color del jugador norte.
     */
    private void accionColorUno() {   
    	jugadores.colorUno = (String)seleccionarColorUno.getSelectedItem();
    	jugadores.repaint();
    }
    
    /*
     * Seleccionar el color del jugador sur.
     */
    private void accionColorDos() {   
    	jugadores.colorDos = (String)seleccionarColorDos.getSelectedItem();
    	jugadores.repaint();
    }
    
    /*
     * Salir de las opciones de juego a la pantalla inicial.
     */
    private void accionSalir(){   
    	dispose();
    } 
    
    /*
     * Jugar con las opciones dadas.
     */
    private void accionJugar(){
    	String nombreUno = campoUno.getText(), nombreDos = campoDos.getText();
    	String colorUno = (String)seleccionarColorUno.getSelectedItem(), colorDos = (String)seleccionarColorDos.getSelectedItem();
    	String tipoMaquina = null;
    	if (seleccionarOponente.getSelectedItem().equals("Máquina")) tipoMaquina = (String)seleccionarTipoMaquina.getSelectedItem();
    	int porcentaje = (int)seleccionarPorcentage.getValue(), tiempo = (int)seleccionarTiempo.getValue();
    	String visualizacion = (String)seleccionarVisualizacion.getSelectedItem(), dificultad = (String)seleccionarDificultad.getSelectedItem();
        try {
			new Juego(nombreUno, nombreDos, colorUno, colorDos, tipoMaquina, porcentaje, comodines.isSelected(), 
					fichasEspeciales.isSelected(),visualizacion, tiempo, dificultad, gui);
	        dispose();
	        gui.setVisible(false);
		} catch (DAPOOSException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (Exception e) {
			Log.record(e);
			JOptionPane.showMessageDialog(null,"Ha ocurrido un error inesperado.");
		}
    } 
    
}

/**
 * Panel de las opciones de jugadores.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
class Jugadores extends JPanel {
	
	String colorUno;
	String colorDos;
	
	/**
	 * Constructor para objetos de clase Jugadores.
	 * @param colorUno Color del jugador Norte.
	 * @param colorDos Color del jugador Sur.
	 */
	public Jugadores(String colorUno, String colorDos) {
		this.colorUno = colorUno;
		this.colorDos = colorDos;
	}
	
	@Override
	public void paint(Graphics g) {
		Image negro = new ImageIcon(getClass().getResource("Imagenes/Dama"+colorUno+".png")).getImage();
		g.drawImage(negro, 125, 95, 50, 50, this);
		Image blanco = new ImageIcon(getClass().getResource("Imagenes/Dama"+colorDos+".png")).getImage();
		g.drawImage(blanco, 590, 95, 50, 50, this);
		setOpaque(false);
		super.paint(g);
	}
}