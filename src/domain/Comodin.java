package domain;

import java.io.Serializable;

/**
 * Comodin de un juego de DAPOOS.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public abstract class Comodin implements Serializable {
	
	protected Casilla casilla;
	
	/**
	 * Constructor para objetos de clase Comodin.
	 * @param casilla Casilla en la que va estar el comodín.
	 */
	public Comodin(Casilla casilla) {
		this.casilla = casilla;
	}
	
	/**
	 * Activa el comodín.
	 * @throws DAPOOSException
	 */
	public abstract void activar() throws DAPOOSException ;
	
	/**
	 * Devuelve la casilla en la que se encuentra el comodín.
	 * @return La casilla del comodín.
	 */
	public Casilla casilla() {
		return casilla;
	}
	
	@Override
	public abstract String toString() ;
}
