package br.com.tcc.repository.filter;

import java.io.Serializable;

import br.com.tcc.model.Grupo;
import br.com.tcc.model.TipoPostoGraduacao;
import br.com.tcc.validation.CODIGO;

public class UsuarioFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String identidade;
	private String nome;	
	
	public String getIdentidade() {
		return identidade;
	}
	
	public void setIdentidade(String identidade) {
		this.identidade = identidade == null ? null : identidade.toUpperCase();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
