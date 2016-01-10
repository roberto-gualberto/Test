package br.com.tcc.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
import br.com.tcc.repository.filter.ArtefatoFilter;
import br.com.tcc.service.NegocioException;
import br.com.tcc.util.jpa.Transactional;

public class ArtefatoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Artefato guardar(Artefato artefato) {

		return artefato = entityManager.merge(artefato);

	}

	@Transactional
	public void remover(Artefato artefato) {
		try {
			// apenas exclui se o artefato nao estiver sendo usando
			artefato = porId(artefato.getId());
			entityManager.remove(artefato);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Artefato nao pode ser excluido");
		}

	}

	public Artefato porCodigo(String codigo) {
		try {
			return entityManager
					.createQuery("from Artefato where upper(codigo) = :codigo",
							Artefato.class)
					.setParameter("codigo", codigo.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Artefato> filtrados(ArtefatoFilter filter) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Artefato.class);

		if (StringUtils.isNotBlank(filter.getCodigo())) {
			criteria.add(Restrictions.eq("codigo", filter.getCodigo()));
		}

		if (StringUtils.isNotBlank(filter.getNome())) {
			criteria.add(Restrictions.ilike("nome", filter.getNome(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Artefato porId(Long id) {

		return entityManager.find(Artefato.class, id);
	}

	public List<Artefato> porNome(String nome) {

		return this.entityManager
				.createQuery("from Artefato where upper(nome) like :nome",
						Artefato.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

}
