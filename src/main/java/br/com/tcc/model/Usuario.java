package br.com.tcc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String identidade;
	private String nome;
	private String email;
	private Divisao divisao;
	private String senha;
	private TipoPostoGraduacao posto;
	private Grupo grupo;
	private Sexo sexo;
	private Date dataCadastro;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank
	@NotNull
	@Column(nullable = false, length = 12)
	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}
	
	@NotBlank
	@NotNull
	@Column(nullable = false, length = 80)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotBlank
	@NotNull
	@Column(nullable = false, unique = true, length = 250)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public Divisao getDivisao() {
		return divisao;
	}

	public void setDivisao(Divisao divisao) {
		this.divisao = divisao;
	}

	@NotBlank
	@NotNull
	@Column(nullable = false, length = 20)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	public TipoPostoGraduacao getPosto() {
		return posto;
	}

	public void setPosto(TipoPostoGraduacao posto) {
		this.posto = posto;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "grupo_id", nullable = false)
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
