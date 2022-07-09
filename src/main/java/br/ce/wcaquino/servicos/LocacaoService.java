package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.BusinessException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filme) throws BusinessException {
		if(filme == null || filme.isEmpty()) {
			throw new BusinessException("Filme nao pode ser nullo.");
		}

		if(filme.stream().anyMatch(x -> x.getEstoque() == 0)) {
			throw new BusinessException("Filme sem estoque");
		}
		
		if(usuario == null) {
			throw new BusinessException("Usuario nao pode ser nullo");
		}
		
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		double valor = 0d;
		
		for (Filme x : filme) {
			valor += x.getPrecoLocacao();
		}
		locacao.setValor(valor);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	public static void main(String[] args) throws Exception {
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Fime 1", 2, 5.0);
		
		List<Filme> filmes = new ArrayList<>();
		
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		System.out.println(locacao.getValor());
		System.out.println(locacao.getDataLocacao());
		System.out.println(locacao.getDataRetorno ());
	}
}