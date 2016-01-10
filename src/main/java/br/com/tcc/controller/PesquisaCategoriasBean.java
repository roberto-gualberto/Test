package br.com.tcc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tcc.model.Categoria;
import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.repository.filter.CategoriaFilter;
import br.com.tcc.repository.filter.PedidoFilter;
import br.com.tcc.repository.filter.UsuarioFilter;
import br.com.tcc.security.Seguranca;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCategoriasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Categoria> categoriaFiltrada;
	
	private CategoriaFilter filter;
	
	private Categoria categoriaSelecionada;

	@Inject
	private CategoriaRepository categoriaRepository;



	public PesquisaCategoriasBean() {
		
		filter = new CategoriaFilter();
	}

	public void pesquisar() {
		categoriaFiltrada = categoriaRepository.filtrados(filter);
	}
	

	public void excluir(){
		categoriaRepository.remover(categoriaSelecionada);
		categoriaFiltrada.remove(categoriaSelecionada);
		
		FacesUtil.addInfoMessage("Categoria "+categoriaSelecionada.getDescricao()+ " excluida com Sucesso!");
	}
	
	public List<Categoria> getCategoriaFiltrada() {
		return categoriaFiltrada;
	}
	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}
	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public CategoriaFilter getFilter() {
		return filter;
	}

	public void setFilter(CategoriaFilter filter) {
		this.filter = filter;
	}
	
	
	
	
}
