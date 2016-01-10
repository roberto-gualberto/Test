package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedido = this.pedidoRepository.porId(pedido.getId());
		
		if(pedido.isNaoCancelavel()){
			throw new NegocioException("Pedido nao pode ser cancelado no status "+pedido.getStatus().getDescricao()+".");
		}
		
		if(pedido.isEmitido()){
			this.estoqueService.retornarItensEstoue(pedido);
		}
	
		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataCancelado(new Date());
		pedido = pedidoRepository.guardar(pedido);
		
		return pedido;
	}

}
