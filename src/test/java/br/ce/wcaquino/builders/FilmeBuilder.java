package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Usuario;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {
	}
	
	public static FilmeBuilder Filme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setNome("Sharknado");
		builder.filme.setEstoque(2);
		builder.filme.setPrecoLocacao(5.0);;
		return builder;
	}
	
	public FilmeBuilder getFilmePreco4() {
		filme.setPrecoLocacao(4.0);
		return this;
	}
	
	public Filme getFilme() {
		return filme;
	}
}

