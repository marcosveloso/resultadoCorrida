package com.cubo.corrida.test;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cubo.corrida.Piloto;

public class PilotoTest {
	
	private Piloto piloto1 = new Piloto("001", "piloto um");
	private Piloto piloto2 = new Piloto("002", "piloto um");
	private static final Long TRINTA_SEGUNDOS = 30000L;
	private static final Long VINTE_SEGUNDOS = 20000L;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCalculaTempoVoltas() {
		assertTrue(piloto1.getTempoTotal() == 0L);
		assertTrue(piloto2.getTempoTotal() == 0L);
		
		piloto1.adicionaVolta(1, TRINTA_SEGUNDOS, LocalTime.now() );
		assertFalse(piloto1.getTempoTotal() == 0L);
		assertTrue(TRINTA_SEGUNDOS.equals(piloto1.getTempoTotal()));
	}

	@Test
	public void testOrdenacao() {

		assertTrue(piloto1.compareTo(piloto2) == 0);
		
		piloto1.adicionaVolta(1, TRINTA_SEGUNDOS, LocalTime.now() );
		piloto1.adicionaVolta(2, TRINTA_SEGUNDOS, LocalTime.now() );
		piloto1.adicionaVolta(3, TRINTA_SEGUNDOS, LocalTime.now() );
		
		piloto2.adicionaVolta(1, VINTE_SEGUNDOS, LocalTime.now() );
		piloto2.adicionaVolta(2, TRINTA_SEGUNDOS, LocalTime.now() );
		piloto2.adicionaVolta(3, TRINTA_SEGUNDOS, LocalTime.now() );
		
		assertTrue(piloto1.compareTo(piloto2) > 0);
	}

}
