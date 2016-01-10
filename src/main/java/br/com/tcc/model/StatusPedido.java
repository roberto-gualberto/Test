package br.com.tcc.model;

public enum StatusPedido {

	PENDENTE("Pendente"),
	EMITIDO("Emitido"), 
	APROVADO("Aprovado"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	private StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
