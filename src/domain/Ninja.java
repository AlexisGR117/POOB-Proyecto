package domain;
import java.util.*;

/**
 * Tipo de ficha que es invulnerable la primera vez que se trata de capturar. Se mueve de a una casilla, se puede devolver.
 * @author Jefer Gonzalez
 * @version 1.0 (27/11/2022)
 */
public class Ninja extends Ficha {
	
	private boolean captura;
	
	/**
	 * Constructor para objetos de clase Ninja.
	 * @param jugador Jugador al cual pertenece la ficha.
	 */
	public Ninja(Jugador jugador) {
		super(jugador);
		captura = false;
	}
	
	@Override
	public ArrayList<int[]> movimientosPosibles() {
		ArrayList<int[]> m = new ArrayList<int[]>();
		if (casilla.salir()) {
			int fila = casilla.fila(), columna = casilla.columna();
			Tablero tablero = casilla.tablero();
			for (int df=-1; df<2;df++) for (int dc=-1; dc<2;dc++) {
	            if (df != 0 && dc != 0 && tablero.dentro(fila+df, columna+dc) && tablero.ficha(fila+df, columna+dc) == null) {
	        		int[] p = {fila+df, columna+dc};
	        		m.add(p);
	            }
			}
			m.addAll(saltosPosibles());
		}
		return m;
	}
	
	@Override
	public ArrayList<int[]> saltosPosibles() {
		ArrayList<int[]> m = new ArrayList<int[]>();
		if (casilla.salir()) {
			int fila = casilla.fila(), columna = casilla.columna();
			Tablero tablero = casilla.tablero();
			for (int df=-1; df<2;df++) for (int dc=-1; dc<2;dc++) {
	            if (df != 0 && dc != 0 && tablero.dentro(fila+df, columna+dc) && tablero.ficha(fila+df, columna+dc) != null && tablero.ficha(fila+df, columna+dc).lado() != jugador.lado() &&
					tablero.dentro(fila+df*2, columna+dc*2) && tablero.ficha(fila+df*2, columna+dc*2) == null) {
	            	int[] p = {fila+df*2, columna+dc*2};
	            	m.add(p);
	            }
	        }
		}
		return m;
	}	

	@Override
	public void capturar() {
		if (captura != false) super.capturar();
		else captura = true;
	}
	
	@Override
	public String toString() {
		return "Ninja"+jugador.color();
	}
}