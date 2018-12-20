package br.senai.sp.info.patrimonio.ianes.dao;

import java.util.List;

import br.senai.sp.info.patrimonio.ianes.models.Usuario;

public interface UsuarioDAO extends DAO<Usuario> {
	
	public Usuario buscarPorEmail(String email);
	public Usuario buscarPorEmailESenha(String email, String senha);
}
