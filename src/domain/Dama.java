package domain;

import java.util.ArrayList;

/**
 * Tipo de ficha que se puede mover varias casillas, se puede devolver.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class Dama extends Ficha {
	
	/**
	 * Constructor para objetos de clase Dama.
	 * @param jugador Jugador al cual pertenece la ficha.
	 */
	public Dama(Jugador jugador) {
		super(jugador);
	}
	
	@Override
	public ArrayList<int[]> movimientosPosibles() {
		ArrayList<int[]> m = new ArrayList<int[]>();
		if (casilla.salir()) {
			int fila = casilla.fila(), columna = casilla.columna();
			Tablero tablero= casilla.tablero();
			boolean d1 = true, d2 = true, d3 = true, d4 = true;
			int tamano = Tablero.TAMANOLADO;
	        for(int f = 1; f <= fila && f <= columna && d1; f++) {
	        	if (tablero.ficha(fila-f, columna-f) != null) d1 = false;
	        	else {
	        		int[] p = {fila-f, columna-f};
	    			m.add(p);
	        	}
	        }
	        for(int f = 1; f < tamano - fila && f < tamano - columna && d2; f++) {
	        	if (tablero.ficha(fila+f, columna+f) != null) d2 = false;
	        	else {
	        		int[] p = {fila+f, columna+f};
	    			m.add(p);
	        	}
	        }
	        for(int f = 1; f <= fila && f < tamano - columna && d3; f++) {
	        	if (tablero.ficha(fila-f, columna+f) != null) d3 = false;
	        	else {
	        		int[] p = {fila-f, columna+f};
	    			m.add(p);
	        	}
	        }
	        for(int f = 1; f < tamano - fila && f <= columna && d4; f++) {
	        	if (tablero.ficha(fila+f, columna-f) != null) d4 = false;
	        	else {
	        		int[] p = {fila+f, columna-f};
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
			int tamano = Tablero.TAMANOLADO;
			int d1 = 0, d2 = 0, d3 = 0, d4 = 0;
			Ficha ficha;
	        for(int f = 1; f <= fila && f <= columna && d1 < 2; f++){
	        	ficha = tablero.ficha(fila-f, columna-f);
	        	if (ficha != null) {
	        		if (ficha.lado() != jugador.lado()) d1 += 1;
	        		else d1 += 2;
	        	} else if (d1 == 1) {
	        		int[] p = {fila-f, columna-f};
	    			m.add(p);
	        	}
	        }
	        for(int f = 1; f < tamano - fila && f < tamano - columna && d2 < 2; f++){
	        	ficha = tablero.ficha(fila+f, columna+f);
	        	if (ficha != null) {
	        		if (ficha.lado() != jugador.lado()) d2 += 1;
	        		else d2 += 2;
	        	} else if (d2 == 1) {
	        		int[] p = {fila+f, columna+f};
	    			m.add(p);
	        	}
	        }
	        for(int f = 1; f <= fila && f < tamano - columna && d3 < 2; f++){
	        	ficha = tablero.ficha(fila-f, columna+f);
	        	if (ficha != null) {
	        		if (ficha.lado() != jugador.lado()) d3 += 1;
	        		else d3 += 2;
	        	} else if (d3 == 1) {
	        		int[] p = {fila-f, columna+f};
	    			m.add(p);
	        	}
	        }
	        for(int f = 1; f < tamano - fila && f <= columna && d4 < 2; f++){
	        	ficha = tablero.ficha(fila+f, columna-f);
	        	if (ficha != null) {
	        		if (ficha.lado() != jugador.lado()) d4 += 1;
	        		else d4 += 2;
	        	} else if (d4 == 1) {
	        		int[] p = {fila+f, columna-f};
	    			m.add(p);
	        	}
	        }
		}
		return m;
	}	
	
	@Override
	public String toString() {
		return "Dama"+jugador.color();
	}
}
