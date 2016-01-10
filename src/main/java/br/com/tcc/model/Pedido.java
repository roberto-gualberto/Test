package br.com.tcc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.tcc.model.ItemPedido;
import br.com.tcc.model.Pedido;
import br.com.tcc.model.Artefato;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.security.Seguranca;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataCriacao;
	private Date dataAprovacao;
	private Date dataEmitido;
	private Date dataCancelado;
	private String observacao;
	private Usuario usuarioSolicitante;
	private Usuario usuarioAprovador;
	private StatusPedido status = StatusPedido.PENDENTE;
	private List<ItemPedido> itens = new ArrayList<>();
	private Integer totalItemPedido = 0;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	// @NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_aprovacao")
	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_emitido")
	public Date getDataEmitido() {
		return dataEmitido;
	}

	public void setDataEmitido(Date dataEmitido) {
		this.dataEmitido = dataEmitido;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cancelado")
	public Date getDataCancelado() {
		return dataCancelado;
	}

	public void setDataCancelado(Date dataCancelado) {
		this.dataCancelado = dataCancelado;
	}

	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "solicitante_id", nullable = false)
	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "aprovador_id", nullable = false)
	public Usuario getUsuarioAprovador() {
		return usuarioAprovador;
	}

	public void setUsuarioAprovador(Usuario usuarioAprovador) {
		this.usuarioAprovador = usuarioAprovador;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}


	@Column(nullable = false, length = 5)
	public Integer getTotalItemPedido() {
		return totalItemPedido;
	}

	public void setTotalItemPedido(Integer totalItemPedido) {
		this.totalItemPedido = totalItemPedido;
	}

	@Transient
	public boolean isNovo() {
		return getId() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void adicionarItemVazio() {
		if (this.isPendente()) {
			Artefato artefato = new Artefato();
			// artefato.setQuantidadeEstoque(1);

			ItemPedido item = new ItemPedido();
			item.setQuantidade(1);
			item.setArtefato(artefato);
			item.setPedido(this);

			this.getItens().add(0, item);
		}

	}

	@Transient
	public boolean isPendente() {
		return StatusPedido.PENDENTE.equals(this.getStatus());
	}

	public void removerItemVazio() {
		ItemPedido primeiroItem = this.getItens().get(0);

		if (primeiroItem != null && primeiroItem.getArtefato().getId() == null) {
			this.getItens().remove(0);
		}
	}

	@Transient
	public boolean isEmitido() {

		return StatusPedido.EMITIDO.equals(this.getStatus());
	}

	@Transient
	public boolean isEmissivel() {
		Seguranca idLogado = new Seguranca();
		Long idUsuario = idLogado.getIdLogado();
		if (id == null) {
			usuarioSolicitante = new Usuario();
		}
		if (idUsuario.equals(usuarioSolicitante.getId())) {
			return this.isExistente() && this.isPendente();
		} else {
			return false;
		}
	}

	@Transient
	public boolean isNaoEmissivel() {

		return !this.isEmissivel();
	}

	

	@Transient
	private boolean isCancelado() {

		return StatusPedido.CANCELADO.equals(this.getStatus());
	}

	@Transient
	public boolean isNaoCancelavel() {

		return !this.isCancelavel();
	}
	
	@Transient
	public boolean isCancelavel() {
		Seguranca idLogado = new Seguranca();
		Long id = idLogado.getIdLogado();
		if (isNovo()) {
			usuarioSolicitante = new Usuario();
			usuarioAprovador = new Usuario();
		}
		if (this.isPendente() && id.equals(usuarioSolicitante.getId())) {
			System.out.println("status-----------------------pendente "
					+ this.isPendente());
			return this.isExistente() && !this.isCancelado()
					&& !this.isAprovado();
		} else {
			System.out
					.println("passou aqui no false...........................");

			if (id != usuarioSolicitante.getId()
					&& id == usuarioAprovador.getId()) {
				System.out.println("===================== solicitante = > "
						+ usuarioSolicitante.getId());
				System.out.println("===================== aprovador = > "
						+ usuarioAprovador.getId());
				return this.isExistente() && !this.isCancelado()
						&& !this.isAprovado();
			} else {
				System.out.println("===================== +++++++++++++++++>"
						+ usuarioSolicitante.getId());
				return false;
			}
		}
	}
	@Transient
	public boolean isAprovavel() {
		Seguranca idLogado = new Seguranca();
		Long id = idLogado.getIdLogado();
		if (isNovo()) {
			usuarioSolicitante = new Usuario();
			usuarioAprovador = new Usuario();
		}
		if (id != usuarioSolicitante.getId() && id == usuarioAprovador.getId()) {

			return this.isEmitido();
		} else {
			return false;
		}

	}

	@Transient
	private boolean isAprovado() {

		return StatusPedido.APROVADO.equals(this.getStatus());
	}

	@Transient
	public boolean isNaoAprovavel() {

		return !this.isAprovavel();
	}

	@Transient
	public boolean isAlteravel() {
		Seguranca idLogado = new Seguranca();
		Long idUsuario = idLogado.getIdLogado();

		if (id == null) {
			return this.isPendente();
		} else if (id != null) {
			if (idUsuario.equals(usuarioSolicitante.getId())) {
				return this.isPendente();
			}
		} else {
			return false;
		}
		return false;
	}

	@Transient
	public boolean isNaoAlteravel() {

		return !this.isAlteravel();

	}

	@Transient
	public void recalcularTotalItens() {

		Integer total = 0;

		for (ItemPedido item : this.getItens()) {
			if (item.getArtefato() != null
					&& item.getArtefato().getId() != null) {
				total = total + item.getQuantidade();
			}
		}

		this.setTotalItemPedido(total);

	}

}
