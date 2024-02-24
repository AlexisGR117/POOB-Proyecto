package domain;

/**
 * Tipo de jugador que se mueve automaticamente.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public abstract class Maquina extends Jugador {
	
	/**
	 * Constructor para objetos de clase Maquina.
	 * @param color Color de las fichas del jugador.
	 * @param lado Lado del jugador.
	 */
	public Maquina(String color, char lado) {
		super("Máquina", color, lado);
	}
	
	/**
	 * Hace un movimiento automático en el juego.
	 * @throws DAPOOSException
	 */
	public abstract void movimientoAutomatico() throws DAPOOSException;
}
