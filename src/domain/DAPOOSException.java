package domain;

/**
 * Clase excepción de DPOOS.
 * @author Jefer Gonzalez
 * @version 3.0 (10/12/2022)
 */
public class DAPOOSException extends Exception {
	
	public static final String INVALID_MOVEMENT = "No se puede realizar el movimiento.";
    public static final String OFF_THE_BOARD = "La posición a la que se quiere mover la ficha no está dentro del tablero.";
    public static final String INVALID_PLAYER = "No es el turno de este jugador.";
    public static final String LONG_NAME = "El nombre de los jugadores debe tener menos de 14 caracteres.";
    public static final String SHORT_NAME = "El nombre de los jugadores debe tener al menos 3 caracteres.";
    public static final String SAME_NAMES = "Los nombres deben ser diferentes.";
    public static final String SAME_COLORS = "Los colores de los jugadores deben ser diferentes.";
    public static final String NOT_EMPTY = "En la posición a la cual se quiere mover la ficha no está vacía.";
    public static final String ONE_TOKEN_PER_TURN = "En un turno solo se puede mover una ficha.";
    public static final String WHITE_SQUARE = "Las fichas solo se pueden mover por las casillas negras.";
    public static final String NOT_SELECTED = "No se ha seleccionado una ficha en el tablero.";
    public static final String INVALID_PERCENTAGE = "El porcentaje debe ser un valor entre 0 y 100.";
    public static final String INVALID_DIFFICULTY = "Esta dificultad no está entre las establecidas que son: 'Normal' o QuickTime'.";
    public static final String INVALID_TIME_TURN = "El tiempo de cada turno debe ser un valor entre 0 y 100.";
    public static final String INVALID_TOKEN_TYPE = "No es un tipo de ficha valido, pueden ser: 'Dama', 'Ninja' o 'Zombie'.";
    public static final String SELECT_TOKEN_TYPE = "Se debe seleccionar el tipo de ficha.";
    public static final String SELECT_TOKEN_TYPE_DISABLE = "No se puede selecionar el tipo de la ficha en el modo sin fichas especiales.";
    public static final String INVALID_MACHINE_TYPE = "No es un tipo de maquina valido, puede se: 'Principiante' o 'Aprendiz'.";
    public static final String INVALID_SQUARE_TYPE = "No es un tipo de casilla valida, pueden ser: 'Jail', 'Mine' o 'Teleport'.";
    public static final String INVALID_VISUALIZATION = "No es un tipo de maquina valido puede ser: 'Permanente' o 'Relámpago'.";
    public static final String INVALID_JOKER = "No es un comodín valido puede ser: 'OneMoreTime', 'Gun' o 'Stomp'.";
    public static final String NOT_OPONNENT_TOKEN = "La ficha a eliminar debe ser del oponente.";
    public static final String SELECT_MACHINE_PLAYER = "Uno de los jugadores debe tener el nomnre 'Máquina'.";
    public static final String PAUSE_ONLY_IN_QUICKTIME = "Solo se puede pausar en el modo de juego Quicktime.";
    public static final String PAUSED_GAME = "No se pueden seleccionar o mover fichas mientras el juego está pausado.";
    
	public static final String FILE_NOT_FOUND = "No se ha encontrado el archivo.";
	public static final String INVALID_CLASS = "El tiempo de ejecución de serialización a detectado un problemas con una clase.";
	public static final String INVALID_OBJECT = "Uno o más objetos deserializados fallaron las pruebas de validación.";
	public static final String NOT_ACTIVE = "La serialización o deserialización no está activa.";
	public static final String STREAM_CORRUPTED = "El archivo está corrupto.";
	public static final String OPTIONAL_DATA = "Durante la lectura de un objeto hubo datos primitivos no leídos o al final de los datos pertenecientes a un objeto serializado en el flujo";
	public static final String INPUT_OUTPUT_ERROR = "Ha ocurrido un error en la entrada/salida.";
	public static final String NOT_SERIALIZABLE = "Una de las clases no es serializable.";
	public static final String EOF = "Se ha alcanzado inesperadamente un final de archivo o final de secuencia durante la entrada.";
	public static final String OPEN_ERROR = "Error al abrir el archivo.";
	public static final String SAVE_ERROR = "Error al guardar la partida.";

    /**
     * Constructor para objetos de clase DAPOOSException.
     * @param message Mensaje de la excepción.
     */
    public DAPOOSException(String message) {
        super(message);
    }
}
