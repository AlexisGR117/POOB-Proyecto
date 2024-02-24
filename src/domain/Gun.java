package domain;

/**
 * Tipo de comodín que al activarlo permite escoger y eliminar a una ficha rival.
 * @author Jefer Gonzalez
 * @version 1.0 (03/12/2022)
 */
public class Gun extends Comodin {

	/**
	 * Constructor para objetos de clase Gun.
	 * @param casilla Casilla en la que va estar el comodín.
	 */
	public Gun(Casilla casilla) {
		super(casilla);
	}

	@Override
	public void activar() throws DAPOOSException {
		Tablero tablero = casilla.tablero();
		tablero.seleccion().capturar();
		tablero.deseleeccionar();
		tablero.dapoos().cambiarTurno();	
		tablero.desactivarComodin();
	}
	
	@Override
	public String toString() {
		return "Gun";
	}
}
