package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.UsuarioRepository;
import br.com.tcc.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		Usuario usuarioExistente = usuarioRepository.porIdentidade(usuario.getIdentidade());
		
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um Usuario com a Identidade informada.");
		}
		usuario.setDataCadastro( new Date());
		return usuarioRepository.guardar(usuario);
	}
}
