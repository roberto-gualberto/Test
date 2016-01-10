package br.com.tcc.controller;

import br.com.tcc.model.Pedido;

public class PedidoAlteradoEvent {

	private Pedido pedido;
	
	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	
}
