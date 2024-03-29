package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.BusinessException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;
	
	@Parameter
	public List<Filme> filme;
	
	@Parameter(value = 1)
	public Double valorLocacao;

	@Parameter(value = 2)
	public String cenario;

	private SPCService spcService;
	private LocacaoDao dao;
	
	@Before
	public void before() {
		service = new LocacaoService();
		dao = Mockito.mock(LocacaoDao.class);
		spcService = Mockito.mock(SPCService.class);
		service.setLocacaoDao(dao);
		service.setSPCService(spcService);
	}

	private static Filme filme1 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	private static Filme filme2 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	private static Filme filme3 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	private static Filme filme4 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	private static Filme filme5 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	private static Filme filme6 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	private static Filme filme7 = FilmeBuilder.Filme().getFilmePreco4().getFilme();
	
	@Parameters(name ="{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] { 
			{Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem desconto"},
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem desconto"}
			});
	}

	@Test
	public void testeValorLocacaoDesconto() throws BusinessException {
		Usuario usuario = new Usuario("Usuario 1");

		Locacao locacao = service.alugarFilme(usuario, filme);

		assertEquals(locacao.getValor(), valorLocacao, 0.01);
	}
}
