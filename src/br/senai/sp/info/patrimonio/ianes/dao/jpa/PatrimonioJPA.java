package br.senai.sp.info.patrimonio.ianes.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.PatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Patrimonio;

@Repository
@Transactional
public class PatrimonioJPA implements PatrimonioDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ItemPatrimonioDAO itemDAO;

	@Override
	public void persistir(Patrimonio obj) {
		sessionFactory.getCurrentSession().persist(obj);
	}

	@Override
	public void alterar(Patrimonio obj) {
		sessionFactory.getCurrentSession().update(obj);
		
	}

	@Override
	public void deletar(Patrimonio obj) {
		String hql = "FROM ItemPatrimonio item WHERE item.patrimonio.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", obj.getId());
		
		
		
		if(query.list() != null) {
			List<ItemPatrimonio> itens = query.list();
		
			for (ItemPatrimonio itemPatrimonio : itens) {
				itemDAO.deletar(itemPatrimonio);

				
			}
		}
		
		/*List<ItemPatrimonio> itens = query.list();
		System.out.println(itens.toString());
		System.out.println(itens.size());
		
		if(itens != null) {
			for (ItemPatrimonio itemPatrimonio : itens) {
				System.out.println(itemPatrimonio.toString());
				sessionFactory.getCurrentSession().createQuery("DELETE FROM Movimentacao mov WHERE mov.item.id = :idItem").setParameter("idItem", itemPatrimonio.getId()).executeUpdate();

				//sessionFactory.getCurrentSession().createQuery("DELETE FROM ItemPatrimonio item WHERE item.id = :itemId").setParameter("itemId", itemPatrimonio.getId()).executeUpdate();
			}
		}*/
		
		
		//sessionFactory.getCurrentSession().createQuery("DELETE FROM ItemPatrimonio item WHERE item.patrimonio.id = :id").setParameter("id", obj.getId()).executeUpdate();
		
		sessionFactory.getCurrentSession().delete(obj);
		
	}

	@Override
	public Patrimonio buscar(Long id) {
		String hql = "FROM Patrimonio pat WHERE pat.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Patrimonio> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<Patrimonio> buscarTodos() {
		String hql = "FROM Patrimonio";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		return query.list();
	}

	@Override
	public Patrimonio buscarPorNome(String nome) {
		
		String hql = "FROM Patrimonio patrimonio WHERE patrimonio.nome = :nome";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("nome", nome);
		List<Patrimonio> resultado = query.list();
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	
}
