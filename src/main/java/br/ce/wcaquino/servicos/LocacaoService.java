package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.BusinessException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	private LocacaoDao dao;
	private SPCService spcService;
	
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
		
		if(spcService.isNegativado(usuario)) {
			throw new BusinessException("O usuário esta negativado no SPC.");
		}

		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SATURDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		dao.salvar(locacao);

		return locacao;
	}
	
	public void setLocacaoDao(LocacaoDao dao) {
		this.dao = dao;
	}

	public void setSPCService(SPCService spcService) {
		this.spcService = spcService;
	}

}