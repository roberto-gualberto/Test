package br.com.tcc.model;

public enum TipoPaiol {

	CASA_MATA("Casa Mata"),
	PAREDE_DUPLA("Parede Dupla"),
	IGLOO("Igloo");
	
	private String descricao;
	
	private TipoPaiol(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
