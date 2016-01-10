package br.com.tcc.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.ItemPedido;
import br.com.tcc.model.Pedido;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.repository.UsuarioRepository;
import br.com.tcc.security.Seguranca;
import br.com.tcc.service.CadastroPedidoService;
import br.com.tcc.util.jsf.FacesUtil;
import br.com.tcc.validation.CODIGO;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuariosRepository;

	@Inject
	private CadastroPedidoService cadastroPedidoService;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private ArtefatoRepository artefatoRepository;

	@Produces
	@PedidoEdicao
	private Pedido pedido;

	private ItemPedido totalItens;

	private Artefato artefatoLinhaEditavel;

	private String codigo;

	private List<Usuario> solicitante;

	public CadastroPedidoBean() {
		limpar();
	}

	public void inicializar() {
		calcularTotalItens();
		if (FacesUtil.isNotPostback()) {
			
			this.pedido.adicionarItemVazio();
					
			if (isEditando()) {
				this.solicitante = this.usuariosRepository.solicitante();
			} else {

				Seguranca usuariologado = new Seguranca();
				

				this.solicitante = this.usuariosRepository
						.solicitanteLogado(usuariologado.getIdLogado()
								.longValue());
			}
		}
	}

	private void limpar() {
		pedido = new Pedido();
		totalItens = new ItemPedido();
	}

	public void salvar() {
		this.pedido.removerItemVazio();
		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);

			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

	public void carregarArtefatoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);

		if (this.artefatoLinhaEditavel != null) {
			if (this.existeItemComArtefato(this.artefatoLinhaEditavel)) {
				FacesUtil
						.addErrorMessage("Ja existe um item no pedido com o artefato informado");
			} else {
				item.setArtefato(this.artefatoLinhaEditavel);

				this.pedido.adicionarItemVazio();
				this.artefatoLinhaEditavel = null;
				this.codigo = null;

			}
		}
	}
	
	//cria um metodo que ira observar se o estado do pedido e alterado
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event){
		this.pedido = event.getPedido();
	}

	private boolean existeItemComArtefato(Artefato artefato) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (artefato.equals(item.getArtefato())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public void calcularTotalItens() {
		this.pedido.recalcularTotalItens();
	}

	public void atualizarQuantidade(ItemPedido item, int linha) {

		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}

		this.pedido.recalcularTotalItens();
	}

	public void carregarArtefatoPorCodigo() {
		if (StringUtils.isNotEmpty(this.codigo)) {
			this.artefatoLinhaEditavel = this.artefatoRepository
					.porCodigo(this.codigo);
			this.carregarArtefatoLinhaEditavel();
		}
	}

	public List<Usuario> completarAprovador(String nome) {

		return this.usuariosRepository.aprovador(nome);
	}
	
	
	public List<Artefato> completarArtefato(String nome) {
		return this.artefatoRepository.porNome(nome);
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getSolicitante() {
		return solicitante;
	}

	public Artefato getArtefatoLinhaEditavel() {
		return artefatoLinhaEditavel;
	}

	public void setArtefatoLinhaEditavel(Artefato artefatoLinhaEditavel) {
		this.artefatoLinhaEditavel = artefatoLinhaEditavel;
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;
	}

	@CODIGO
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ItemPedido getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(ItemPedido totalItens) {
		this.totalItens = totalItens;
	}

}