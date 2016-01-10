package br.com.tcc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Categoria;
import br.com.tcc.model.TipoPaiol;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.service.CadastroArtefatoService;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroArtefatoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Artefato artefato;

	private Categoria categoriaPai;

	@Inject
	private CategoriaRepository categoriaRepository;

	@Inject
	private CadastroArtefatoService cadastroArtefatoService;

	private List<Categoria> categoriaRaizes;

	private List<Categoria> subcategorias;

	public CadastroArtefatoBean() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando....");

		if (FacesUtil.isNotPostback()) {
			categoriaRaizes = categoriaRepository.buscarCategoriaRaizes();
			if(this.categoriaPai != null){
				carregarSubcategorias();
			}
		}
		
	}

	public void carregarSubcategorias() {
		subcategorias = categoriaRepository.subcategoriaDe(categoriaPai);
	}

	private void limpar() {
		artefato = new Artefato();
		categoriaPai = null;
		subcategorias = new ArrayList<>();

	}

	public void salvar() {
		artefato = cadastroArtefatoService.salvar(artefato);

		FacesUtil.addInfoMessage("Artefato Salvo com sucesso");

	}

	public Artefato getArtefato() {
		return artefato;
	}

	public void setArtefato(Artefato artefato) {
		this.artefato = artefato;
		if (this.artefato != null) {
			this.categoriaPai = this.artefato.getCategoria().getCategoriaPai();
		}
	}

	public List<Categoria> getCategoriaRaizes() {
		return categoriaRaizes;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public TipoPaiol[] getTipoPaiol() {
		return TipoPaiol.values();
	}
	
	public boolean isEditando(){
		return this.artefato.getId() != null;
	}
}