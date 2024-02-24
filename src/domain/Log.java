package domain;  

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * Clase que se encarga de registrar.
 */
public class Log {
    public static String nombre="DAPOOS";
    
    /**
     * Toma las excepciones obtenidas y las registra en un archivo llamado DAPOOS.log
     * @param e Excepción que va ser reguitrada-.
     */
    public static void record(Exception e) {
        try {
            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler(nombre+".log", true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE, e.toString(), e);
            file.close();
        }catch (Exception oe) {
            oe.printStackTrace();
            System.exit(0);
        }
    }
}