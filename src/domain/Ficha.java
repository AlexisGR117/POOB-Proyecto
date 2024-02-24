package domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ficha del juego DAPOOS, se muede diagonalmente en el tablero.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public abstract class Ficha implements Serializable {
	
	protected Jugador jugador;
	protected Casilla casilla;
	
	/**
	 * Constructor para objetos de clase Ficha.
	 * @param jugador Jugador al cual pertenece la ficha.
	 */
	public Ficha(Jugador jugador) {
		this.jugador = jugador;
		jugador.agregarFicha(this);
	}
	
	/**
	 * Da las posibles posiciones a las que se puede mover la ficha en el tablero.
	 * @return ArrayList con los movimientos posibles en arreglos donde en la posición 0 está la final y en la 1 la columna.
	 */
	public abstract ArrayList<int[]> movimientosPosibles();
	
	/**
	 * Da las posibles posiciones en las que la ficha puede capturar al moverse.
	 * @return ArrayList con los saltos posibles en arreglos donde en la posición 0 está la final y en la 1 la columna.
	 */
	public abstract ArrayList<int[]> saltosPosibles();
	
	/**
	 * Mueve la ficha a una posición dada en el tablero.
	 * @param f Fila a la cual se quiere mover la ficha.
	 * @param c Columna a la cual se quiere mover la ficha.
	 * @return False si la ficha podia capturar y no lo hizo. Si capturó o no tenía posibilidad de capturar, True.
	 * @throws DAPOOSException INVALID_MOVEMENT, si la ficha no se puede mover a la posición dada.
	 */
	public boolean mover(int f, int c) throws DAPOOSException {
		int fila = casilla.fila(), columna = casilla.columna();
		ArrayList<int[]> mp;
		boolean captura = false;
		if(casilla.tablero().comodinActivo() instanceof Stomp) mp = jugador.posiblesEliminaciones();
		else mp = movimientosPosibles();
		boolean mover = false;
		for (int[] m:mp) if (m[0] == f && m[1] == c) mover = true;
		if (!mover) throw new DAPOOSException(DAPOOSException.INVALID_MOVEMENT);
		if(casilla.tablero().comodinActivo() instanceof Stomp) {
			casilla.tablero().ficha(f,  c).capturar();
			captura = true;
		}
		Tablero tablero = casilla.tablero();
		int fi = f - fila, co = c - columna, d = 1, e = 1;
		if (fi < 0) d = -1;
		if (co < 0) e = -1;			
		if (Math.abs(fi) > 1) {
			Ficha fichaC;
			for (int i = 1; i < Math.abs(fi) && !captura; i++) {
				fichaC = tablero.ficha(fila+i*d, columna+i*e);
				if (fichaC != null) {
					fichaC.capturar();
					captura = true;
				}
			}	
		}
		return captura;
	}
	
	/**
	 * Da el color del jugador al cual le pertenece la ficha.
	 * @return Color de la ficha.
	 */
	public String color() {
		return jugador.color();
	}
	
	/**
	 * Da el lado del jugador al cual le pertenece la ficha.
	 * @return Lado de la ficha.
	 */
	public char lado() {
		return jugador.lado();
	}
	
	/**
	 * Devuelve el jugador al cual le pertenece la ficha
	 * @return El jugador de la ficha.
	 */
	public Jugador jugador() {
		return jugador;
	}
	
	/**
	 * Captura la ficha lo que significa que se quita de la casilla y se le elimna al jugador que le pertenecía.
	 */
	public void capturar() {
		casilla.quitarFicha();
		jugador.eliminarFicha(this);
	}
	
	/**
	 * Establece la casilla en la que está la ficha en el tablero.
	 * @param casilla Casilla del tablero en la que se quiere poner la ficha.
	 */
	public void establecerCasilla(Casilla casilla) {
		this.casilla = casilla;
	}
	
	/**
	 * Da la casilla en la que se encuentra la ficha dentro del tablero del juego.
	 * @return Casilla en la que está la ficha.
	 */
	public Casilla casilla() {
		return casilla;
	}
	
	/**
	 * Retorna los saltos posibles que puede hacer la ficha dentro del tablero.
	 * @return Número de los saltos posibles que puede hacer la ficha.
	 */
	public int numeroSaltosPosibles() {
		return saltosPosibles().size();
	}

	/**
	 * Retorna los movimientos posibles que puede hacer la ficha dentro del tablero.
	 * @return Número de los movimientos posibles que puede hacer la ficha.
	 */
	public int numeroMovimientosPosibles() {
		return movimientosPosibles().size();
	}
	
	@Override
	public String toString() {
		return "Ficha";
	}
}