package br.senai.sp.info.patrimonio.ianes.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Patrimonio;

@Repository
@Transactional
public class ItemPatrimonioJPA implements ItemPatrimonioDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistir(ItemPatrimonio obj) {
		sessionFactory.getCurrentSession().persist(obj);
	}

	@Override
	public void alterar(ItemPatrimonio obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public void deletar(ItemPatrimonio obj) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM Movimentacao mov WHERE mov.item.id = :id").setParameter("id", obj.getId()).executeUpdate();
		sessionFactory.getCurrentSession().delete(obj);
	}

	@Override
	public ItemPatrimonio buscar(Long id) {
		String hql = "FROM ItemPatrimonio item WHERE item.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<ItemPatrimonio> resultado = query.list();
	
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<ItemPatrimonio> buscarTodos() {
		String hql = "FROM ItemPatrimonio";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<ItemPatrimonio> buscarItensDoAmbiente(Long id) {
		String hql = "FROM ItemPatrimonio item WHERE item.ambiente.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		return query.list();
	}

	@Override
	public List<ItemPatrimonio> buscarItensDoPatrimonio(Long id) {
		String hql = "FROM ItemPatrimonio item WHERE item.patrimonio.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		return query.list();
	}

	@Override
	public void deletarPorPatrimonio(Long id) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM ItemPatrimonio item WHERE item.patrimonio.id = :id").setParameter("id", id).executeUpdate();
		
	}

	
	
	/*public List<ItemPatrimonio> buscarPorPatrimonioEAmbiente(Long patrimonioId, Long ambienteId) {
		String hql = "FROM ItemPatrimonio item WHERE item.patrimonio.id = :patrimonioId && FROM ItemPatrimonio item WHERE item.ambiente.id = :ambienteId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id, id", patrimonioId, ambienteId);
		
		return query.list();
	}*/	
	
}
