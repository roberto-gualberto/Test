package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.util.jpa.Transactional;

public class AprovarPedidoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public Pedido aprovar(Pedido pedido) {
		pedido = this.pedidoRepository.porId(pedido.getId());
		
		if(pedido.isNaoAprovavel()){
			throw new NegocioException("Pedido nao pode ser aprovado no status "+pedido.getStatus().getDescricao()+".");
		}
	
	
		pedido.setStatus(StatusPedido.APROVADO);
		pedido.setDataAprovacao(new Date());
		pedido = pedidoRepository.guardar(pedido);
		
		return pedido;
	}

}
