package br.senai.sp.info.patrimonio.ianes.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.patrimonio.ianes.dao.UsuarioDAO;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

@Repository
@Transactional
public class UsuarioJPA implements UsuarioDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void alterar(Usuario obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public Usuario buscar(Long id) {
		String hql = "FROM Usuario u WHERE u.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Usuario> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public Usuario buscarPorEmail(String email) {
		String hql = "FROM Usuario u WHERE u.email = :email";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		query.setParameter("email", email);
		
		List<Usuario> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			//Email existe
			return resultado.get(0);
		}else {
			//Email não existe
			return null;
		}
	}

	@Override
	public List<Usuario> buscarTodos() {
		String hql = "FROM Usuario";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public void deletar(Usuario usuario) {
		sessionFactory.getCurrentSession().delete(usuario);
	}

	@Override
	@Transactional
	public void persistir(Usuario obj) {
		sessionFactory.getCurrentSession().persist(obj);
		
	}

	@Override
	public Usuario buscarPorEmailESenha(String email, String senha) {
		String hql = "FROM Usuario u WHERE u.email = :email AND u.senha = :senha";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		
		//Dispara a busca e guarda o resultado em uma lista
		List<Usuario> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

}
