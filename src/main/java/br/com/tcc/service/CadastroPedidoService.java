package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Pedido;
import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.security.Seguranca;
import br.com.tcc.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;

	@Transactional
	public Pedido salvar(Pedido pedido) {

		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.PENDENTE);

		}

		if (pedido.isNaoAlteravel()) {
			throw new NegocioException(
					"Pedido nao pode ser alterado no status "
							+ pedido.getStatus().getDescricao() + ".");
		}

		pedido.recalcularTotalItens();

		if (pedido.getItens().isEmpty()) {
			throw new NegocioException(
					"O pedido deve possuir no minimo um item. ");
		}

		return pedidoRepository.guardar(pedido);
	}
}
