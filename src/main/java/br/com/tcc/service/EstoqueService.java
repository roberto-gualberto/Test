package br.com.tcc.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.tcc.model.ItemPedido;
import br.com.tcc.model.Pedido;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.util.jpa.Transactional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido){
		pedido = this.pedidoRepository.porId(pedido.getId());
	
		for(ItemPedido item : pedido.getItens()){
			item.getArtefato().baixarEstoque(item.getQuantidade());
		}
	}

	public void retornarItensEstoue(Pedido pedido) {
		pedido = this.pedidoRepository.porId(pedido.getId());
		
		
		for(ItemPedido item : pedido.getItens()){
			item.getArtefato().adicionaEstoque(item.getQuantidade());
		}
		
	}
	
}
