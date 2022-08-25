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
import org.mockito.Mockito;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDao;
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

	private SPCService spcService;
	private LocacaoDao dao;
	
	@Before
	public void before() {
		service = new LocacaoService();
		dao = Mockito.mock(LocacaoDao.class);
		service.setLocacaoDao(dao);
		spcService = Mockito.mock(SPCService.class);
		service.setSPCService(spcService);
	}

	@Test(expected = BusinessException.class)
	public void TesteFilmeForaDeEstoque() throws Exception {
		Usuario usuario = UsuarioBuilder.Usuario().getUsuario();
		Filme filme = new Filme("Fime 1", 1, 5.0);
		Filme filme2 = new Filme("Fime 2", 0, 5.0);
		Filme filme3 = new Filme("Fime 3", 1, 5.0);

		service.alugarFilme(usuario, Arrays.asList(filme, filme2, filme3));
	}

	@Test
	public void testeUsuarioVazio() {
		Filme filme = FilmeBuilder.Filme().getFilme();

		try {
			service.alugarFilme(null, Arrays.asList(filme));
			fail();
		} catch (BusinessException e) {
			assertEquals(e.getMessage(), "Usuario nao pode ser nullo");
		}
	}

	@Test
	public void testeFilmeVazio() throws BusinessException {
		Usuario usuario = UsuarioBuilder.Usuario().getUsuario();

		erro.expect(BusinessException.class);
		erro.expectMessage("Filme nao pode ser nullo.");
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void locacaoNoSabado() throws BusinessException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		Usuario usuario = UsuarioBuilder.Usuario().getUsuario();
		List<Filme> filme = Arrays.asList(FilmeBuilder.Filme().getFilme());
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		boolean isSegunda= DataUtils.verificarDiaSemana(locacao.getDataLocacao(), Calendar.MONDAY);
		assertFalse(isSegunda);
	}
	
	@Test
	public void naoDeveAlugarFilmeNegativado() throws BusinessException {
		Usuario usuario = UsuarioBuilder.Usuario().getUsuario();
		List<Filme> filme = Arrays.asList(FilmeBuilder.Filme().getFilme());
		
		Mockito.when(spcService.isNegativado(usuario)).thenReturn(true);
		
		erro.expect(BusinessException.class);
		erro.expectMessage("O usuário esta negativado no SPC.");
		
		service.alugarFilme(usuario, filme);

	}
}
