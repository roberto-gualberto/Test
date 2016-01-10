package br.com.tcc.model;

public enum Divisao {

	DIVISAO_TECNICA("DT - Divisao Tecnica"), 
	DIVISAO_OPERACAO("DO - Divisao Operacao"),
	DIVISAO_ARTILHARIA("DA - Divisao Artilharia"),
	DIVISAO_CAVALARIA("DC - Divisao Cavalaria"),
	DIVISAO_INFANTARIA("DI - Divisao Infantaria");
	
	
	private String descricao;

	private Divisao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
