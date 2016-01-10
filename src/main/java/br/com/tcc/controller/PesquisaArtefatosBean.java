package br.com.tcc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tcc.model.Artefato;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.filter.ArtefatoFilter;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaArtefatosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArtefatoRepository artefatoRepository;
	
	private ArtefatoFilter filter;
	
	private Artefato artefatoSelecionado;
	
	private List<Artefato> artefatosFiltrados;
	
	public PesquisaArtefatosBean() {
		filter = new ArtefatoFilter();
	}
	
	public void excluir(){
		artefatoRepository.remover(artefatoSelecionado);
		artefatosFiltrados.remove(artefatoSelecionado);
		
		FacesUtil.addInfoMessage("Artefato"+artefatoSelecionado.getCodigo()+ " excluido com Sucesso!");
	}
	
	public void pesquisar(){
		artefatosFiltrados = artefatoRepository.filtrados(filter);
	}

	public List<Artefato> getArtefatosFiltrados() {
		return artefatosFiltrados;
	}

	public ArtefatoFilter getFilter() {
		return filter;
	}
	
	public Artefato getArtefatoSelecionado(){
		return artefatoSelecionado;
	}
	
	public void setArtefatoSelecionado(Artefato artefatoSelecionado){
		this.artefatoSelecionado = artefatoSelecionado;
	}
	
	
	
}
