package domain;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Versión modificada del juego clasico de mesa para dos jugadores, Damas.
 * @author Jefer Gonzalez
 * @version 3.0 (10/12/2022)
 */
public class DAPOOS implements Serializable {
	
	private Jugador[] jugadores;
	private Tablero tablero;
	private Jugador turno, ganador;
	private int segundos, tiempo, porcentaje, puntaje;
	private String dificultad, visualizacion, tipoMaquina;
	public static final String[] FICHASESPECIALES = {"Dama", "Ninja", "Zombie"};
	public static final String[] CASILLASESPECIALES = {"Mine", "Jail", "Teleport"};
	public static final String[] COMODINES = {"Gun", "Stomp", "OneMoreTime"};
	private boolean comodinesActivos, fichasActivas, pausado;
	
	/**
	 * Constructor para objetos de clase DAPOOS.
	 * @param nombreUno Nombre del jugador norte.
	 * @param nombreDos Nombre del jugador sur.
	 * @param colorUno Color de las fichas del jugador norte.
	 * @param colorDos Color de las fichas del jugador sur
	 * @param comodinesActivos Valor booleano que dice si el juego va a tener comodines.
	 * @param fichasActivas Valor booleano que dice si el juego va a fichas especiales aparte de las Damas.
	 * @param porcentaje Porcentaje de casillas especiales debe ser un valor entero entre 0 y 100.
	 * @param visualizacion Tipo de visualización de las casillas especiales puede ser: "Relámpago" o "Permanente".
	 * @throws DAPOOSException	LONG_NAME, si alguno de los nombres tiene longitud mayor a 14.
	 * 						   	SHORT_NAME, si alguno de los nombres tiene longitud menor a 3.
	 * 							SAME_NAME, si los nombres de los juagdores son iguales.
	 * 							SAME_COLORS, si los colres de los jugadores son iguales.
	 * 							INVALID_PERCENTAGE, si el porcentage no es mayor a cero y menor que 100.
	 * 							INVALID_VISUALIZATION, si la visualización dada no está entre las establecidad que son: "Permanente" y "Relámpago".
	 * 							INVALID_SQUARE_TYPE, si el tipo de casilla no está entre las establecidad que son: "Jail", "Teleport" y "Mine".
	 */
	public DAPOOS(String nombreUno, String nombreDos, String colorUno, String colorDos, boolean comodinesActivos, boolean fichasActivas, int porcentaje, String visualizacion) throws DAPOOSException {
		if (nombreUno.length() > 14 || nombreDos.length() > 14) throw new DAPOOSException(DAPOOSException.LONG_NAME);
		if (nombreUno.length() < 3 || nombreDos.length() < 3) throw new DAPOOSException(DAPOOSException.SHORT_NAME);
		if (nombreUno.equals(nombreDos)) throw new DAPOOSException(DAPOOSException.SAME_NAMES);
		if (colorUno.equals(colorDos)) throw new DAPOOSException(DAPOOSException.SAME_COLORS);
		if (porcentaje < 0 || porcentaje > 100) throw new DAPOOSException(DAPOOSException.INVALID_PERCENTAGE);
		if (!(visualizacion.equals("Permanente") || visualizacion.equals("Relámpago"))) throw new DAPOOSException(DAPOOSException.INVALID_VISUALIZATION);
		jugadores = new Jugador[2];
		jugadores[0] = new Jugador(nombreUno, colorUno, 'n');
		jugadores[1] = new Jugador(nombreDos, colorDos, 's');
		tablero = new Tablero(this, porcentaje);
		turno = jugadores[1];
		ganador = null;
		this.comodinesActivos = comodinesActivos;
		this.fichasActivas = fichasActivas;
		this.visualizacion = visualizacion;
		this.porcentaje = porcentaje;
		dificultad = "Normal";
 	}
	
	/**
	 * Constructor para objetos de clase DAPOOS.
	 * @param nombreUno Nombre del jugador norte.
	 * @param nombreDos Nombre del jugador sur.
	 * @param colorUno Color de las fichas del jugador norte.
	 * @param colorDos Color de las fichas del jugador sur.
	 * @param comodinesActivos Valor booleano que indica si el juego va a tener comodines.
	 * @param fichasActivas Valor booleano que inidca si el juego va a tener fichas especiales aparte de las Damas.
	 * @param porcentaje Porcentaje de casillas especiales debe ser un valor entero entre 0 y 100.
	 * @param visualizacion Tipo de visualización de las casillas especiales puede ser: "Relámpago" o "Permanente".
	 * @param dificultad Dificultad del juego puede ser "Normal" o "QuickTime".
	 * @param tiempo Tiempo en segundos de cada turno, valido para la dificultad "QuickTime", puede ser un valor entre 0 y 100.
	 * @throws DAPOOSException	LONG_NAME, si alguno de los nombres tiene longitud mayor a 14.
	 * 						   	SHORT_NAME, si alguno de los nombres tiene longitud menor a 3.
	 * 							SAME_NAME, si los nombres de los juagdores son iguales.
	 * 							SAME_COLORS, si los colres de los jugadores son iguales.
	 * 							INVALID_PERCENTAGE, si el porcentage no es mayor a cero y menor que 100.
	 * 							INVALID_VISUALIZATION, si la visualización dada no está entre las establecidad que son: "Permanente" y "Relámpago".
	 * 							INVALID_SQUARE_TYPE, si el tipo de casilla no está entre las establecidad que son: "Jail", "Teleport" y "Mine".
	 * 							INVALID_DIFFICULTY, si la dificultad dad no esta entre las establecidas que son "Normal" y "QuickTime".
	 * 							INVALID_TIME_TURN, si el modo es "QuickTime" y el tiempo de cada turno no está entre 0 y 100. 
	 */
	public DAPOOS(String nombreUno, String nombreDos, String colorUno, String colorDos, boolean comodinesActivos, boolean fichasActivas, int porcentaje, String visualizacion, String dificultad, int tiempo) throws DAPOOSException {
		this(nombreUno, nombreDos, colorUno, colorDos, comodinesActivos, fichasActivas, porcentaje, visualizacion);
		if (!(dificultad.equals("Normal") || dificultad.equals("QuickTime"))) throw new DAPOOSException(DAPOOSException.INVALID_DIFFICULTY);
		if (dificultad.equals("QuickTime") && (tiempo < 0 || tiempo > 100)) throw new DAPOOSException(DAPOOSException.INVALID_TIME_TURN);
		this.tiempo = tiempo;
		this.dificultad = dificultad;
    	if (dificultad.equals("QuickTime")) colocarTemporizador();
 	}
	
	/**
	 * Constructor para objetos de clase DAPOOS.
	 * @param nombreUno Nombre del jugador norte.
	 * @param nombreDos Nombre del jugador sur.
	 * @param colorUno Color de las fichas del jugador norte.
	 * @param colorDos Color de las fichas del jugador sur.
	 * @param tipoMaquina Tipo de máquina que va a jugar contra el usuario.
	 * @param comodinesActivos Valor booleano que indica si el juego va a tener comodines.
	 * @param fichasActivas Valor booleano que inidca si el juego va a tener fichas especiales aparte de las Damas.
	 * @param porcentaje Porcentaje de casillas especiales debe ser un valor entero entre 0 y 100.
	 * @param visualizacion Tipo de visualización de las casillas especiales puede ser: "Relámpago" o "Permanente".
	 * @throws DAPOOSException	SELECT_MACHINE_PLAYER, si no se estableció que jugador va ser la maquina.
	 * 							LONG_NAME, si alguno de los nombres tiene longitud mayor a 14.
	 * 						   	SHORT_NAME, si alguno de los nombres tiene longitud menor a 3.
	 * 							SAME_NAME, si los nombres de los juagdores son iguales.
	 * 							SAME_COLORS, si los colres de los jugadores son iguales.
	 * 							INVALID_PERCENTAGE, si el porcentage no es mayor a cero y menor que 100.
	 * 							INVALID_VISUALIZATION, si la visualización dada no está entre las establecidad que son: "Permanente" y "Relámpago".
	 * 							INVALID_MACHINE_TYPE, si el tipo de máquina dado no es "Principiante" o "Aprendiz".
	 * 							INVALID_SQUARE_TYPE, si el tipo de casilla no está entre las establecidad que son: "Jail", "Teleport" y "Mine".
	 */
	public DAPOOS(String nombreUno, String nombreDos, String colorUno, String colorDos, String tipoMaquina, boolean comodinesActivos, boolean fichasActivas, int porcentaje, String visualizacion) throws DAPOOSException {
		if (!(nombreUno.equals("Máquina") || nombreDos.equals("Máquina"))) throw new DAPOOSException(DAPOOSException.SELECT_MACHINE_PLAYER);
		if (nombreUno.length() > 14 || nombreDos.length() > 14) throw new DAPOOSException(DAPOOSException.LONG_NAME);
		if (nombreUno.length() < 3 || nombreDos.length() < 3) throw new DAPOOSException(DAPOOSException.SHORT_NAME);
		if (nombreUno.equals(nombreDos)) throw new DAPOOSException(DAPOOSException.SAME_NAMES);
		if (colorUno.equals(colorDos)) throw new DAPOOSException(DAPOOSException.SAME_COLORS);
		if (porcentaje < 0 || porcentaje > 100) throw new DAPOOSException(DAPOOSException.INVALID_PERCENTAGE);
		if (!(visualizacion.equals("Permanente") || visualizacion.equals("Relámpago"))) throw new DAPOOSException(DAPOOSException.INVALID_VISUALIZATION);
		jugadores = new Jugador[2];
		if (nombreUno.equals("Máquina")) {
			try {
				Class<?> cls = Class.forName("domain." + tipoMaquina);
				Constructor<?>[] cons = cls.getConstructors();
				jugadores[0] = (Maquina)cons[0].newInstance(colorUno, 'n');	
			} catch (ReflectiveOperationException e) {
				throw new DAPOOSException(DAPOOSException.INVALID_MACHINE_TYPE);
			}
		} else jugadores[0] = new Jugador(nombreUno, colorUno, 'n');
		if (nombreDos.equals("Máquina")) {
			try {
				Class<?> cls = Class.forName("domain." + tipoMaquina);
				Constructor<?>[] cons = cls.getConstructors();
				jugadores[1] = (Maquina)cons[0].newInstance(colorDos, 's');	
			} catch (ReflectiveOperationException e) {
				throw new DAPOOSException(DAPOOSException.INVALID_MACHINE_TYPE);
			}
		} else jugadores[1] = new Jugador(nombreDos, colorDos, 's');
		tablero = new Tablero(this, porcentaje);
		turno = jugadores[1];
		ganador = null;
		this.comodinesActivos = comodinesActivos;
		this.fichasActivas = fichasActivas;
		this.visualizacion = visualizacion;
		this.tipoMaquina = tipoMaquina;
		this.porcentaje = porcentaje;
		dificultad = "Normal";
		if (jugadores[1] instanceof Maquina) ((Maquina)jugadores[1]).movimientoAutomatico();
 	}
	
	/**
	 * Constructor para objetos de clase DAPOOS.
	 * @param nombreUno Nombre del jugador norte.
	 * @param nombreDos Nombre del jugador sur.
	 * @param colorUno Color de las fichas del jugador norte.
	 * @param colorDos Color de las fichas del jugador sur.
	 * @param porcentaje Porcentaje de casillas especiales debe
	 * @param tipoMaquina Tipo de máquina que va a jugar contra el usuario.
	 * @param comodinesActivos Valor booleano que indica si el juego va a tener comodines.
	 * @param fichasActivas Valor booleano que inidca si el juego va a tener fichas especiales aparte de las Damas.
	 * @param porcentaje Porcentaje de casillas especiales debe ser un valor entero entre 0 y 100.
	 * @param visualizacion Tipo de visualización de las casillas especiales puede ser: "Relámpago" o "Permanente"
	 * @param visualizacion Tipo de visualización de las casillas especiales puede ser: "Relámpago" o "Permanente".
	 * @param dificultad Dificultad del juego puede ser "Normal" o "QuickTime".
	 * @param tiempo Tiempo en segundos de cada turno, valido para la dificultad "QuickTime", puede ser un valor entre 0 y 100.
	 * @throws DAPOOSException	SELECT_MACHINE_PLAYER, si no se estableció que jugador va ser la maquina.
	 * 							LONG_NAME, si alguno de los nombres tiene longitud mayor a 14.
	 * 						   	SHORT_NAME, si alguno de los nombres tiene longitud menor a 3.
	 * 							SAME_NAME, si los nombres de los juagdores son iguales.
	 * 							SAME_COLORS, si los colres de los jugadores son iguales.
	 * 							INVALID_PERCENTAGE, si el porcentage no es mayor a cero y menor que 100.
	 * 							INVALID_VISUALIZATION, si la visualización dada no está entre las establecidad que son: "Permanente" y "Relámpago".
	 * 							INVALID_MACHINE_TYPE, si el tipo de máquina dado no es "Principiante" o "Aprendiz".
	 * 							INVALID_SQUARE_TYPE, si el tipo de casilla no está entre las establecidad que son: "Jail", "Teleport" y "Mine".
	 * 							INVALID_DIFFICULTY, si la dificultad dad no esta entre las establecidas que son "Normal" y "QuickTime".
	 * 							INVALID_TIME_TURN, si el modo es "QuickTime" y el tiempo de cada turno no está entre 0 y 100. 
	 */
	public DAPOOS(String nombreUno, String nombreDos, String colorUno, String colorDos, String tipoMaquina, boolean comodinesActivos, boolean fichasActivas, int porcentaje, String visualizacion, String dificultad, int tiempo) throws DAPOOSException {
		this(nombreUno, nombreDos, colorUno, colorDos, tipoMaquina, comodinesActivos, fichasActivas, porcentaje, visualizacion);
		if (!(dificultad.equals("Normal") || dificultad.equals("QuickTime"))) throw new DAPOOSException(DAPOOSException.INVALID_DIFFICULTY);
		if (dificultad.equals("QuickTime") && (tiempo < 0 || tiempo > 100)) throw new DAPOOSException(DAPOOSException.INVALID_TIME_TURN);
		this.tiempo = tiempo;
		this.dificultad = dificultad;
		this.tipoMaquina = tipoMaquina;
    	if (dificultad.equals("QuickTime")) colocarTemporizador();
 	}
	
	/**
	 * Coloca el temporizador para el modo Quicktime.
	 */
	public void colocarTemporizador() {
		Timer timer = new Timer();
		segundos = this.tiempo + 2;
        timer.scheduleAtFixedRate(new TimerTask() {	
        	public void run() {
        		segundos--;
        		if (pausado || ganador != null) cancel();
        		if (segundos < 0) {
        			try {
						cambiarTurno();
						if (tablero.bloqueado()) tablero.desbloquear();
					} catch (DAPOOSException e) {}
        		}		
        	}
        }, 0, 1000);
	}
	
	/**
	 * Da el tablero del juego de DAPOOS.
	 * @return El tablero del juego.
	 */
	public Tablero tablero() {
		return tablero;
	}
	
	/**
	 * Retorna el jugador norte del juego de DAPOOS.
	 * @return El jugador del lado norte del tablero.
	 */
	public Jugador jugadorNorte() {
		return jugadores[0];
	}
	
	/**
	 * Retorna el jugador sur del juego de DAPOOS.
	 * @return El jugador del lado sur del tablero.
	 */
	public Jugador jugadorSur() {
		return jugadores[1];
	}
	
	/**
	 * Da el jugador que puede mover actualmente.
	 * @return El jugador que tiene el turno actual.
	 */
	public Jugador turno() {
		return turno;
	}
	
	/**
	 * Da el oponente de un jugador.
	 * @param lado El lado del jugador al cual se quiere saber su oponente.
	 * @return El oponente.
	 */
	public Jugador oponente(char lado) {
		Jugador jugador;
		if (lado == 'n') jugador = jugadores[1];
		else jugador = jugadores[0];
		return jugador;
	}
	
	/**
	 * Si el turno lo tenía el jugador norte, este pasa al juagdor sur, si no, todo lo contario.
	 * @throws DAPOOSException 	INVALID_PLAYER, si no es el turno del jugador de la ficha que se seleccionada.
	 * 							ONE_TOKEN_PER_TURN, si se selecciona otra ficha cuando se acaba de capturar una ficha y se tiene otra posibilidad de capturar.
	 * 							INPUT_OUTPUT_ERROR, si ha ocurrido un error en la salida.
	 */
	public void cambiarTurno() throws DAPOOSException {
		tablero.desactivarComodin();
		turno.nuevoTurno();
		if (turno == jugadores[0]) turno = jugadores[1];
		else turno = jugadores[0];
		if (ganador == null) {
			if (dificultad.equals("QuickTime")) {
				reiniciarTiempo();
				if (segundos == 0) tablero.seleccionar(null);
			}
			tablero.desenterrarZombies();
			if (comodinesActivos && (jugadores[0].turnos() + jugadores[1].turnos()) % 5 == 0) {
				try {
					ArrayList<Casilla> vacias = tablero.casillasNormalesVacias();
					if (vacias.size() > 0) {
						String tipoComodin;
						if (dificultad.equals("QuickTime")) tipoComodin = COMODINES[(int)(Math.random()*(COMODINES.length))];
						else tipoComodin = COMODINES[(int)(Math.random()*(COMODINES.length-1))];
						Class<?> cls = Class.forName("domain." + tipoComodin);
						Constructor<?>[] cons = cls.getConstructors();
						Casilla casilla = vacias.get((int)(Math.random()*(vacias.size())));
						Comodin comodin = (Comodin)cons[0].newInstance(casilla);	
						casilla.colocarComodin(comodin);
					}
				} catch (ReflectiveOperationException e) {
					throw new DAPOOSException(DAPOOSException.INVALID_JOKER);
				}
			}
			ganar();
			if (turno instanceof Maquina && ganador == null) ((Maquina)turno).movimientoAutomatico();
		}
	}
	
	/**
	 * Reinicia el tiempo del turno.
	 */
	public void reiniciarTiempo() {
		segundos = tiempo;
	}
	
	/**
	 * Dice si ya ganó alguien, ya sea por que se quedo sin fichas o sin movimientos.
	 * @return False si aún no ha ganado nadie, True si alguien ya ganó.
	 * @throws DAPOOSException INPUT_OUTPUT_ERROR, si ha ocurrido un error en la salida.
	 */
	public boolean ganar() throws DAPOOSException {
		boolean g = false;
		if (jugadores[0].numeroFichas() == 0 || (turno == jugadores[0] && jugadores[0].numeroPosiblesMovimientos() == 0)) {
			g = true;
			ganador = jugadores[1]; 
		}
		if (jugadores[1].numeroFichas() == 0 || (turno == jugadores[1] && jugadores[1].numeroPosiblesMovimientos() == 0)) {
			g = true;
			ganador = jugadores[0]; 
		}
		if (g) {
			puntaje = calcularPuntaje();
			registrarPuntaje(ganador.nombre(), puntaje);
		}
		return g;
	}
	
	/**
	 * Calcula el puntaje del ganador.
	 * @return Número que representa el puntaje obtenido por el ganador.
	 */
	public int calcularPuntaje() {
		int puntaje = ganador.numeroFichasEspeciales()*5+ganador.numeroFichas()*4;
		puntaje += ganador.comodinesActivados()*3-ganador.movimientos();
		return puntaje;
	}
	
	/**
	 * Registra el puntaje en un archivo .txt
	 * @param nombre String con el nombre del ganador.
	 * @param puntaje Puntaje obtenido en la partida.
	 * @throws DAPOOSException INPUT_OUTPUT_ERROR, si ha ocurrido un error en la salida.
	 */
	public static void registrarPuntaje(String nombre, int puntaje) throws DAPOOSException {
		try {
			FileReader fileReader = new FileReader("Puntuaciones.txt");
			BufferedReader reader = new BufferedReader(fileReader);
			String[][] information = new String[10][2];
			String[] puntuacion;
			String line = reader.readLine();
			String[] nuevaPuntuacion = {nombre, puntaje+""};
			int c = 0;
			boolean colocado = false;
			if (line == null) information[c] = nuevaPuntuacion;
			while (line != null && c < 10) {
				puntuacion = line.trim().split(" ");
				if (Integer.parseInt(puntuacion[1]) >= puntaje || colocado) information[c] = puntuacion;
				else {
					information[c] = nuevaPuntuacion;
					if (c + 1 < 10)	information[c+1] = puntuacion;
					colocado = true;
				}
				line = reader.readLine();
				c ++;
			}
			if (!colocado && c < 10) information[c] = nuevaPuntuacion;
			reader.close();	
			File puntuaciones = new File("Puntuaciones.txt");
			puntuaciones.delete();
			File nuevasPuntuaciones = new File("Puntuaciones.txt");
			FileWriter writer = new FileWriter(nuevasPuntuaciones, true);	 
			for (int i = 0; i < information.length; i++) {
				if (i != information.length -1) writer.write(information[i][0] + " " + information[i][1] + "\n");
				else writer.write(information[i][0] + " " + information[i][1]);
			}
			writer.close();			
		} catch (IOException e) {
			throw new DAPOOSException(DAPOOSException.INPUT_OUTPUT_ERROR);
		}
	  }
	
	/**
	 * El ganador del juego de DAPOOS.
	 * @return El jugador que gano el juego.
	 */
	public Jugador ganador() {
		return ganador;
	}
	
	/**
	 * Retorna los segundos que quedan actualmente del turno.
	 * @return Número de segundos que quedan del turno.
	 */
	public int segundos() {
		return segundos;
	}
	
	/**
	 * Devuelve el tiempo que dura cada turno en el juego.
	 * @return Número que representa el timepo en segundos.
	 */
	public int tiempo() {
		return tiempo;
	}
	
	/**
	 * Da la dificultad del juego.
	 * @return Dificultad del juego.
	 */
	public String dificultad() {
		return dificultad;
	}
	
	/**
	 * Retorna la visualización de las casillas especialese en el juego.
	 * @return Visualización de las casilla especiales.
	 */
	public String visualizacion() {
		return visualizacion;
	}
	
	/**
	 * Dupica el tiempo del turno.
	 */
	public void aumentarTiempo() {
		segundos = segundos * 2;
	}
	   
	/**
	 * Dice si las fichas especiales están activas en el juego.
	 * @return True si están activas las fichas especiales, de lo contario, false.
	 */
	public boolean fichasActivas() {
		return fichasActivas;
	}
	   
	/**
	 * Dice si los comodines están activas en el juego.
	 * @return True si están activos los comodines, de lo contario, false.
	 */
	public boolean comodinesActivos() {
		return comodinesActivos;
	}	
	   
	/**
	 * Retorna el tipo de maquina del juego.
	 * @return Tipo de maquina del juego.
	 */
	public String tipoMaquina() {
		return tipoMaquina;
	}	

	/**
	 * Da el porcentaje de casilla especiales del juego.
	 * @return Número enetro que representa el porcentaje de casillas especiales.
	 */
	public int porcentaje() {
		return porcentaje;
	 }	
	   
	/**
	 * Si estan activas las fichas especiales las desactiva, de lo contrario las activa.
	 */
	public void cambiarEstadoFichas() {
		fichasActivas = !fichasActivas;
	}	

	/**
	 * Si estan activos los comodines los desactiva, de lo contrario los activa.
	 */
	public void cambiarEstadoComodines() {
		comodinesActivos = !comodinesActivos;
	}	
	   
	/**
	  * Si no está pausado el juego detiene el temporizador, de lo contrario o reanuda.
	  * @throws DAPOOSException PAUSE_ONLY_IN_QUICKTIME, si el juego no tiene dificultad QuickTime.
	  */
	public void cambiarEstadoPausado() throws DAPOOSException {
		if (!dificultad.equals("QuickTime")) throw new DAPOOSException(DAPOOSException.PAUSE_ONLY_IN_QUICKTIME);
		if (pausado) {
			int tiempo2 = tiempo;
			tiempo = segundos;
			colocarTemporizador();
			tiempo = tiempo2;
		} 
		pausado = !pausado;
	}
	   
	   /**
	    * Dice si el juego está pausado.
	    * @return True si está pausado, de lo contrario, false.
	    */
	   public boolean pausado() {
		   return pausado;
	   }
	   
	   /**
	    * Da el puntaje que obtuvo el ganador.
	    * @return Puntaje del ganador.
	    */
	   public int puntaje() {
		   return puntaje;
	   }
	   
	   /**
	    * Hace que se rinda el jugador que actualmente tiene el turno.
	    * @throws DAPOOSException INPUT_OUTPUT_ERROR, si ha ocurrido un error en la salida. 
	    */
	   public void rendirse() throws DAPOOSException {
		   ganador = oponente(turno.lado());
		   puntaje = calcularPuntaje();
		   registrarPuntaje(ganador.nombre(), puntaje);
	   }
	   
	   /**
	    * Abre un archivo que contiene la información de una una partida de DAPOOS.
	    * @param file Archivo que se desea abrir.
	    * @return Una red bidimensional AManufacturing.
	    * @throws DAPOOSException FILE_NOT_FOUND, si no se ha encontrado el archivo.
	    * 							 STREAM_CORRUPTED, si el archivo está corrupto.
	    * 							 INVALID_OBJECT, si uno o más objetos deserializados fallaron las pruebas de validación.
	    * 							 OPTIONAL_DATA, si durante la lectura de un objeto hubo datos primitivos no leídos o al final de los datos pertenecientes a un objeto serializado en el flujo.
	    * 							 NOT_ACTIVE, si la deserialización no está activa.
	    * 							 EOF, si se ha alcanzado inesperadamente un final de archivo o final de secuencia durante la entrada.
	    * 							 INPUT_OUTPUT_ERROR, si ha ocurrido un error en la entrada.
	    * 							 OPEN_ERROR, si hubo un error al abrir el archivo.
	    */
	   public static DAPOOS abra(File file) throws DAPOOSException {
			try {
				FileInputStream fileInput = new FileInputStream(file);
				ObjectInputStream objectInput = new ObjectInputStream(fileInput);
				DAPOOS dapoos = (DAPOOS)objectInput.readObject();
				if (dapoos.dificultad().equals("QuickTime")) dapoos.colocarTemporizador();
				objectInput.close();
				return dapoos;
			} catch (FileNotFoundException e) {
	            throw new DAPOOSException(DAPOOSException.FILE_NOT_FOUND);
	        } catch (StreamCorruptedException e) {
	            throw new DAPOOSException(DAPOOSException.STREAM_CORRUPTED);
	        } catch (InvalidObjectException e) {
	            throw new DAPOOSException(DAPOOSException.INVALID_OBJECT);
	        } catch (OptionalDataException e) {
	            throw new DAPOOSException(DAPOOSException.OPTIONAL_DATA);
	        } catch (NotActiveException e) {
	            throw new DAPOOSException(DAPOOSException.NOT_ACTIVE);
	        } catch (EOFException e) {
	            throw new DAPOOSException(DAPOOSException.EOF);
	        } catch (IOException e) {
	            throw new DAPOOSException(DAPOOSException.INPUT_OUTPUT_ERROR);
	        } catch (Exception e) {
				throw new DAPOOSException(DAPOOSException.OPEN_ERROR);
	        } 
	   }
	   
	   /**
	    * Guarda un archivo con la información de una partida den DAPOOS.
	    * @param file Archivo en el que se desea guardar.
	    * @throws DAPOOSException FILE_NOT_FOUND, si no se ha encontrado el archivo.
	    * 							 INVALID_CLASS, si el tiempo de ejecución de serialización a detectaso un problemas con una clase.
	    * 							 NOT_SERIALIZABLE, si una de las clases no es serializable.
	    * 							 NOT_ACTIVE, si la serialización no está activa.
	    * 							 INPUT_OUTPUT_ERROR, si ha ocurrido un error en la salida.
	    * 							 SAVE_ERROR, si hubo un error al guardar la red AManufacturing.
	    */
	   public void guarde(File file) throws DAPOOSException {
			try {
				FileOutputStream fileOutput = new FileOutputStream(file);
				ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
				objectOutput.writeObject(this);
				objectOutput.close();
	        } catch (FileNotFoundException e) {
	            throw new DAPOOSException(DAPOOSException.FILE_NOT_FOUND);
	        } catch (InvalidClassException e) {
	            throw new DAPOOSException(DAPOOSException.INVALID_CLASS);
	        } catch (NotSerializableException e) {
	            throw new DAPOOSException(DAPOOSException.NOT_SERIALIZABLE);
	        } catch (NotActiveException e) {
	            throw new DAPOOSException(DAPOOSException.NOT_ACTIVE);
	        } catch (IOException e) {        	
	            throw new DAPOOSException(DAPOOSException.INPUT_OUTPUT_ERROR);
			} catch (Exception e) {
				throw new DAPOOSException(DAPOOSException.SAVE_ERROR);
			}
	   }
}
