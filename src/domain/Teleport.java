package domain;

import java.util.ArrayList;

/**
 * Tipo de casilla que al colocar una ficha en ella, la ficha se teletransporta a una casilla vacia aleatoria en el tablero.
 * @author Angel Cuervo y Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class Teleport extends Casilla {
	
	/**
	 * Constructor para objetos de tipo Teleport.
	 * @param tablero Tablero en el que está la casilla Teleport.
	 * @param fila Fila del tablero en la que está posicionada la casilla Telepor.
	 * @param columna Columna del tablero en la que está posicionada la casilla Teleport.
	 */
	public Teleport(Tablero tablero, int fila, int columna) {
		super(tablero, fila, columna);
	}
	
	@Override
	public Comodin colocarFicha(Ficha ficha) throws DAPOOSException {
		if (!activo) activo = true;
		if (!visible) visible = true;
		ArrayList<Casilla> casillasVacias = tablero.casillasVacias();
		Casilla c = casillasVacias.get((int)(Math.random()*(casillasVacias.size())));
		Comodin comodin2 = c.colocarFicha(ficha);
		int fila = ficha.casilla().fila();
		if (((ficha.lado() == 'n' && fila == Tablero.TAMANOLADO - 1) ||
       		 (ficha.lado() == 's' && fila == 0)) && ficha instanceof Peon) {
			String[] fichasEspeciales = DAPOOS.FICHASESPECIALES;
			((Peon)ficha).cambiarTipo(fichasEspeciales[(int)(Math.random()*(fichasEspeciales.length))]);	
		}
		return comodin2;
	}
	
	@Override
	public String toString() {
		return "Teleport";
	}
}
