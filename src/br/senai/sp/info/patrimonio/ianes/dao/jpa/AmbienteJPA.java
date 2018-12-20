package br.senai.sp.info.patrimonio.ianes.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.patrimonio.ianes.dao.AmbienteDAO;
import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.models.Ambiente;

@Repository
@Transactional
public class AmbienteJPA implements AmbienteDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MovimentacaoDAO movimentacaoDAO;
	
	@Autowired
	private ItemPatrimonioDAO itemDAO;

	@Override
	public void persistir(Ambiente obj) {
		sessionFactory.getCurrentSession().persist(obj);
	}

	@Override
	public void alterar(Ambiente obj) {
		
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public void deletar(Ambiente obj) {
		//Excluindo os itens que se encontram no determinado ambiente
		sessionFactory.getCurrentSession().createQuery("DELETE FROM ItemPatrimonio item WHERE item.ambiente.id = :id").setParameter("id", obj.getId()).executeUpdate();
		//Excluindo as movimentações relacionadas ao ambiente determinado
		sessionFactory.getCurrentSession().createQuery("DELETE FROM Movimentacao mov WHERE mov.ambienteOrigem.id = :id or mov.ambienteDestino.id = :id").setParameter("id", obj.getId()).executeUpdate();
		sessionFactory.getCurrentSession().delete(obj);
	
	}

	@Override
	public Ambiente buscar(Long id) {
		String hql = "FROM Ambiente amb WHERE amb.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Ambiente> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<Ambiente> buscarTodos() {
		String hql = "FROM Ambiente";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public Ambiente buscarPorNome(String nome) {
		String hql = "FROM Ambiente amb WHERE amb.nome = :nome";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("nome", nome);
		
		List<Ambiente> resultado = query.list();
		
		if(!resultado.isEmpty()) {
			return resultado.get(0);
		}else {
			return null;
		}
	}

}
