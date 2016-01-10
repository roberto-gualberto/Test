package br.com.tcc.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Categoria;
import br.com.tcc.model.Grupo;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.filter.CategoriaFilter;
import br.com.tcc.repository.filter.UsuarioFilter;
import br.com.tcc.service.NegocioException;
import br.com.tcc.util.jpa.Transactional;

public class CategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	
	public Categoria guardar(Categoria categoria){
		return categoria = entityManager.merge(categoria);
	}

	public List<Categoria> buscarCategoriaRaizes() {
		return entityManager.createQuery("from Categoria where categoriaPai is null",
				Categoria.class).getResultList();
	}

	public Categoria porId(Long id) {
		return entityManager.find(Categoria.class, id);
	}

	public List<Categoria> subcategoriaDe(Categoria categoriaPai) {
		return entityManager
				.createQuery("from Categoria where categoriaPai = :raiz",
						Categoria.class).setParameter("raiz", categoriaPai)
				.getResultList();
	}

	public Categoria porDescricao(String descricao) {
		try {
			return entityManager
					.createQuery("from Categoria where upper(descricao) = :descricao",
							Categoria.class)
					.setParameter("descricao", descricao.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Categoria> buscarCategorias() {
		return entityManager.createQuery("from Categoria",
				Categoria.class).getResultList();
	}

	@Transactional
	public void remover(Categoria categoriaSelecionada) {
		try {
			// nao estiver sendo usando
			categoriaSelecionada = porId(categoriaSelecionada.getId());
			entityManager.remove(categoriaSelecionada);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Categoria nao pode ser excluida");
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> filtrados(CategoriaFilter filter) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Categoria.class);

		if (StringUtils.isNotBlank(filter.getDescricao())) {
			criteria.add(Restrictions.ilike("descricao", filter.getDescricao(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("id")).list();
	}

	

}
