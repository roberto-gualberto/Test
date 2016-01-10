package br.com.tcc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.tcc.service.NegocioException;
import br.com.tcc.validation.CODIGO;

@Entity
@Table(name = "artefato")
public class Artefato implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String codigo;
	private Integer quantidadeEstoque;
	private Date dataCadastro;
	private Date dataValidade;
	private Categoria categoria;
	private TipoPaiol tipoPaiol;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotBlank
	@CODIGO
	@Column(nullable = false, length = 20, unique = true)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotNull
	@Min(0)
	@Max(value = 9999, message = "tem o valor muito alto")
	@Column(name = "quantidade_estoque", nullable = false, length = 5)
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	//@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_validade", nullable = false)
	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_paiol", nullable = false, length = 20)
	public TipoPaiol getTipoPaiol() {
		return tipoPaiol;
	}

	public void setTipoPaiol(TipoPaiol tipoPaiol) {
		this.tipoPaiol = tipoPaiol;
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
		Artefato other = (Artefato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void baixarEstoque(Integer quantidade) {

		int novaQuantidade = this.getQuantidadeEstoque() - quantidade;
		if (novaQuantidade < 0) {
			throw new NegocioException("Nao ha disponibilidade no estoque de "
					+ quantidade + " itens do artefato " + this.getCodigo()
					+ " .");
		}

		this.setQuantidadeEstoque(novaQuantidade);
	}

	public void adicionaEstoque(Integer quantidade) {
		this.setQuantidadeEstoque(getQuantidadeEstoque() + quantidade);

	}
}
