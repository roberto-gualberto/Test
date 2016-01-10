package br.com.tcc.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {
	
	
	@Inject
	private ExternalContext externalContext;
	
	public String getNomeUsuario(){
	
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
			nome = usuarioLogado.getUsuario().getNome();
		}
		
		return nome;
	}
	
	public String getPostoUsuario(){
		
		String posto = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
			posto = usuarioLogado.getUsuario().getPosto().getDescricao();
		}
		
		return posto;
	}
	
	public Long getIdLogado(){
		Long id = null;
		UsuarioSistema usuarioId = getUsuarioLogado();
		
		if(usuarioId != null){
			id = usuarioId.getUsuario().getId();
		}
		return id;
	}

	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
		FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null){
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuario;
	}
	
	public boolean isAprovarPedidoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADOR");
	}
	public boolean isCancelarPedidoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADOR")||externalContext.isUserInRole("SOLICITANTE");
	}
	
	
}
