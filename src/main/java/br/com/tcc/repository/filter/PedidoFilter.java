package br.com.tcc.repository.filter;

import java.io.Serializable;
import java.util.Date;

import br.com.tcc.model.Grupo;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.model.TipoPostoGraduacao;
import br.com.tcc.validation.CODIGO;

public class PedidoFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private Date dataAprovacaoDe;
	private Date dataAprovacaoAte;
	private String nomeAprovador;
	private String nomeSolicitante;
	private StatusPedido[] status;
	
	public Long getNumeroDe() {
		return numeroDe;
	}
	public void setNumeroDe(Long numeroDe) {
		this.numeroDe = numeroDe;
	}
	public Long getNumeroAte() {
		return numeroAte;
	}
	public void setNumeroAte(Long numeroAte) {
		this.numeroAte = numeroAte;
	}
	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}
	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}
	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}
	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}
	
	public Date getDataAprovacaoDe() {
		return dataAprovacaoDe;
	}
	public void setDataAprovacaoDe(Date dataAprovacaoDe) {
		this.dataAprovacaoDe = dataAprovacaoDe;
	}
	public Date getDataAprovacaoAte() {
		return dataAprovacaoAte;
	}
	public void setDataAprovacaoAte(Date dataAprovacaoAte) {
		this.dataAprovacaoAte = dataAprovacaoAte;
	}
	public String getNomeAprovador() {
		return nomeAprovador;
	}
	public void setNomeAprovador(String nomeAprovador) {
		this.nomeAprovador = nomeAprovador;
	}
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	public StatusPedido[] getStatus() {
		return status;
	}
	public void setStatus(StatusPedido[] status) {
		this.status = status;
	}
	
	
	
}
