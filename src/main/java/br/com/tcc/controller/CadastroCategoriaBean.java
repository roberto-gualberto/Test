package br.com.tcc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Categoria;
import br.com.tcc.model.Divisao;
import br.com.tcc.model.Grupo;
import br.com.tcc.model.Sexo;
import br.com.tcc.model.TipoPaiol;
import br.com.tcc.model.TipoPostoGraduacao;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.repository.GrupoRepository;
import br.com.tcc.service.CadastroArtefatoService;
import br.com.tcc.service.CadastroCategoriaService;
import br.com.tcc.service.CadastroUsuarioService;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Categoria categoria;

	@Inject
	private CategoriaRepository categoriaRepository;

	@Inject
	private CadastroCategoriaService cadastroCategoriaService;

	private List<Categoria> categorias;

	public CadastroCategoriaBean() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando....");

		if (FacesUtil.isNotPostback()) {
			this.categorias = this.categoriaRepository.buscarCategorias();

		}

	}

	private void limpar() {
		categoria = new Categoria();
		categorias = null;

	}

	public void salvar() {
		categoria = cadastroCategoriaService.salvar(categoria);

		FacesUtil.addInfoMessage("Categoria Salva com sucesso");

	}
	

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		// if (this.categoria != null) {
		// this.categoria = this.categoria;

		// }
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public boolean isEditando() {
		return this.categoria.getId()!=null;
	}

}