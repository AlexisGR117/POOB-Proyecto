package domain;
import java.util.*;

/**
 * Tipo de ficha después de ser capturada, puede volver al espacio en que fue capturada. 
 * @author Jefer Gonzalez
 * @version 1.0 (27/11/2022)
 */
public class Zombie extends Ficha {
	
	private boolean captura, enterrada;
	private int turnos;
	
	/**
	 * Constructor para objetos de clase Zombie.
	 * @param jugador Jugador al cual pertenece la ficha.
	 */
	public Zombie(Jugador jugador) {
		super(jugador);
		captura = false;
		enterrada = false;
	}
	
	@Override
	public ArrayList<int[]> movimientosPosibles() {
		ArrayList<int[]> m = new ArrayList<int[]>();
		if (!enterrada && casilla.salir()) {
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
		if (!enterrada && casilla.salir()) {
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
		else {
			casilla.quitarFicha();
			casilla.tablero().enterrarZombie(this);
			enterrada = true;
			captura = true;
		}
	}
	
	/**
	 * Desentierra la ficha Zombie, lo que significa que vuelve al tablero.
	 * @return True si se pudo desenterrar, de lo contrario, false.
	 * @throws DAPOOSException
	 */
	public boolean desenterrar() throws DAPOOSException {
		turnos += 1;
		if (turnos >= 4 && casilla.ficha() == null) {
			casilla.colocarFicha(this);
			enterrada = false;
			return true;
		} else return false;
	}
	
	@Override
	public String toString() {
		return "Zombie"+jugador.color();
	}
}
