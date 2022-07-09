package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.BusinessException;

public class LocacaoServiceTest {

	public LocacaoService service;
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException erro = ExpectedException.none();

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	
	@Before
	public void before() {
		service = new LocacaoService();
	}
	
	@Test(expected = BusinessException.class)
	public void TesteFilmeForaDeEstoque() throws Exception {
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Fime 1", 1, 5.0);
		Filme filme2 = new Filme("Fime 2", 0, 5.0);
		Filme filme3 = new Filme("Fime 3", 1, 5.0);
		
		service.alugarFilme(usuario, Arrays.asList(filme, filme2, filme3));
	}

	@Test
	public void testeUsuarioVazio() {
		Filme filme = new Filme("Fime 1", 1, 5.0);

		try {
			service.alugarFilme(null, Arrays.asList(filme));
			fail();
		} catch (BusinessException e) {
			assertEquals(e.getMessage(), "Usuario nao pode ser nullo");
		}
	}

	@Test
	public void testeFilmeVazio() throws BusinessException {
		Usuario usuario = new Usuario("Usuario 1");
		
		erro.expect(BusinessException.class);
		erro.expectMessage("Filme nao pode ser nullo.");
		service.alugarFilme(usuario, null);
	}

}
