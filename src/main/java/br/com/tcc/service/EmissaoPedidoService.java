package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);
		
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO);
		pedido.setDataEmitido(new Date());
		
		pedido = this.pedidoRepository.guardar(pedido);
		
		return pedido;
	}
	
}
