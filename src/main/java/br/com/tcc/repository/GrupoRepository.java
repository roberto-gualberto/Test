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
import br.com.tcc.model.Grupo;
import br.com.tcc.repository.filter.ArtefatoFilter;
import br.com.tcc.service.NegocioException;
import br.com.tcc.util.jpa.Transactional;

public class GrupoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	public List<Grupo> buscarGrupos() {
		return manager.createQuery("from Grupo",
				Grupo.class).getResultList();
	}

	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

}
