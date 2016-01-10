package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Artefato;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.util.jpa.Transactional;

public class CadastroArtefatoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArtefatoRepository artefatoRepository;
	
	@Transactional
	public Artefato salvar(Artefato artefato) {
		
		Artefato artefatoExistente = artefatoRepository.porCodigo(artefato.getCodigo());
		
		if (artefatoExistente != null && !artefatoExistente.equals(artefato)) {
			throw new NegocioException("Já existe um artefato com o CODIGO informado.");
		}
		artefato.setDataCadastro( new Date());
		return artefatoRepository.guardar(artefato);
	}
}
