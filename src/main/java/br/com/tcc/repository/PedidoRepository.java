package br.com.tcc.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import br.com.tcc.model.DataValor;
import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.filter.PedidoFilter;

public class PedidoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@SuppressWarnings({ "unchecked" })
	public Map<Date, Long> itensTotaisPorData(Long numeroDeDias, Usuario criadoPor, StatusPedido statusPedido) {
		Session session = entityManager.unwrap(Session.class);

		numeroDeDias -= 1;

		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, (int) (numeroDeDias * -1));

		Map<Date, Long> resultado = criarMapaVazio(numeroDeDias, dataInicial);

		Criteria criteria = session.createCriteria(Pedido.class);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("date(data_criacao) as data", "date(data_criacao)",
						new String[] { "data" }, new Type[] { StandardBasicTypes.DATE }))
				.add(Projections.sum("totalItemPedido").as("valor")))
				.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));

		if (criadoPor != null) {
			criteria.add(Restrictions.eq("usuarioSolicitante", criadoPor));
		}

		if (statusPedido != null) {
			criteria.add(Restrictions.eq("status", statusPedido));
		}

		List<DataValor> valoresPorData = criteria.setResultTransformer(Transformers.aliasToBean(DataValor.class))
				.list();

		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}

		return resultado;
	}

	private Map<Date, Long> criarMapaVazio(Long numeroDeDias, Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, Long> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), Long.valueOf(0));
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}

	public List<Pedido> meusPedidos(Long id) {
		return this.entityManager
				.createQuery("from Pedido where aprovador_id = :usuarioAprovador and status='EMITIDO'", Pedido.class)
				.setParameter("usuarioAprovador", id).getResultList();
	}

	public List<Pedido> minhasSolicitacoes(Long id) {
		return this.entityManager.createQuery("from Pedido where solicitante_id = :usuarioSolicitante", Pedido.class)
				.setParameter("usuarioSolicitante", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = this.entityManager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Pedido.class);

		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a
			// filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a
			// filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}

		if (filtro.getDataAprovacaoDe() != null) {
			criteria.add(Restrictions.ge("dataAprovacao", filtro.getDataAprovacaoDe()));
		}

		if (filtro.getDataAprovacaoAte() != null) {
			criteria.add(Restrictions.le("dataAprovacao", filtro.getDataAprovacaoAte()));
		}

		// if (StringUtils.isNotBlank(filtro.getNomeSolicitante())) {
		// acessamos o nome do cliente associado ao pedido pelo alias "c",
		// criado anteriormente
		// criteria.add(Restrictions.ilike("s.nome",
		// filtro.getNomeSolicitante(), MatchMode.ANYWHERE));
		// }

		// if (StringUtils.isNotBlank(filtro.getNomeAprovador())) {
		// acessamos o nome do vendedor associado ao pedido pelo alias "v",
		// criado anteriormente
		// criteria.add(Restrictions.ilike("a.nome", filtro.getNomeAprovador(),
		// MatchMode.ANYWHERE));
		// }

		if (filtro.getStatus() != null && filtro.getStatus().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes
			// da enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatus()));
		}

		return criteria.addOrder(Order.asc("id")).list();
	}

	public Pedido guardar(Pedido pedido) {

		return this.entityManager.merge(pedido);
	}

	public Pedido porId(Long id) {
		return entityManager.find(Pedido.class, id);
	}

}
