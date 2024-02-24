package domain;

/**
 * Tipo de casilla que al colocar una ficha en ella explota eliminando las fichas que están en un area 3x3 alrededor de ella.
 * @author Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class Mine extends Casilla{
	
	/**
	 * Constructor para objetos de tipo Mine.
	 * @param tablero Tablero en el que está la casilla Mine.
	 * @param fila Fila del tablero en la que está posicionada la casilla Mine.
	 * @param columna Columna del tablero en la que está posicionada la casilla Mine.
	 */
	public Mine(Tablero tablero, int fila, int columna) {
		super(tablero, fila, columna);
	}

	@Override
	public Comodin colocarFicha(Ficha ficha) throws DAPOOSException {
		Comodin comodin = super.colocarFicha(ficha);
		ficha.capturar();
		for (int df=-1; df<2;df++) for (int dc=-1; dc<2;dc++) {
			if (df != 0 && dc != 0 && tablero.dentro(fila+df, columna+dc) && tablero.ficha(fila+df, columna+dc) != null) {
				tablero.ficha(fila+df, columna+dc).capturar();
			}
		}
		return comodin;
	}
	
	@Override
	public String toString() {
		return "Mine";
	}
	
}
