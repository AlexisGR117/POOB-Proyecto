package domain;

import java.io.Serializable;

/**
 * Casilla de un tablero de DAPOOS.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class Casilla implements Serializable {
	
	protected int fila, columna;
	protected Tablero tablero;
	protected Ficha ficha;
	protected boolean activo, visible;
	protected Comodin comodin;
	
	/**
	 * Constructor para objetos de tipo Casilla.
	 * @param tablero Tablero en el que está la casilla.
	 * @param fila Fila del tablero en la que está posicionada la casilla.
	 * @param columna Columna del tablero en la que está posicionada la casilla.
	 */
	public Casilla(Tablero tablero, int fila, int columna) {
		this.tablero = tablero;
		this.fila = fila;
		this.columna = columna;
		activo = false;
		visible = true;
	}
	
	/**
	 * Fila en la que está la casilla.
	 * @return Número de la fila en el tablero.
	 */
	public int fila() {
		return fila;
	}
	
	/**
	 * Columna en la que está la casilla.
	 * @return Número de la columna en el tablero.
	 */
	public int columna(){
		return columna;
	}
	
	/**
	 * Retorna el tablero donde está la casilla.
	 * @return Tablero en el que está la casilla.
	 */
	public Tablero tablero() {
		return tablero;
	}
	
	/**
	 * Devuelve la ficha que se encuentra actualemnte en la casilla.
	 * @return La ficha que esta ubicada en la casilla.
	 */
	public Ficha ficha() {
		return ficha;
	}
	
	/**
	 * Coloca una ficha en la casilla.
	 * @param ficha Ficha que va ser colocada en la casilla.
	 * @throws DAPOOSException INVALID_TOKEN_TYPE, si el tipo de ficha especial dado no está dentro de los establecidos que son: "Dama", "Ninja" y "Zombie".
	 */
	public Comodin colocarFicha(Ficha ficha) throws DAPOOSException {
		if (!activo) activo = true;
		if (!visible) visible = true;
		this.ficha = ficha;
		ficha.establecerCasilla(this);
		tablero.cambiarUltimaCasilla(this);
		return comodin;
	}
	
	/**
	 * Quita la ficha de la casilla.
	 */
	public void quitarFicha() {
		ficha = null;
	}
	
	/**
	 * Dice si la ficha puede salir de la casilla.
	 * @return True si la ficha puede salir, de lo contario False.
	 */
	public boolean salir() {
		return true;
	}
	
	/**
	 * Dice si las casilla está activa, esto significa que ya ha sido pisada alguna vez.
	 * @return True si han colocado en la casilla alguna ficha, de lo contario, False.
	 */
	public boolean activo() {
		return activo;
	}
	
	/**
	 * Invisibiliza la casilla, lo que significa que si es especial su representación no es visible.-
	 */
	public void invisibilizar() {
		visible = false;
	}
	
	/**
	 * Dice si la representación gráfica de la casilla es visible.
	 * @return True si es visible, de lo contrario, False.
	 */
	public boolean visible() {
		return visible;
	}
	
	/**
	 * Devuelve el comodín que está en la casilla.
	 * @return Comodín que está en la casilla.
	 */
	public Comodin comodin() {
		return comodin;
	}
	
	/**
	 * Coloca un comodín en la casilla.
	 * @param comodin Comodín que se quiere poner en la casilla.
	 */
	public void colocarComodin(Comodin comodin) {
		this.comodin = comodin;
	}
	
	/**
	 * Quita el comodín de la casilla.
	 */
	public void quitarComodin() {
		comodin = null;
	}
	
	@Override
	public String toString() {
		return "Casilla";
	}
}
