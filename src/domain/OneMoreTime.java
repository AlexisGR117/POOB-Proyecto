package domain;

/**
 * Tipo de comodín que al activarlo le da 100% más de tiempo en la jugada (solo aparece en el modo Quicktime).
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public class OneMoreTime extends Comodin {

	/**
	 * Constructor para objetos de clase OneMoreTime.
	 * @param casilla Casilla en la que va estar el comodín.
	 */
	public OneMoreTime(Casilla casilla) {
		super(casilla);
	}
	
	@Override
	public void activar() {
		casilla.tablero().dapoos().aumentarTiempo();
	}
	
	@Override
	public String toString() {
		return "OneMoreTime";
	}
}
