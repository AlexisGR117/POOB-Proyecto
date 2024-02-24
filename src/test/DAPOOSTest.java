package test;
import domain.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Clase test de DAPOOS.
 * @author Angel Cuervo y Jefer Gonzalez
 * @version 1.0 (19/11/2022)
 */
public class DAPOOSTest {
	
    @Test
    public void deberiaPoderCrearUnNuevoJuegoDeDamas() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals("Nombre Norte", dapoos.jugadorNorte().nombre());
        	assertEquals("Negro", dapoos.jugadorNorte().color());
        	assertEquals(0, dapoos.jugadorNorte().movimientos());
        	assertEquals('n', dapoos.jugadorNorte().lado());
        	assertEquals(20, dapoos.jugadorNorte().numeroFichas());
        	assertEquals("Nombre Sur", dapoos.jugadorSur().nombre());
        	assertEquals("Blanco", dapoos.jugadorSur().color());
        	assertEquals(0, dapoos.jugadorSur().movimientos());
        	assertEquals('s', dapoos.jugadorSur().lado());
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());    
        	int tamano = Tablero.TAMANOLADO;
        	assertEquals(10, tamano);    
        	Tablero tablero = dapoos.tablero();
    		for (int i = 0; i < tamano; i++) for (int j = 0; j < tamano; j++) {
    			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
    				if (i < tamano/2 - 1 || i > tamano/2) {
	 					assertEquals(i, tablero.casilla(i, j).fila());
						assertEquals(j, tablero.casilla(i, j).columna());
						assertEquals(tablero, tablero.casilla(i, j).tablero());
						assertTrue(tablero.ficha(i, j) instanceof Peon);
	    				if (i < tamano/2 - 1) assertEquals(dapoos.jugadorNorte(), tablero.ficha(i, j).jugador());
	    				if (i > tamano/2) assertEquals(dapoos.jugadorSur(), tablero.ficha(i, j).jugador());
    				}
    			}
    		}
    		assertEquals(dapoos.jugadorSur(), dapoos.turno()); 
    		assertNull(dapoos.ganador());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElPrimerNombreTieneLongitudMayorA14() {
        try { 
        	new DAPOOS("Nombre Largo Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.LONG_NAME, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElSegundoNombreTieneLongitudMayorA14() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Largo Sur", "Negro", "Blanco", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.LONG_NAME, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElPrimerNombreTieneLongitudMenorA3() {
        try { 
        	new DAPOOS("No", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SHORT_NAME, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElSegundoNombreTieneLongitudMenorA3() {
        try { 
        	new DAPOOS("Nombre Norte", "Su", "Negro", "Blanco", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SHORT_NAME, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiLosDosJugadoresTienenElMismoNombre() {
        try { 
        	new DAPOOS("Nombre", "Nombre", "Negro", "Blanco", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SAME_NAMES, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiLosDosJugadoresTienenElMismoColor() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Negro", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SAME_COLORS, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElPorcentajeEsMenorA0() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, -1, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_PERCENTAGE, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElPorcentajeEsMayorA100() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 101, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_PERCENTAGE, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiLaDificultadDadaNoEstaEntreLasEstablecidad() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente", "Rapido", 10);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_DIFFICULTY, e.getMessage());
        }  
    }  
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElTiempoDeTurnoEsMenorA0() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente", "QuickTime", -1);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_TIME_TURN, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElTiempoDeTurnoEsMayorA100() {
        try { 
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente", "QuickTime", 101);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_TIME_TURN, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaPoderMoverFichas() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Ficha ficha = tablero.ficha(6, 1);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 0);
        	assertNull(tablero.ficha(6, 1));
            assertEquals(ficha, tablero.ficha(5, 0));
            assertEquals(1, dapoos.jugadorSur().movimientos());
        	Ficha ficha2 = tablero.ficha(3, 4);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 5);
        	assertNull(tablero.ficha(3, 4));
            assertEquals(ficha2, tablero.ficha(4, 5));
            assertEquals(1, dapoos.jugadorNorte().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaCambiarDeTurnoAlMover() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 5);
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNombreDelugadorQueTieneElTurno() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	assertEquals("Nombre Sur", dapoos.turno().nombre());
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	assertEquals("Nombre Norte", dapoos.turno().nombre());
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 5);
        	assertEquals("Nombre Sur", dapoos.turno().nombre());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNombreDelJugadorSur() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals("Nombre Sur", dapoos.jugadorSur().nombre());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNombreDelJugadorNorte() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals("Nombre Norte", dapoos.jugadorNorte().nombre());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElColorDelJugadorSur() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals("Blanco", dapoos.jugadorSur().color());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElColorDelJugadorNorte() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals("Negro", dapoos.jugadorNorte().color());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNumeroDeFichasDelJugadorSur() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNumeroDeFichasDelJugadorNorte() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	assertEquals(20, dapoos.jugadorNorte().numeroFichas());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNumeroDeMovimientosDelJugadorSur() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	assertEquals(0, dapoos.jugadorSur().movimientos());
          	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	assertEquals(1, dapoos.jugadorSur().movimientos());
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	assertEquals(2, dapoos.jugadorSur().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderConsultarElNumeroDeMovimientosDelJugadorNorte() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
           	Tablero tablero = dapoos.tablero();
           	assertEquals(0, dapoos.jugadorNorte().movimientos());
          	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	assertEquals(1, dapoos.jugadorNorte().movimientos());
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	assertEquals(2, dapoos.jugadorNorte().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaEliminarseLaFichaDelJugadorSurSiEsCapturada() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
           	assertEquals(0, dapoos.jugadorNorte().movimientos());
           	Ficha ficha = tablero.ficha(6, 9);
          	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	assertFalse(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaEliminarseLaFichaDelJugadorNorteSiEsCapturada() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
           	assertEquals(0, dapoos.jugadorNorte().movimientos());
           	Ficha ficha = tablero.ficha(3, 0);
          	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 2);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(5, 2));
        	tablero.moverSeleccion(3, 0);
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception" + e.getMessage());
        }
    }
    
    @Test
    public void deberiaDismisnuirElNumeroDeFichasDelJugadorSurSiLeCapturanAlguna() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
           	assertEquals(0, dapoos.jugadorNorte().movimientos());
           	Ficha ficha = tablero.ficha(6, 9);
          	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	assertEquals(19, dapoos.jugadorSur().numeroFichas());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaDismisnuirElNumeroDeFichasDelJugadorNorteSiLeCapturanAlguna() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
           	assertEquals(0, dapoos.jugadorNorte().movimientos());
           	Ficha ficha = tablero.ficha(3, 0);
          	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 2);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(5, 2));
        	tablero.moverSeleccion(3, 0);
        	assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElJugadorQueNoTieneElTurnoIntentaJugar() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_PLAYER, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaPoderCapturarFichasConPeones() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Ficha ficha = tablero.ficha(6, 3);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 4);       	
        	Ficha ficha2 = tablero.ficha(3, 2);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(3, 2);
        	assertNull(tablero.ficha(4, 3));
        	assertEquals(ficha, tablero.ficha(3, 2));
        	assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha2));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    @Test
    public void deberiaEliminarseUnaFichaSiElJugadorNoCapturaTeniendoLaPosibilidad() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Ficha ficha = tablero.ficha(6, 3);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 4);
        	tablero.seleccionar(tablero.ficha(3, 2));
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	assertNull(tablero.ficha(4, 5));
        	assertNull(tablero.ficha(5, 4));
        	assertEquals(19, dapoos.jugadorSur().numeroFichas());
        	assertFalse(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiEnLaPosicionALaQueSeQuiereMoverHayOtraFicha() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(7, 0));
        	tablero.moverSeleccion(6, 1);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.NOT_EMPTY, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiALaColumnaALaQueSeQuiereMoverEstaFueraDelTablero() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 10);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.OFF_THE_BOARD, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiALaFilaALaQueSeQuiereMoverEstaFueraDelTablero() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(10, 9);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.OFF_THE_BOARD, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiElJugadorNorteIntentaMoverUnPeonHaciaElNorte() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(4, 3));
        	tablero.moverSeleccion(3, 4);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
 
    @Test
    public void deberiaLanzarUnaExcepcionSiElJugadorSurIntentaMoverUnPeonHaciaElSur() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(tablero.ficha(5, 6));
        	tablero.moverSeleccion(6, 5);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiSeIntentaMoverUnaFichaAUnaCasillaBlanca() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(5, 5);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.WHITE_SQUARE, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiSeIntentaMoverUnPeonMasDeUnaCasillaSinCapturar() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(4, 7);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaEliminarseLaFichaSiElJugadorNoCapturaTeniendoLaPosibilidadDeVolverACapturarConLaMismaFicha() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	Ficha ficha = tablero.ficha(3, 8);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(5, 4);        	
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);        	
        	tablero.seleccionar(tablero.ficha(7, 6));
        	tablero.moverSeleccion(6, 5);        	
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);        	
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);        	
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(6, 7);        	
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(7, 6);        	
        	assertNull(tablero.ficha(4, 9));
        	assertNull(tablero.ficha(5, 8));
        	assertNull(tablero.ficha(6, 7));
        	assertNull(tablero.ficha(7, 6));
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha));
        	assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        } catch (DAPOOSException e){
        	e.printStackTrace();
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderHacerMasDeUnMovimientoSiPuedoVolverACapturar() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	Ficha ficha = tablero.ficha(3, 8);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(6, 7);
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(8, 9);
        	assertNull(tablero.ficha(5, 8));
        	assertNull(tablero.ficha(7, 8));
        	assertEquals(ficha, tablero.ficha(8, 9));
        	assertEquals(18, dapoos.jugadorSur().numeroFichas());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiEnUnMismoTurnoIntentaMoverMasDeUnaFicha() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.ONE_TOKEN_PER_TURN, e.getMessage());
        } 
    }
    
    @Test
    public void deberiaPoderSeleccionarUnaFichaDamaAlLlegarAlOtroLado() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, true, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8, "Dama");
        	assertTrue(tablero.ficha(9, 8) instanceof Dama);
        	assertEquals(1, dapoos.jugadorNorte().numeroFichasEspeciales());
        } catch (DAPOOSException e){
        	e.printStackTrace();
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderMoverseMasDeUnaCasillasUnaDama() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Dama(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(4, 7);
        	assertNull(tablero.ficha(6, 9));
        	assertEquals(ficha, tablero.ficha(4, 7));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderDevolvermeConUnaDama() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Dama(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(6, 9);
        	assertNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(6, 9));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderCapturarAMasDe2CasillasConUnaDama() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Dama(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	Ficha ficha2 = tablero.ficha(3, 6);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(3, 6);
        	assertNull(tablero.ficha(6, 9));
        	assertNull(tablero.ficha(4, 7));
        	assertEquals(ficha, tablero.ficha(3, 6));
        	assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha2));
        } catch (DAPOOSException e){
            fail("Threw a exception"+e.getMessage());
        }
    } 
    
    @Test
    public void deberiaPoderSeleccionarUnaFichaNinjaAlLlegarAlOtroLado() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, true, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8, "Ninja");
        	assertTrue(tablero.ficha(9, 8) instanceof Ninja);
        	assertEquals(1, dapoos.jugadorNorte().numeroFichasEspeciales());
        } catch (DAPOOSException e){
        	e.printStackTrace();
            fail("Threw a exception");
        }
    }
   
    @Test
    public void deberianPoderMoverUnaFichaNinja() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Ninja(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	assertNull(tablero.ficha(6, 9));
            assertEquals(ficha, tablero.ficha(5, 8));
            assertEquals(1, dapoos.jugadorSur().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderDevolvermeConUnaFichaNinja() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Ninja(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(6, 9);
        	assertNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(6, 9));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderCapturarFichasConFichasNinja() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 3);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Ninja(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 4);       	
        	Ficha ficha2 = tablero.ficha(3, 2);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(3, 2);
        	assertNull(tablero.ficha(4, 3));
        	assertEquals(ficha, tablero.ficha(3, 2));
        	assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha2));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    
    @Test
    public void deberiaLanzarUnaExcepcionSiSeIntentaMoverUnaFichaNinjaMasDeUnaCasillaSinCapturar() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 5);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Ninja(dapoos.jugadorSur()));
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(4, 7);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
    
    @Test
    public void noDeberiaPoderCapturarUnaFichaNinjaLaPrimeraVezQueSeSaltaSobreElla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Ninja(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	Ficha ficha2 = tablero.ficha(3, 8);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(6, 9);
        	assertNotNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(5, 8));
        	assertEquals(ficha2, tablero.ficha(6, 9));
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());
        	assertTrue(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    @Test
    public void deberiaPoderCapturarUnaFichaNinjaLaSegundaVezQueSeSaltaSobreElla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Ninja(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(4, 7));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(4, 7);
        	Ficha ficha2 = tablero.ficha(3, 6);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(5, 8);
        	assertNull(tablero.ficha(4, 7));
        	assertEquals(ficha2, tablero.ficha(5, 8));
        	assertEquals(19, dapoos.jugadorSur().numeroFichas());
        	assertFalse(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    } 
    
    @Test
    public void deberiaPoderSeleccionarUnaFichaZombieAlLlegarAlOtroLado() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, true, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8, "Zombie");
        	assertTrue(tablero.ficha(9, 8) instanceof Zombie);
        	assertEquals(1, dapoos.jugadorNorte().numeroFichasEspeciales());
        } catch (DAPOOSException e){
        	e.printStackTrace();
            fail("Threw a exception");
        }
    }
   
    @Test
    public void deberianPoderMoverUnaFichaZombie() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	assertNull(tablero.ficha(6, 9));
            assertEquals(ficha, tablero.ficha(5, 8));
            assertEquals(1, dapoos.jugadorSur().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderDevolvermeConUnaFichaZombie() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(6, 9);
        	assertNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(6, 9));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderCapturarFichasConFichasZombie() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 3);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 4);       	
        	Ficha ficha2 = tablero.ficha(3, 2);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(3, 2);
        	assertNull(tablero.ficha(4, 3));
        	assertEquals(ficha, tablero.ficha(3, 2));
        	assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha2));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    
    @Test
    public void deberiaLanzarUnaExcepcionSiSeIntentaMoverUnaFichaZombieMasDeUnaCasillaSinCapturar() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 5);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	tablero.seleccionar(tablero.ficha(6, 5));
        	tablero.moverSeleccion(4, 7);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
    
    @Test
    public void noDeberiaCapturarseUnaFichaZombieLaPrimeraVezQueSeSaltaSobreElla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	Ficha ficha2 = tablero.ficha(3, 8);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(6, 9);
        	assertEquals(ficha2, tablero.ficha(6, 9));
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());
        	assertTrue(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    @Test
    public void deberiaQuitarseDelTableroUnaFichaZombieLaPrimeraVezQueSeSaltaSobreElla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	Ficha ficha2 = tablero.ficha(3, 8);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(6, 9);
        	assertNull(tablero.ficha(5, 8));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }  
    
    @Test
    public void noDeberiaVolverAlTableroUnaFichaZombieAntesDeTresTurnosDeJuego() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(4, 7));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(7, 2));
        	tablero.moverSeleccion(6, 1);
        	assertNull(tablero.ficha(5, 8));
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	assertNull(tablero.ficha(5, 8));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }   
    
    @Test
    public void deberiaVolverAlTableroUnaFichaZombieDespuesDeTresTurnosDeJuego() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(4, 7));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(7, 2));
        	tablero.moverSeleccion(6, 1);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(8, 3));
        	tablero.moverSeleccion(7, 2);
        	assertNotNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(5, 8));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void noDeberiaVolverAlTableroUnaFichaZombieSiHayOtraFichaEnLaCasilla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(4, 7));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(7, 2));
        	tablero.moverSeleccion(6, 1);
        	assertNotEquals(ficha, tablero.ficha(5, 8));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaVolverAlTableroUnaFichaZombieCuandoSeDesocupeLaCasilla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(4, 7));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(7, 2));
        	tablero.moverSeleccion(6, 1);
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(4, 9);
        	assertNotNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(5, 8));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderCapturarUnaFichaZombieLaSegundaVezQueSeSaltaSobreElla() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(6, 9);
        	casilla.ficha().capturar();
        	casilla.colocarFicha(new Zombie(dapoos.jugadorSur()));
        	Ficha ficha = casilla.ficha();
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);       
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(4, 7));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(7, 2));
        	tablero.moverSeleccion(6, 1);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(8, 3));
        	tablero.moverSeleccion(7, 2);
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(4, 7);
        	Ficha ficha2 = tablero.ficha(3, 6);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(5, 8);
        	assertNull(tablero.ficha(4, 7));
        	assertEquals(ficha2, tablero.ficha(5, 8));
        	assertEquals(19, dapoos.jugadorSur().numeroFichas());
        	assertFalse(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    } 
    
    @Test
    public void deberiaLanzarUnaExcepcionSiLasFichasEspecialesEstanActivasYNoSeSeleccionaElTipoAlLlegarAlOtroLAdo() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, true, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SELECT_TOKEN_TYPE, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaLanzarUnaExcepcionSiLasFichasEspecialesEstánDesactivadasYSeSeleccionaElTipoAlLlegarAlotroLado() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8, "Zombie");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SELECT_TOKEN_TYPE_DISABLE, e.getMessage());
        }  
    }
    
    @Test
    public void deberiaPoderCrearUnNuevoJuegoDeDapoosConCasillasEspeciales() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 100, "Permanente");
        	int tamano = Tablero.TAMANOLADO;
    		for (int i = tamano/2 - 1; i <= tamano/2; i++) for (int j = 0; j < tamano; j++) {
    			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
    				Casilla casilla = dapoos.tablero().casilla(i, j);
    				assertTrue(casilla instanceof Mine || casilla instanceof Jail || casilla instanceof Teleport);
    			}
    		}
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianCrearseLAsCasillasEspecialesDeAcuerdoAlPorcentajeDado() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 60, "Permanente");
        	int tamano = Tablero.TAMANOLADO;
        	int c = 0;
    		for (int i = tamano/2 - 1; i <= tamano/2; i++) for (int j = 0; j < tamano; j++) {
    			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
    				Casilla casilla = dapoos.tablero().casilla(i, j);
    				if (casilla instanceof Mine || casilla instanceof Jail || casilla instanceof Teleport) c ++;
    			}
    		}
    		assertEquals((int)((double)((double)60/(double)100)*(double)tamano), c);
        	DAPOOS damas2 = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 94, "Permanente");
        	c = 0;
    		for (int i = tamano/2 - 1; i <= tamano/2; i++) for (int j = 0; j < tamano; j++) {
    			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
    				Casilla casilla = damas2.tablero().casilla(i, j);
    				if (casilla instanceof Mine || casilla instanceof Jail || casilla instanceof Teleport) c ++;
    			}
    		}
    		assertEquals((int)((double)((double)94/(double)100)*(double)tamano), c);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderTenerCasillasJail() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Jail(tablero, 5, 8);
        	assertTrue(tablero.casilla(5, 8) instanceof Jail);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianLanzarUnaExcepcionSiSeIntentaMoverUnaFichaQueEstaEnUnaCasillaJailDespuesDeUnTurno() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Jail(tablero, 5, 8);
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(4, 7);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
    
    @Test
    public void deberianLanzarUnaExcepcionSiSeIntentaMoverUnaFichaQueEstaEnUnaCasillaJailDespuesDeDosTurnos() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Jail(tablero, 5, 8);
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(4, 7);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.INVALID_MOVEMENT, e.getMessage());
        } 
    }
    
    @Test
    public void deberianPoderMoverUnaFichaQueEstaEnUnaCasilaJailDespuesDeDosTurnos() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Jail(tablero, 5, 8);
        	Ficha ficha = tablero.ficha(6, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	tablero.seleccionar(tablero.ficha(3, 0));
        	tablero.moverSeleccion(4, 1);
        	tablero.seleccionar(tablero.ficha(6, 3));
        	tablero.moverSeleccion(5, 4);
        	tablero.seleccionar(tablero.ficha(3, 4));
        	tablero.moverSeleccion(4, 5);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(4, 7);
        	assertNull(tablero.ficha(5, 8));
        	assertEquals(ficha, tablero.ficha(4, 7));
        } catch (DAPOOSException e) {
        	 fail("Threw a exception");
        } 
    }
    
    @Test
    public void noDeberiaEliminarseLaFichaEncarceladaSiElJugadorTienePosibilidadDeCapturarConEstaYNoCaptura() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][4] = new Jail(tablero, 5, 4);
        	Ficha ficha = tablero.ficha(6, 3);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 4);
        	tablero.seleccionar(tablero.ficha(3, 2));
        	tablero.moverSeleccion(4, 3);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	assertEquals(ficha, tablero.ficha(5, 4));
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());
        	assertTrue(dapoos.jugadorSur().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderTenerCasillasTeleport() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Teleport(tablero, 5, 8);
        	assertTrue(tablero.casilla(5, 8) instanceof Teleport);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianTeletransportarseAUnLugarAleatorioUnaFichaQuePisaUnaCasillaTeleport() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Teleport(tablero, 5, 8);
        	Ficha ficha = tablero.ficha(6, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	boolean enCasilla = false;
        	for (Casilla[] f: tablero.casillas()) for (Casilla c:f) {
        		if (c != null && c.ficha() == ficha) enCasilla = true;
        	}
        	assertTrue(enCasilla);
        	assertFalse(tablero.ficha(5, 8) == ficha);
        } catch (DAPOOSException e) {
        	 fail("Threw a exception");
        } 
    }
    
    @Test
    public void deberianPoderTenerCasillasMine() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Mine(tablero, 5, 8);
        	assertTrue(tablero.casilla(5, 8) instanceof Mine);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianEliminarseTodasLasFichasQueEstenEnElEspacio3x3DeLaBombaAlSerPisadaPorUnaFicha() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.casillas()[5][8] = new Mine(tablero, 5, 8);
        	tablero.seleccionar(tablero.ficha(6, 1));
        	tablero.moverSeleccion(5, 0);
        	Ficha ficha1 = tablero.ficha(3, 8);
        	tablero.seleccionar(ficha1);
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(6, 3));
        	tablero.moverSeleccion(5, 2);
        	Ficha ficha2 = tablero.ficha(3, 6);
        	tablero.seleccionar(ficha2);
        	tablero.moverSeleccion(4, 7);
        	Ficha ficha3 = tablero.ficha(6, 9);
        	tablero.seleccionar(ficha3);
        	tablero.moverSeleccion(5, 8);
        	Ficha ficha4 = tablero.ficha(6, 7);
        	assertNull(tablero.ficha(4, 7));
        	assertNull(tablero.ficha(4, 9));
        	assertNull(tablero.ficha(5, 8));
        	assertNull(tablero.ficha(6, 7));
        	assertNull(tablero.ficha(6, 9));
        	assertEquals(18, dapoos.jugadorNorte().numeroFichas());
        	assertEquals(18, dapoos.jugadorSur().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha1));
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha2));
        	assertFalse(dapoos.jugadorSur().fichas().contains(ficha3));
        	assertFalse(dapoos.jugadorSur().fichas().contains(ficha4));
        } catch (DAPOOSException e) {
        	 fail("Threw a exception");
        } 
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiNoSeHaSeleccionadoUnaFichaYSeIntentaMover() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.moverSeleccion(5, 6);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.NOT_SELECTED, e.getMessage());
        } 
    }
    
    @Test
    public void deberianAparecerComodinesEnCasillasAleatoriasCada5Turnos() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", true, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	int c = 0;
        	ArrayList<Casilla> vacias = tablero.casillasVacias();
    		for (Casilla v:vacias) if (v.comodin() != null) c ++;
    		assertEquals(1, c);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderTenerComodinesOneMoreTime() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new OneMoreTime(casilla));
        	assertTrue(tablero.casilla(5, 8).comodin() instanceof OneMoreTime);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaTomarElTurnoElJugadorQueTomaUnComodinOneMoreTime() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new OneMoreTime(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderVolverAMoverElJugadorQueTomaUnComodinOneMoreTime() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new OneMoreTime(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	Ficha ficha = tablero.ficha(6, 7);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 6);
        	assertNull(tablero.ficha(6, 7));
            assertEquals(ficha, tablero.ficha(5, 6));
            assertEquals(2, dapoos.jugadorSur().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaCambiarseDeTurnoDespuesDeVolverAMoverConElComodinOneMoreTime() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new OneMoreTime(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderTenerComodinesGun() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Gun(casilla));
        	assertTrue(tablero.casilla(5, 8).comodin() instanceof Gun);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaTomarElTurnoElJugadorQueTomaUnComodinGun() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Gun(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderSeleccionarUnaFichaDelOpononenteYEliminarlarConUnComodinGun() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Gun(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	Ficha ficha = tablero.ficha(1, 8);
        	tablero.seleccionar(ficha);
        	assertNull(tablero.ficha(1, 8));
            assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiSeIntentaEliminarUnaFichaPropiaConElComodinGun() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Gun(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(6, 7));
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.NOT_OPONNENT_TOKEN, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaCambiarseDeTurnoDespuesDeUsarElComodinGun() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Gun(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberianPoderTenerComodinesStomp() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Stomp(casilla));
        	assertTrue(tablero.casilla(5, 8).comodin() instanceof Stomp);
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaTomarElTurnoElJugadorQueTomaUnComodinStomp() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Stomp(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderMoverUnaFichaAUnaCasillaConFichaDelOponenteConUnComodinStomp() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Stomp(casilla));
        	Ficha ficha = tablero.ficha(6, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(ficha);
        	Ficha ficha2 = tablero.ficha(1, 8);
        	tablero.moverSeleccion(1, 8);
        	assertEquals(ficha, tablero.ficha(1, 8));
            assertEquals(19, dapoos.jugadorNorte().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha2));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderMoverUnaFichaAlFinalYElegirUnaFichaEspecialConUnComodinStomp() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, true, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Stomp(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(0, 9, "Ninja");
        	assertTrue(tablero.ficha(0, 9) instanceof Ninja);
        	assertEquals(1, dapoos.jugadorSur().numeroFichasEspeciales());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiSeIntentaEliminarUnaFichaPropiaConElComodinStomp() {
        try { 
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Gun(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(6, 7);
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.NOT_OPONNENT_TOKEN, e.getMessage());
        }  
    }   
    
    @Test
    public void deberiaCambiarseDeTurnoDespuesDeUsarElComodinStomp() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Casilla casilla = tablero.casilla(5, 8);
        	casilla.colocarComodin(new Stomp(casilla));
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(5, 8));
        	tablero.moverSeleccion(1, 0);
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderCrearUnNuevoJuegoDeDamasContraUnaMaquinaAprendiz() {
        try {
        	DAPOOS dapoos = new DAPOOS("Máquina", "Nombre Sur", "Negro", "Blanco", "Aprendiz", false, false, 0, "Permanente");
        	assertEquals("Máquina", dapoos.jugadorNorte().nombre());
           	assertTrue(dapoos.jugadorNorte() instanceof Aprendiz);
        	assertEquals("Negro", dapoos.jugadorNorte().color());
        	assertEquals(0, dapoos.jugadorNorte().movimientos());
        	assertEquals('n', dapoos.jugadorNorte().lado());
        	assertEquals(20, dapoos.jugadorNorte().numeroFichas());
        	assertEquals("Nombre Sur", dapoos.jugadorSur().nombre());
        	assertEquals("Blanco", dapoos.jugadorSur().color());
        	assertEquals(0, dapoos.jugadorSur().movimientos());
        	assertEquals('s', dapoos.jugadorSur().lado());
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());    
        	int tamano = Tablero.TAMANOLADO;
        	assertEquals(10, tamano);    
        	Tablero tablero = dapoos.tablero();
    		for (int i = 0; i < tamano; i++) for (int j = 0; j < tamano; j++) {
    			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
    				if (i < tamano/2 - 1 || i > tamano/2) {
	 					assertEquals(i, tablero.casilla(i, j).fila());
						assertEquals(j, tablero.casilla(i, j).columna());
						assertEquals(tablero, tablero.casilla(i, j).tablero());
						assertTrue(tablero.ficha(i, j) instanceof Peon);
	    				if (i < tamano/2 - 1) assertEquals(dapoos.jugadorNorte(), tablero.ficha(i, j).jugador());
	    				if (i > tamano/2) assertEquals(dapoos.jugadorSur(), tablero.ficha(i, j).jugador());
    				}
    			}
    		}
    		assertEquals(dapoos.jugadorSur(), dapoos.turno()); 
    		assertNull(dapoos.ganador());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaMoverseUnaMaquinaAprendizAutaticamenteAlTenerElTurno() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Máquina", "Negro", "Blanco", "Aprendiz", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	assertEquals(1, dapoos.jugadorSur().movimientos());
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	assertEquals(2, dapoos.jugadorSur().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaPoderCrearUnNuevoJuegoDeDamasContraUnaMaquinaPrincipiante() {
        try {
        	DAPOOS dapoos = new DAPOOS("Máquina", "Nombre Sur", "Negro", "Blanco", "Principiante", false, false, 0, "Permanente");
        	assertEquals("Máquina", dapoos.jugadorNorte().nombre());
           	assertTrue(dapoos.jugadorNorte() instanceof Principiante);
        	assertEquals("Negro", dapoos.jugadorNorte().color());
        	assertEquals(0, dapoos.jugadorNorte().movimientos());
        	assertEquals('n', dapoos.jugadorNorte().lado());
        	assertEquals(20, dapoos.jugadorNorte().numeroFichas());
        	assertEquals("Nombre Sur", dapoos.jugadorSur().nombre());
        	assertEquals("Blanco", dapoos.jugadorSur().color());
        	assertEquals(0, dapoos.jugadorSur().movimientos());
        	assertEquals('s', dapoos.jugadorSur().lado());
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());    
        	int tamano = Tablero.TAMANOLADO;
        	assertEquals(10, tamano);    
        	Tablero tablero = dapoos.tablero();
    		for (int i = 0; i < tamano; i++) for (int j = 0; j < tamano; j++) {
    			if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)) {
    				if (i < tamano/2 - 1 || i > tamano/2) {
	 					assertEquals(i, tablero.casilla(i, j).fila());
						assertEquals(j, tablero.casilla(i, j).columna());
						assertEquals(tablero, tablero.casilla(i, j).tablero());
						assertTrue(tablero.ficha(i, j) instanceof Peon);
	    				if (i < tamano/2 - 1) assertEquals(dapoos.jugadorNorte(), tablero.ficha(i, j).jugador());
	    				if (i > tamano/2) assertEquals(dapoos.jugadorSur(), tablero.ficha(i, j).jugador());
    				}
    			}
    		}
    		assertEquals(dapoos.jugadorSur(), dapoos.turno()); 
    		assertNull(dapoos.ganador());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaMoverseUnaMaquinaPrincipianteAutaticamenteAlTenerElTurno() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Máquina", "Negro", "Blanco", "Principiante", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	assertEquals(1, dapoos.jugadorSur().movimientos());
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 7);
        	assertEquals(2, dapoos.jugadorSur().movimientos());
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaCapturarUnaMaquinaPrincipianteSiTieneLaOportunidad() {
        try {
        	DAPOOS dapoos = new DAPOOS("Máquina", "Nombre Sur", "Negro", "Blanco", "Principiante", false, false, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	Ficha ficha2 = new Peon(dapoos.jugadorNorte());
        	tablero.casilla(4, 7).colocarFicha(ficha2);
        	Ficha ficha = tablero.ficha(6, 9);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	assertEquals(1, dapoos.jugadorSur().movimientos());
        	assertNull(tablero.ficha(5, 8));
        	assertEquals(ficha2, tablero.ficha(6, 9));
        	assertEquals(19, dapoos.jugadorSur().numeroFichas());
        	assertFalse(dapoos.jugadorNorte().fichas().contains(ficha));
        } catch (DAPOOSException e){
            fail("Threw a exception");
        }
    }
    
    @Test
    public void deberiaLanzarUnaExcepcionSiNoSeEspecificaElJugadorQueVaSerUnaMaquina() {
        try {
        	new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", "Principiante", false, false, 0, "Permanente");
            fail("Did not throw exception");
        } catch (DAPOOSException e) {
            assertEquals(DAPOOSException.SELECT_MACHINE_PLAYER, e.getMessage());
        }  
    } 
    
    @Test
    public void deberiaPoderAbrirUnJuegoDeDAPOOS() {
        try {
        	File juego = new File("GanarMovimientosSur.dat");
        	DAPOOS dapoos = DAPOOS.abra(juego);
        	assertEquals("Norte", dapoos.jugadorNorte().nombre());
        	assertEquals("Blanco", dapoos.jugadorNorte().color());
        	assertEquals(19, dapoos.jugadorNorte().movimientos());
        	assertEquals('n', dapoos.jugadorNorte().lado());
        	assertEquals(2, dapoos.jugadorNorte().numeroFichas());
        	assertEquals(0, dapoos.jugadorNorte().numeroFichasEspeciales());
        	assertEquals("Sur", dapoos.jugadorSur().nombre());
        	assertEquals("Rojo", dapoos.jugadorSur().color());
        	assertEquals(20, dapoos.jugadorSur().movimientos());
        	assertEquals('s', dapoos.jugadorSur().lado());
        	assertEquals(20, dapoos.jugadorSur().numeroFichas());    
        	assertEquals(1, dapoos.jugadorSur().numeroFichasEspeciales());    
    		assertEquals(dapoos.jugadorSur(), dapoos.turno()); 
        	assertNull(dapoos.ganador());
        } catch (DAPOOSException e) {
        	fail("Threw a exception");
        }  
    } 
    
    @Test
    public void deberiaPoderGuardarUnJuegoDeDAPOOS() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Rojo", "Negro", false, true, 0, "Permanente");
        	Tablero tablero = dapoos.tablero();
        	tablero.seleccionar(tablero.ficha(6, 9));
        	tablero.moverSeleccion(5, 8);
        	tablero.seleccionar(tablero.ficha(3, 8));
        	tablero.moverSeleccion(4, 9);
        	tablero.seleccionar(tablero.ficha(7, 8));
        	tablero.moverSeleccion(6, 9);
        	tablero.seleccionar(tablero.ficha(2, 7));
        	tablero.moverSeleccion(3, 8);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(1, 8));
        	tablero.moverSeleccion(2, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(5, 6);
        	tablero.seleccionar(tablero.ficha(4, 9));
        	tablero.moverSeleccion(6, 7);
        	tablero.seleccionar(tablero.ficha(6, 7));
        	tablero.moverSeleccion(8, 9);
        	tablero.seleccionar(tablero.ficha(8, 7));
        	tablero.moverSeleccion(7, 8);
        	tablero.seleccionar(tablero.ficha(0, 9));
        	tablero.moverSeleccion(1, 8);
        	tablero.seleccionar(tablero.ficha(9, 8));
        	tablero.moverSeleccion(8, 7);
        	tablero.seleccionar(tablero.ficha(8, 9));
        	tablero.moverSeleccion(9, 8, "Dama");
        	assertTrue(tablero.ficha(9, 8) instanceof Dama);
        	dapoos.guarde(new File("PruebaGuardar.dat"));
        	File juego = new File("PruebaGuardar.dat");
        	dapoos = DAPOOS.abra(juego);
        	assertEquals("Nombre Norte", dapoos.jugadorNorte().nombre());
        	assertEquals("Rojo", dapoos.jugadorNorte().color());
        	assertEquals(7, dapoos.jugadorNorte().movimientos());
        	assertEquals('n', dapoos.jugadorNorte().lado());
        	assertEquals(20, dapoos.jugadorNorte().numeroFichas());
        	assertEquals(1, dapoos.jugadorNorte().numeroFichasEspeciales());
        	assertEquals("Nombre Sur", dapoos.jugadorSur().nombre());
        	assertEquals("Negro", dapoos.jugadorSur().color());
        	assertEquals(6, dapoos.jugadorSur().movimientos());
        	assertEquals('s', dapoos.jugadorSur().lado());
        	assertEquals(18, dapoos.jugadorSur().numeroFichas());    
        	assertEquals(0, dapoos.jugadorSur().numeroFichasEspeciales());    
    		assertEquals(dapoos.jugadorSur(), dapoos.turno()); 
        	assertNull(dapoos.ganador());
        } catch (DAPOOSException e) {
        	fail("Threw a exception");
        }  
    } 
    
    @Test
    public void deberiaPoderGanarCapturandoTodasLasFichasConElJugadorNorte() {
        try {
        	File juego = new File("GanarCapturasNorte.dat");
        	DAPOOS dapoos = DAPOOS.abra(juego);
        	Tablero tablero = dapoos.tablero();
        	Ficha ficha = tablero.ficha(3, 6);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(5, 8);
        	assertNull(tablero.ficha(4, 7));
        	assertEquals(ficha, tablero.ficha(5, 8));
        	assertEquals(19, dapoos.jugadorNorte().movimientos());
        	assertEquals(dapoos.jugadorNorte(), dapoos.ganador());
        	assertEquals(66, dapoos.puntaje());
        	assertEquals(0, dapoos.jugadorSur().numeroFichas());
        } catch (DAPOOSException e) {
        	fail("Threw a exception");
        }  
    }   
    
    @Test
    public void deberiaPoderGanarConElJugadorSurDejandoSinMovimientosAlJugadorNorte() {
        try {
        	File juego = new File("GanarMovimientosSur.dat");
        	DAPOOS dapoos = DAPOOS.abra(juego);
        	Tablero tablero = dapoos.tablero();
        	Ficha ficha = tablero.ficha(5, 6);
        	tablero.seleccionar(ficha);
        	tablero.moverSeleccion(2, 9);
        	assertNull(tablero.ficha(4, 7));
        	assertEquals(ficha, tablero.ficha(2, 9));
        	assertEquals(21, dapoos.jugadorSur().movimientos());
        	assertEquals(dapoos.jugadorSur(), dapoos.ganador());
        	assertEquals(64, dapoos.puntaje());
        	assertEquals(1, dapoos.jugadorNorte().numeroFichas());
        } catch (DAPOOSException e) {
        	fail("Threw a exception");
        }  
    }  
    
    /*
    @Test
    public void deberiaCambiarDeTurnoDespuesDe20SegundosEnQuickTime() {
        try {
        	DAPOOS dapoos = new DAPOOS("Nombre Norte", "Nombre Sur", "Negro", "Blanco", 0, "QuickTime", 1);
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        	Thread.sleep(4*1000);
        	assertEquals(dapoos.jugadorNorte(), dapoos.turno());
        	Thread.sleep(5*1000);
        	assertEquals(dapoos.jugadorSur(), dapoos.turno());
        } catch (Exception e){
            fail("Threw a exception");
        }
    }
    */
     
}
