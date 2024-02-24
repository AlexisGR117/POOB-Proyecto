package domain;

/**
 * Tipo de comodín que al activarlo permite que una ficha propia pueda eliminar a una rival cayendo directamente sobre ella.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public class Stomp extends Comodin{

	/**
	 * Constructor para objetos de clase Stomp.
	 * @param casilla Casilla en la que va estar el comodín.
	 */
	public Stomp(Casilla casilla) {
		super(casilla);
	}

	@Override
	public void activar() {
		
	}

	@Override
	public String toString() {
		return "Stomp";
	}
}
