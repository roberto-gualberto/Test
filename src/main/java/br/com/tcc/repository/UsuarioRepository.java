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
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.filter.ArtefatoFilter;
import br.com.tcc.repository.filter.UsuarioFilter;
import br.com.tcc.security.Seguranca;
import br.com.tcc.service.NegocioException;
import br.com.tcc.util.jpa.Transactional;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Usuario guardar(Usuario usuario) {

		return usuario = entityManager.merge(usuario);

	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			// nao estiver sendo usando
			usuario = porId(usuario.getId());
			entityManager.remove(usuario);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuario nao pode ser excluido");
		}

	}

	public Usuario porIdentidade(String identidade) {
		try {
			return entityManager
					.createQuery("from Usuario where identidade = :identidade",
							Usuario.class)
					.setParameter("identidade", identidade).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Usuario> solicitanteLogado(Long id) {
		return this.entityManager.createQuery("from Usuario where id =:id",Usuario.class).setParameter("id", id).getResultList();
		
	}

	public List<Usuario> porNome(String nome) {

		return this.entityManager
				.createQuery(
						"from Usuario where upper(nome) like :nome and grupo_id=1",
						Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
	
	
	public List<Usuario> aprovador(String nome) {
		Seguranca idLogado = new Seguranca();
		Long id = idLogado.getIdLogado();
		return this.entityManager
				.createQuery(
						"from Usuario where upper(nome) like :nome and grupo_id=1 and id <> :id",
						Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%").setParameter("id", id).getResultList();
	}
	
	public List<Usuario> solicitante() {
		return this.entityManager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filter) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(filter.getIdentidade())) {
			criteria.add(Restrictions.eq("identidade", filter.getIdentidade()));
		}

		if (StringUtils.isNotBlank(filter.getNome())) {
			criteria.add(Restrictions.ilike("nome", filter.getNome(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Usuario porId(Long id) {

		return entityManager.find(Usuario.class, id);
	}

	public Usuario porEmail(String email) {

		Usuario usuario = null;

		try {
			usuario = this.entityManager
					.createQuery("from Usuario where lower(email) = :email",
							Usuario.class)
					.setParameter("email", email.toLowerCase())
					.getSingleResult();
		} catch (NoResultException e) {
			// nada encontrado
		}
		return usuario;
	}

}
