package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.BusinessException;

public class CalculadoraTDDTest {
	
	public Calculadora calc;
	
	@Before
	public void before() {
		calc = new Calculadora();
	}
	
	@Test
	public void testeSomar() {
		int a = 5;
		int b = 5;
		
		int result = calc.somar(a, b);
		
		assertEquals(10, result);
		
	}

	@Test
	public void testeSubtrair() {
		int a = 5;
		int b = 5;
		
		int result = calc.subtrair(a, b);
		
		assertEquals(0, result);
		
	}

	@Test
	public void testeDividir() throws BusinessException {
		int a = 5;
		int b = 5;
		int result = calc.dividir(a, b);
		
		assertEquals(1, result);
		
	}
	
	@Test
	public void testeDividirPorZero(){
		int a = 5;
		int b = 0;
		
		try {
			int result = calc.dividir(a, b);
		} catch (BusinessException e) {
			assertEquals(e.getMessage(), "Não é possivel dividir por zero.");
		}
	}
}