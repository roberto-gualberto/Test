package br.com.tcc.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Categoria;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.util.jpa.Transactional;

public class CadastroCategoriaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoriaRepository categoriaRepository;
	
	@Transactional
	public Categoria salvar(Categoria categoria) {
		
		Categoria categoriaExistente = categoriaRepository.porDescricao(categoria.getDescricao());
		
		if (categoriaExistente != null && !categoriaExistente.equals(categoria)) {
			throw new NegocioException("Já existe uma Categoria com a descricao informada.");
		}
		
		return categoriaRepository.guardar(categoria);
	}
}
