package br.senai.sp.info.patrimonio.ianes.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.patrimonio.ianes.dao.CategoriaPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.models.CategoriaPatrimonio;

@Repository
@Transactional
public class CategoriaPatrimonioJPA implements CategoriaPatrimonioDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistir(CategoriaPatrimonio obj) {
		sessionFactory.getCurrentSession().persist(obj);
		
	}

	@Override
	public void alterar(CategoriaPatrimonio obj) {
		sessionFactory.getCurrentSession().update(obj);
		
	}

	@Override
	public void deletar(CategoriaPatrimonio obj) {
		sessionFactory.getCurrentSession().delete(obj);
		
	}

	@Override
	public CategoriaPatrimonio buscar(Long id) {
		String hql = "FROM CategoriaPatrimonio categoria WHERE categoria.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<CategoriaPatrimonio> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<CategoriaPatrimonio> buscarTodos() {
		String hql = "FROM CategoriaPatrimonio";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		return query.list();
	}

	@Override
	public CategoriaPatrimonio buscarPorNome(String nome) {
		
		String hql = "FROM CategoriaPatrimonio categoria WHERE categoria.nome = :nome";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("nome", nome);
		List<CategoriaPatrimonio> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

}
