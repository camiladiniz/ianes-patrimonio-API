package br.senai.sp.info.patrimonio.ianes.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.models.Movimentacao;

@Repository
@Transactional
public class MovimentacaoJPA implements MovimentacaoDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistir(Movimentacao obj) {
		sessionFactory.getCurrentSession().persist(obj);
		
	}

	@Override
	public void alterar(Movimentacao obj) {
		
	}

	@Override
	public void deletar(Movimentacao obj) {
		
	}

	@Override
	public Movimentacao buscar(Long id) {
		String hql = "FROM Movimentacao mov WHERE mov.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Movimentacao> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<Movimentacao> buscarTodos() {
		String hql = "FROM Movimentacao";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<Movimentacao> buscarMovimentacoesDeDeterminadoItem(Long id) {
		String hql = "FROM Movimentacao mov WHERE mov.item.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		return query.list();
	}

	@Override
	public void deletarPorPatrimonio(Long id) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM Movimentacao mov WHERE mov.item.patrimonio.id = :id").setParameter("id", id).executeUpdate();	
	}
	

}
