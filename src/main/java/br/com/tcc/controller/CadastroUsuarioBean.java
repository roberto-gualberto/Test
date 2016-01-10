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
import br.com.tcc.service.CadastroUsuarioService;
import br.com.tcc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	private Grupo grupo;

	@Inject
	private GrupoRepository grupoRepository;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	private List<Grupo> grupos;

	public CadastroUsuarioBean() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando....");

		if (FacesUtil.isNotPostback()) {
			this.grupos = this.grupoRepository.buscarGrupos();

		}

	}

	private void limpar() {
		usuario = new Usuario();
		grupos = null;
		grupo = null;

	}

	public void salvar() {
		usuario = cadastroUsuarioService.salvar(usuario);

		FacesUtil.addInfoMessage("Usuario Salvo com sucesso");

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		if (this.usuario != null) {
			this.grupo = this.usuario.getGrupo();

		}
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	@NotNull
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public TipoPostoGraduacao[] getTipoPostoGraduacao() {
		return TipoPostoGraduacao.values();
	}

	public Sexo[] getSexo() {
		return Sexo.values();
	}

	public Divisao[] getDivisao() {
		return Divisao.values();
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

}