package br.com.tcc.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tcc.model.Pedido;
import br.com.tcc.service.AprovarPedidoService;
import br.com.tcc.service.CancelamentoPedidoService;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@RequestScoped
public class AprovarPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AprovarPedidoService aprovarPedidoService; 

	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void aprovarPedido(){
		this.pedido = this.aprovarPedidoService.aprovar(this.pedido);
		this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));
		
		FacesUtil.addInfoMessage("Pedido aprovado com sucesso!");
	
	}
	
}
