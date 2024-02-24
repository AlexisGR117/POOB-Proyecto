package domain;

import java.io.Serializable;
import java.util.*;

/**
 * Jugador de un juego de DAPOOS.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class Jugador implements Serializable {
	
	private String color, nombre;
	private int movimientos, turnos, comodinesActivados;
	protected char lado;
	protected ArrayList<Ficha> fichas = new ArrayList<Ficha>();
	
	/**
	 * Constructor para objetos de clase Jugador.
	 * @param nombre Nombre del jugador.
	 * @param color Color del jugador.
	 * @param lado Lado del tablero del jugador.
	 */
	public Jugador(String nombre, String color, char lado) {
		this.nombre = nombre;
		this.color = color;
		this.lado = lado;
		movimientos = 0;
		turnos = 0;
	}
	 
	/**
	 * Da las posibles capturas que puede hacer el jugador con las diferentes fichas.
	 * @return Retorna un ArrayList con arreglos donde está la ficha que hace la captura, la fila y la columna.
	 */
	public ArrayList<Object[]> posiblesCapturas() {
		ArrayList<Object[]> pc = new ArrayList<Object[]>();
		for (Ficha ficha:fichas) {
			for (int[] p:ficha.saltosPosibles()) {
				Object[] movimiento = {ficha, p[0], p[1]};
				pc.add(movimiento);
			}
		}
		return pc;
	}
	
	/**
	 * Dice si puede hacer alguna captura.
	 * @return Retorna la primera ficha que pueda hacer una captura.
	 */
	public Ficha posibleCaptura() {
		Ficha ficha = null;
		for (int i = 0; i < fichas.size() && ficha == null; i ++) {
			if (fichas.get(i).numeroSaltosPosibles() > 0) ficha = fichas.get(i);
		}
		return ficha;
	}
	
	/**
	 * Los movimientos que el el jugador puede realizar con sus diferentes fichas.
	 * @return Movimientos que pueden realizar.
	 */
	public ArrayList<Object[]> posiblesMovimientos() {
		ArrayList<Object[]> pm = new ArrayList<Object[]>();
		for (Ficha ficha:fichas) {
			for (int[] p:ficha.movimientosPosibles()) {
				Object[] movimiento = {ficha, p[0], p[1]};
				pm.add(movimiento);
			}
		}
		return pm;
	}
	
	/**
	 * Los movimientos que el el jugador puede realizar con sus diferentes fichas.
	 * @return Número de movimientos que pueden realizar.
	 */
	public int numeroPosiblesMovimientos() {
		return posiblesMovimientos().size();
	}
	
	/**
	 * Da las posiciones de las fichas del jugador contrario.
	 * @return ArrayList de arreglos donde el primer entero es la fila y el segundo la columna.
	 */
	public ArrayList<int[]> posiblesEliminaciones() {
		ArrayList<int[]> pe = new ArrayList<int[]>();
		if (fichas.size() > 0) {
			ArrayList<Ficha> fichasOponente = fichas.get(0).casilla().tablero().dapoos().oponente(lado).fichas();
			for (Ficha f:fichasOponente) {
				int[] p = {f.casilla().fila(), f.casilla().columna()};
            	pe.add(p);
			}
		}
		return pe;
	}
	
	/**
	 * Las fichas que tiene el jugador actualmente en le juego de DAPOOS.
	 * @return ArrayList con las fichas que tiene disponnibles el jugador.
	 */
	public ArrayList<Ficha> fichas() {
		return fichas;
	}
	
	/**
	 * Da el color que identifica al jugador en el juego.
	 * @return El color del jugador.
	 */
	public String color() {
		return color;
	}
	
	/**
	 * Da el nombre que identifica al jugador.
	 * @return El nombre del jugador.
	 */
	public String nombre() {
		return nombre;
	}
	
	/**
	 * Retorna el lado en el que esta ubicado el jugador en el tablero.
	 * @return 'n' si está en el lado norte o 's' si está en el lado sur.
	 */
	public char lado() {
		return lado;
	}
	
	/**
	 * Devuelve la cantidad de movimientos que ha hecho el jugado en el juego de DAPOOS.
	 * Un movimiento es cambiar una ficha de posición.
	 * @return Número de movimiento.
	 */
	public int movimientos() {
		return movimientos;
	}
	
	/**
	 * Da los turnos que ha tenido el jugador hasta ahora en el juego de DAPOOS.
	 * @return Número de turnos.
	 */
	public int turnos() {
		return turnos;
	}
	
	/**
	 * Retorna la cantidad de comodines que ha activado en el juego el juagdor.
	 * @return Número de comodines activados.
	 */
	public int comodinesActivados() {
		return comodinesActivados;
	}
	
	/**
	 * Suma uno al número de movimientos, representando que cambió de posición alguna ficha.
	 */
	public void nuevoMovimiento() {
		movimientos += 1;
	}
	
	/**
	 * Suma uno al número de turnos, representando que tuvo un turno nuevo.
	 */
	public void nuevoTurno() {
		turnos += 1;
	}
	
	/**
	 * Suma uno al número de comodines activados, representando que activo un comodín.
	 */
	public void nuevoComodinActivado() {
		comodinesActivados += 1;
	}
	
	/**
	 * Retorna la cantidad de fichas que actualmente tiene el jugador en la partida.
	 * @return Número de fichas del jugador en el tablero.
	 */
	public int numeroFichas() {
		return fichas.size();
	}
	 
	/**
	 * Devuelve la cantidad de fichas especiales que actualmente tiene el jugador en la partida.
	 * @return Número de fichas de tipo dama del jugador en el tablero.
	 */	
	public int numeroFichasEspeciales() {
		int n = 0;
		for (Ficha f:fichas) {
			if (!f.toString().contains("Peon")) n += 1;
		}
		return n;
	}
	
	/**
	 * Agrga una ficha al jugador.
	 * @param ficha Ficha que se le quiere agregar al jugador.
	 */
	public void agregarFicha(Ficha ficha) {
		fichas.add(ficha);
	}
	
	/**
	 * Elimina una ficha del jugador.
	 * @param ficha Ficha que se le quiere eliminar al jugador.
	 */
	public void eliminarFicha(Ficha ficha) {
		fichas.remove(ficha);
	}
	
	/**
	/**
	 * Cambia el color del jugador.
	 * @param color Nuevo color del jugador.
	 * @throws DAPOOSException SAME_COLOR, si el nuevo color es el mismo del oponente.
	 */
	public void cambiarColor(String color) throws DAPOOSException {
		if (fichas.size() > 0) {
			String colorOponente = fichas.get(0).casilla().tablero().dapoos().oponente(lado).color();
			if (colorOponente.equals(color)) throw new DAPOOSException(DAPOOSException.SAME_COLORS);
			this.color = color;
		}
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
