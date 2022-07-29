package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {
	}
	
	public static UsuarioBuilder Usuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Lucas");
		return builder;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}

