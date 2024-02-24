package domain;

import java.util.ArrayList;

/**
 * Tipo de Maquina que hace movimientos aleatorios.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public class Aprendiz extends Maquina {

	/**
	 * Constructor para objetos de clase Aprendiz.
	 * @param color Color de las fichas del jugador.
	 * @param lado Lado del jugador.
	 */
	public Aprendiz(String color, char lado) {
		super(color, lado);
	}
	
	@Override
	public void movimientoAutomatico() throws DAPOOSException {
		if (fichas.size() > 0) {
			Tablero tablero = fichas.get(0).casilla().tablero();
			int fila, columna;
			Ficha ficha;
			if (tablero.comodinActivo() instanceof Stomp) {
				ficha = fichas.get((int)(Math.random()*(fichas.size())));
				ArrayList<int[]> pe = posiblesEliminaciones();		
				int[] movimiento = pe.get((int)(Math.random()*(pe.size())));
				fila = movimiento[0];
				columna = movimiento[1];
			} else {
				ArrayList<Object[]> pm = posiblesMovimientos();
				Object[] movimiento = pm.get((int)(Math.random()*(pm.size())));
				ficha = (Ficha)movimiento[0];
				fila = (int)movimiento[1];
				columna = (int)movimiento[2];
			}
			tablero.seleccionar(ficha);
			if (((lado == 'n' && fila == Tablero.TAMANOLADO - 1) ||
        		 (lado == 's' && fila == 0)) && ficha instanceof Peon) {
				String[] fichasEspeciales = DAPOOS.FICHASESPECIALES;
				tablero.moverSeleccion(fila, columna, fichasEspeciales[(int)(Math.random()*(fichasEspeciales.length))]);
			} else tablero.moverSeleccion(fila, columna);
			if (tablero.comodinActivo() instanceof Gun) {
				ArrayList<int[]> pe = posiblesEliminaciones();		
				int[] movimiento = pe.get((int)(Math.random()*(pe.size())));
				tablero.seleccionar(tablero.ficha(movimiento[0], movimiento[1]));
			}
		}
	}
	
}
