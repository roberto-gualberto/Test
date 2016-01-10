package br.com.tcc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.repository.filter.PedidoFilter;
import br.com.tcc.security.Seguranca;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Pedido> pedidosFiltrados;
	private List<Pedido> meusPedidosFiltrados;
	private List<Pedido> minhasSolicitacoesFiltradas;

	@Inject
	private PedidoRepository pedidoRepository;

	private PedidoFilter filtro;

	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			Seguranca idLogado = new Seguranca();

			meusPedidosFiltrados = pedidoRepository.meusPedidos(idLogado
					.getIdLogado().longValue());
			minhasSolicitacoesFiltradas = pedidoRepository.minhasSolicitacoes(idLogado.getIdLogado().longValue());
		}
	}

	public void pesquisar() {
		pedidosFiltrados = pedidoRepository.filtrados(filtro);
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public List<Pedido> getMeusPedidosFiltrados() {
		return meusPedidosFiltrados;
	}

	public StatusPedido[] getStatus() {
		return StatusPedido.values();
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}

	public List<Pedido> getMinhasSolicitacoesFiltradas() {
		return minhasSolicitacoesFiltradas;
	}

	
	
}
