package br.com.tcc.repository.filter;

import java.io.Serializable;

import br.com.tcc.validation.CODIGO;

public class ArtefatoFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nome;
	
	@CODIGO
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo == null ? null : codigo.toUpperCase();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
