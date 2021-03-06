package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.BusinessException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filme) throws BusinessException {
		if (filme == null || filme.isEmpty()) {
			throw new BusinessException("Filme nao pode ser nullo.");
		}

		if (filme.stream().anyMatch(x -> x.getEstoque() == 0)) {
			throw new BusinessException("Filme sem estoque");
		}

		if (usuario == null) {
			throw new BusinessException("Usuario nao pode ser nullo");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		double valorTotal = 0d;

		for (int i = 0; i < filme.size(); i++) {
			double valor = filme.get(i).getPrecoLocacao();
			switch (i) {
			case 2:
				valor *= 0.75;
				break;
			case 3:
				valor *= 0.50;
				break;
			case 4:
				valor *= 0.25;
				break;
			case 5:
				valor = 0d;
				break;
			default:
				break;
			}
			valorTotal += valor;
		}

		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SATURDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}

}