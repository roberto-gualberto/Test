package br.com.tcc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Divisao;
import br.com.tcc.model.TipoPostoGraduacao;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.UsuarioRepository;
import br.com.tcc.repository.filter.ArtefatoFilter;
import br.com.tcc.repository.filter.UsuarioFilter;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPaiolBean implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;
	
	private UsuarioFilter filter;
	
	private Usuario usuarioSelecionado;
	
	private List<Usuario> UsuariosFiltrados;
	
	
	public PesquisaPaiolBean() {
		filter = new UsuarioFilter();
		
	}
	
	public void excluir(){
		usuarioRepository.remover(usuarioSelecionado);
		UsuariosFiltrados.remove(usuarioSelecionado);
		
		FacesUtil.addInfoMessage("Usuario "+usuarioSelecionado.getNome()+ " excluido com Sucesso!");
	}
	
	public void pesquisar(){
		UsuariosFiltrados = usuarioRepository.filtrados(filter);
	}

	public List<Usuario> getUsuariosFiltrados() {
		return UsuariosFiltrados;
	}

	public UsuarioFilter getFilter() {
		return filter;
	}
	
	public Usuario getUsuarioSelecionado(){
		return usuarioSelecionado;
	}
	
	public void setUsuarioSelecionado(Usuario usuarioSelecionado){
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
	public TipoPostoGraduacao[] getTipoPostoGraduacao() {
		return TipoPostoGraduacao.values();
	}
	
}
