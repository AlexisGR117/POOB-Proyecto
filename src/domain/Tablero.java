package domain;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Tablero del juego DAPOOS.
 * @author Jefer Gonzalez
 * @version 3.0 (10/12/2022)
 */
public class Tablero implements Serializable {
	
	public static final int TAMANOLADO = 10;
	private Casilla[][] casillas;
	private DAPOOS dapoos;
	private Ficha seleccion;
	private boolean bloqueado, tipoSeleccionado;
	private ArrayList<Zombie> zombiesEnterrados = new ArrayList<Zombie>();
	private Comodin comodinActivo;
	private Casilla ultimaCasilla;
	
	/**
	 * Constructor para objetos de clase Tablero.
	 * @param dapoos Juego de DAPOOS.
	 * @param porcentaje Porcentage de casillas especiales que va a tener el tablero.
	 * @throws DAPOOSException INVALID_SQUARE_TYPE, si el tipo de casilla dado no existe en el juego.
	 */
	public Tablero(DAPOOS dapoos, int porcentaje) throws DAPOOSException {
		this.dapoos = dapoos;
		casillas = new Casilla[TAMANOLADO][TAMANOLADO];
		Casilla casilla;
		for (int i = 0; i < TAMANOLADO; i++) for (int j = 0; j < TAMANOLADO; j++) {
			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
				casilla = new Casilla(this, i, j);
				if (i < TAMANOLADO/2 - 1) casilla.colocarFicha(new Peon(dapoos.jugadorNorte()));
				else if (i > TAMANOLADO/2) casilla.colocarFicha(new Peon(dapoos.jugadorSur()));
				casillas[i][j] = casilla;
			}
		}
		int p = (int)((double)TAMANOLADO*((double)porcentaje/(double)100));
		ArrayList<Casilla> vacias = casillasVacias();
		while (p > 0) {
			Casilla c = vacias.get((int)(Math.random()*(vacias.size())));
			int fila = c.fila(), columna = c.columna();
			String[] casillasEspeciales = DAPOOS.CASILLASESPECIALES;
			String tipoCasilla = casillasEspeciales[(int)(Math.random()*(casillasEspeciales.length))];
			try {
				Class<?> cls = Class.forName("domain." + tipoCasilla);
				Constructor<?>[] cons = cls.getConstructors();
				casillas[fila][columna] = (Casilla)cons[0].newInstance(this, fila, columna);	
			} catch (ReflectiveOperationException e) {
				throw new DAPOOSException(DAPOOSException.INVALID_SQUARE_TYPE);
			}
			vacias.remove(c);
			p--;
		}
	}
	
	/**
	 * Dice si una posicion dada está dentro del tablero.
	 * @param f Fila de la posición que se quiere verificar.
	 * @param c Columna de la posición que se quiere verificar.
	 * @return True si la posición esta dentro del tablero, de lo contario, False.
	 */
	public boolean dentro(int f, int c) {
		 return 0 <= f && f < TAMANOLADO && 0 <= c && c < TAMANOLADO;
	}
	
	/**
	 * Da las casillas que conforman el tablero.
	 * @return Una matriz con las casillas del tablero.
	 */
	public Casilla[][] casillas(){
		return casillas;
	}
	
	/**
	 * El juego DAPOOS el cual se está jugando en el tablero.
	 * @return Juego DAPOOS.
	 */
	public DAPOOS dapoos(){
		return dapoos;
	}
	
	/**
	 * Selecciona una ficha del tablero.
	 * @param ficha Ficha que se quiere seleccionar.
	 * @throws DAPOOSException 	INVALID_PLAYER, si no es el turno del jugador de la ficha que se seleccionada.
	 * 							ONE_TOKEN_PER_TURN, si se selecciona otra ficha cuando se acaba de capturar una ficha y se tiene otra posibilidad de capturar.
	 * 							PAUSED_GAME, si se intenta seleccionar una ficha mientras el juego está pausado.
	 */
	public void seleccionar(Ficha ficha) throws DAPOOSException {
		if (ficha != null && ficha.jugador != dapoos.turno() && !(comodinActivo instanceof Gun)) throw new DAPOOSException(DAPOOSException.INVALID_PLAYER);
		if (comodinActivo instanceof Gun && ficha.jugador() == dapoos.turno()) throw new DAPOOSException(DAPOOSException.NOT_OPONNENT_TOKEN);
		if (bloqueado && seleccion() != ficha) throw new DAPOOSException(DAPOOSException.ONE_TOKEN_PER_TURN);
		if (dapoos.dificultad().equals("QuickTime") && dapoos.pausado()) throw new DAPOOSException(DAPOOSException.PAUSED_GAME);
		seleccion = ficha;
		if (comodinActivo instanceof Gun) comodinActivo.activar();
	}
	
	/**
	 * Retorna la ficha que actualemnte está seleccionada en el tablero.
	 * @return Ficha que está seleccionada en el tablero.
	 */
	public Ficha seleccion() {
		return seleccion;
	}
	
	/**
	 * Deja de seleccionar la ficha actual.
	 */
	public void deseleeccionar() {
		seleccion = null;
	}
	
	/**
	 * Mueve la ficha que está seleccionada en el tablero a una posición dada.
	 * @param f Fila a la cual se quiere mover la ficha.
	 * @param c Columna a la cual se quiere mover la ficha.
	 * @return Valor booleano que indica si podía capturar y no lo hizo.
	 * @throws DAPOOSException 	OFF_THE_BOARD, si a la posición a la cual se quiere mover no está dentro del tablero.
	 * 							WHITE_SQUARE, si a la posición que se quiere mover es una casilla blanca.
	 * 							NOT_EMPTY, si en la posición dada hay una ficha.
	 *							NOT_SELECTED, si no se tiene seleccionada una ficha en el tablero.
	 *							INVALID_MOVEMENT, si la ficha no se puede mover a la posición dada.
	 *							INVALID_TOKEN_TYPE, si el tipo de ficha especial dado no está dentro de los establecidos que son: "Dama", "Ninja" y "Zombie".
	 * 							PAUSED_GAME, si se intenta seleccionar una ficha mientras el juego está pausado.
	 */
	public boolean moverSeleccion(int f, int c) throws DAPOOSException {
		if (!dentro(f, c)) throw new DAPOOSException(DAPOOSException.OFF_THE_BOARD);
		if (casillas[f][c] == null) throw new DAPOOSException(DAPOOSException.WHITE_SQUARE);
		if (casillas[f][c].ficha() != null && !(comodinActivo instanceof Stomp)) throw new DAPOOSException(DAPOOSException.NOT_EMPTY);
		if (seleccion == null) throw new DAPOOSException(DAPOOSException.NOT_SELECTED);
		if (dapoos.fichasActivas() && !tipoSeleccionado && seleccion instanceof Peon && ((seleccion.lado() == 'n' && f == Tablero.TAMANOLADO - 1) ||
		   (seleccion.lado() == 's' && f == 0))) throw new DAPOOSException(DAPOOSException.SELECT_TOKEN_TYPE);
		if (dapoos.dificultad().equals("QuickTime") && dapoos.pausado()) throw new DAPOOSException(DAPOOSException.PAUSED_GAME);
		Jugador jugador = seleccion.jugador();
		Ficha ficha = jugador.posibleCaptura();
		boolean captura = seleccion.mover(f,  c);
		seleccion.casilla().quitarFicha();
		jugador.nuevoMovimiento();
		comodinActivo = casillas[f][c].colocarFicha(seleccion);
		if (!dapoos.fichasActivas() && seleccion instanceof Peon && ((seleccion.lado() == 'n' && f == Tablero.TAMANOLADO - 1) ||
			(seleccion.lado() == 's' && f == 0))) ((Peon)seleccion).cambiarTipo("Dama");
		if (bloqueado) desbloquear();
		if (comodinActivo == null && (captura == false || (captura == true && seleccion.numeroSaltosPosibles() == 0))) {
			deseleeccionar();
			dapoos.cambiarTurno();
		} else if (comodinActivo != null) {
			jugador.nuevoComodinActivado();
			seleccion.casilla().quitarComodin();
			if (dapoos.dificultad().equals("QuickTime")) dapoos.reiniciarTiempo();
			deseleeccionar();
			if (comodinActivo instanceof OneMoreTime) comodinActivo.activar();
			if (dapoos.turno() instanceof Maquina && !(comodinActivo instanceof Gun)) ((Maquina)dapoos.turno()).movimientoAutomatico();
		} else {
			if (dapoos.dificultad().equals("QuickTime")) dapoos.reiniciarTiempo();
			bloquear();
			if (dapoos.turno() instanceof Maquina) ((Maquina)dapoos.turno()).movimientoAutomatico();
		}
		if (ficha != null && !captura) ficha.capturar();
		return ficha != null && !captura;
	}
	
	/**
	 * Mueve la ficha que está seleccionada en el tablero a una posición dada y si llega al otro lado cambia la ficha porel tipo de ficha especial dada.
	 * @param f Fila a la cual se quiere mover la ficha.
	 * @param c Columna a la cual se quiere mover la ficha.
	 * @param tipo Tipo especial de ficha por el que se quiere cambiar el peón.
	 * @return Valor booleano que indica si podía capturar y no lo hizo.
	 * @throws DAPOOSException 	OFF_THE_BOARD, si a la posición a la cual se quiere mover no está dentro del tablero.
	 * 							WHITE_SQUARE, si a la posición que se quiere mover es una casilla blanca.
	 * 							NOT_EMPTY, si en la posición dada hay una ficha.
	 *							NOT_SELECTED, si no se tiene seleccionada una ficha en el tablero.
	 *							INVALID_MOVEMENT, si la ficha no se puede mover a la posición dada.
	 *							INVALID_TOKEN_TYPE, si el tipo de ficha especial dado no está dentro de los establecidos que son: "Dama", "Ninja" y "Zombie".
	 *							SELECT_TOKEN_TYPE_DISABLE, si no está habilitadas las fichas especiales en el juego.
	 * 							PAUSED_GAME, si se intenta seleccionar una ficha mientras el juego está pausado.
	 */
	public boolean moverSeleccion(int f, int c, String tipo) throws DAPOOSException {
		if(!dapoos.fichasActivas()) throw new DAPOOSException(DAPOOSException.SELECT_TOKEN_TYPE_DISABLE);
		Ficha ficha = seleccion;
		tipoSeleccionado = true;
		boolean m = moverSeleccion(f, c);
		if (ficha instanceof Peon && ((ficha.lado() == 'n' && f == Tablero.TAMANOLADO - 1) ||
			(ficha.lado() == 's' && f == 0))) ((Peon)ficha).cambiarTipo(tipo);
		tipoSeleccionado = false;
		return m;
	}
	
	/**
	 * Bloquea el tablero, lo cual significa que una ficha ha capturado y tiene la posibilidad de volver a capturar.
	 * Por lo tanto, no se cambia de turno y no se permite mover otras fichas que no sea la que ha capturado.
	 */
	public void bloquear() {
		bloqueado = true;
	}
	
	/**
	 * Desbloquea el tablero, permitiendo cambiar de turno y seleccionar otras fichas.
	 */
	public void desbloquear() {
		bloqueado = false;
	}
	
	/**
	 * Dice si el tablero está bloqueado.
	 * @return True si está blouqeado, de lo contrario False.
	 */
	public boolean bloqueado() {
		return bloqueado;
	}
	
	/**
	 * Retorna las casillas que no son blancas y no tienen fichas.
	 * @return Un ArrayList con las casillas vacías.
	 */
	public ArrayList<Casilla> casillasVacias() {
		ArrayList<Casilla> casillasVacias = new ArrayList<Casilla>();
		for (Casilla[] cf: casillas) for (Casilla c: cf) {
			if (c != null && c.ficha() == null) casillasVacias.add(c);
		}
		return casillasVacias;
	}
	
	/**
	 * Da las casillas que son normales, que no son blancas y están sin ficha.
	 * @return Un ArrayList con las casillas normales vacías.
	 */
	public ArrayList<Casilla> casillasNormalesVacias() {
		ArrayList<Casilla> casillasVacias = new ArrayList<Casilla>();
		for (Casilla[] cf: casillas) for (Casilla c: cf) {
			if (c != null && c.ficha() == null && c.toString().equals("Casilla") && c.comodin() == null) casillasVacias.add(c);
		}
		return casillasVacias;
	}
	
	/**
	 * Da la ficha de la casilla ubicada en la posición dada
	 * @param f Fila de la casilla en la que está la ficha.
	 * @param c Columna de la casilla en la que está la ficha.
	 * @return La ficha que está en la posición dada.
	 */
	public Ficha ficha(int f, int c) {
		return (casillas[f][c] != null ? casillas[f][c].ficha() : null);
	}
	
	/**
	 * Da la casilla ubicada en la posición dada
	 * @param f Fila de la casilla.
	 * @param c Columna de la casilla.
	 * @return La casilla que está en la posición dada.
	 */
	public Casilla casilla(int f, int c) {
		return casillas[f][c];
	}
	
	/**
	 * Desentierra las fichas zombies que están enterradas en el tablero.
	 * @throws DAPOOSException
	 */
	public void desenterrarZombies() throws DAPOOSException {
		ArrayList<Zombie> zombies = new ArrayList<Zombie>(zombiesEnterrados);
		for (Zombie z:zombies) {
			if (z.desenterrar()) zombiesEnterrados.remove(z);
		}
	}
	
	/**
	 * Entierra una ficha zombie en el tablero.
	 * @param zombie Ficha zombie que se va enterrar.
	 */
	public void enterrarZombie(Zombie zombie) {
		zombiesEnterrados.add(zombie);
	}
	
	/**
	 * Retorna comodín que actualmente está activo en el tablero.
	 * @return El comodín que está activo.
	 */
	public Comodin comodinActivo(){
		return comodinActivo;
	}
	
	/**
	 * Desactiva el comodín que estaba activo.
	 */
	public void desactivarComodin(){
		comodinActivo = null;
	}
	
	/**
	 * Devuelve la última casilla en la que se colocó una ficha.
	 * @return Última casilla.
	 */
	public Casilla ultimaCasilla() {
		return ultimaCasilla;
	}
	
	/**
	 * Cambia la última casilla en la que se colocó una ficha del tablero.
	 * @param casilla Última casilla.
	 */
	public void cambiarUltimaCasilla(Casilla casilla) {
		ultimaCasilla = casilla;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Casilla[] c:casillas) {
			s += Arrays.toString(c) + "\n";
		}
		return s;
	}
}
