package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void teste() {
		assertTrue(true);
		assertFalse(false);
		
		assertEquals(1, 1);
		assertEquals(0.5122, 0.5124 , 0.001);
		assertEquals(Math.PI, 3.14 , 0.01);
		
		int i = 5;
		Integer i2 = 5;
		
		assertEquals(Integer.valueOf(i), i2);
		assertEquals(i, i2.intValue());
		
		assertEquals("bola", "bola");
		assertNotEquals("Bola", "casa");
		assertTrue("bola".equalsIgnoreCase("Bola"));
		assertTrue("bola".startsWith("bo"));
	
		Usuario u1 = new Usuario();
		Usuario u2 = new Usuario();
		Usuario u3 = null;
		
		assertEquals(u1, u2);
		assertNotSame(u1, u2);
		
		assertNull(u3);
		assertNotNull(u2);
		
		
		
	}
}
