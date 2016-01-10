package br.com.tcc.repository.filter;

import java.io.Serializable;

import br.com.tcc.model.Grupo;
import br.com.tcc.model.TipoPostoGraduacao;
import br.com.tcc.validation.CODIGO;

public class CategoriaFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String descricao;	
	
	public String getDescricao() {
		return descricao;
	}
	
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
