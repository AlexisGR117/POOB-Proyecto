package domain;

/**
 * Tipo de casilla que al colocar una ficha en ella la encarcela impidiendole salir durante dos turnos.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class Jail extends Casilla{
	
	private int turnos;

	/**
	 * Constructor para objetos de tipo Mine.
	 * @param tablero Tablero en el que está la casilla Mine.
	 * @param fila Fila del tablero en la que está posicionada la casilla Mine.
	 * @param columna Columna del tablero en la que está posicionada la casilla Mine.
	 */
	public Jail(Tablero tablero, int fila, int columna) {
		super(tablero, fila, columna);
	}
	
	@Override
	public Comodin colocarFicha(Ficha ficha) throws DAPOOSException {
		Comodin comodin = super.colocarFicha(ficha);
		turnos = ficha.jugador().turnos();
		return comodin;
	}
	
	@Override
	public boolean salir() {
		return (ficha.jugador().turnos() - turnos < 3 ? false : true);
	}

	@Override
	public String toString() {
		return "Jail";
	}
}
