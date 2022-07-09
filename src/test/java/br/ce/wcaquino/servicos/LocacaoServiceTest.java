package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.BusinessException;
import br.ce.wcaquino.utils.DataUtils;

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

	@Test
	public void teste25PorcentoDesconto() throws BusinessException {
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = new ArrayList();
		filmes.add(new Filme("Fime 1", 1, 4.0));
		filmes.add(new Filme("Fime 2", 1, 4.0));
		filmes.add(new Filme("Fime 3", 1, 4.0));
		
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		assertEquals(locacao.getValor(), 11.0, 0.01);
	}

	@Test
	public void teste50PorcentoDesconto() throws BusinessException {
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = new ArrayList();
		filmes.add(new Filme("Fime 1", 1, 4.0));
		filmes.add(new Filme("Fime 2", 1, 4.0));
		filmes.add(new Filme("Fime 3", 1, 4.0));
		filmes.add(new Filme("Fime 4", 1, 4.0));
		
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		assertEquals(locacao.getValor(), 13.0, 0.01);
	}

	@Test
	public void teste75PorcentoDesconto() throws BusinessException {
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = new ArrayList();
		filmes.add(new Filme("Fime 1", 1, 4.0));
		filmes.add(new Filme("Fime 2", 1, 4.0));
		filmes.add(new Filme("Fime 3", 1, 4.0));
		filmes.add(new Filme("Fime 4", 1, 4.0));
		filmes.add(new Filme("Fime 5", 1, 4.0));
		
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		assertEquals(locacao.getValor(), 14.0, 0.01);
	}

	@Test
	@Ignore
	public void teste100PorcentoDesconto() throws BusinessException {
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = new ArrayList();
		filmes.add(new Filme("Fime 1", 1, 4.0));
		filmes.add(new Filme("Fime 2", 1, 4.0));
		filmes.add(new Filme("Fime 3", 1, 4.0));
		filmes.add(new Filme("Fime 4", 1, 4.0));
		filmes.add(new Filme("Fime 5", 1, 4.0));
		filmes.add(new Filme("Fime 6", 1, 4.0));
		
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		assertEquals(locacao.getValor(), 14.0, 0.01);
	}
	
	@Test
	public void locacaoNoSabado() throws BusinessException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 1, 1.0));
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		boolean isSegunda= DataUtils.verificarDiaSemana(locacao.getDataLocacao(), Calendar.MONDAY);
		assertFalse(isSegunda);
	}
}
