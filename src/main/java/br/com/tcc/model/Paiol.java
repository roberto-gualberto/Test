package br.com.tcc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

//@Entity
//@Table(name="paiol")
public class Paiol implements Serializable {

	private static final long serialVersionUID = 1L;
/*
 * 
 * 
 * 
 
	private Long id;
	private String descricao;
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
	@Size(max=80)
	@Column(nullable= false, length = 80)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_paiol",nullable = false, length = 20)
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
		Paiol other = (Paiol) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
*/
}
