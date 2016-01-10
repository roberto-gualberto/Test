package br.com.tcc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Categoria;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Artefato.class)
public class ArtefatoConverter implements Converter {

	// @Inject nao funciona em conversores :(
	private ArtefatoRepository artefatoRepository;

	public ArtefatoConverter() {
		artefatoRepository = CDIServiceLocator
				.getBean(ArtefatoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Artefato retorno = null;

		if (value != null) {
			Long id = new Long(value);

			retorno = artefatoRepository.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Artefato artefato = (Artefato) value;
			return artefato.getId() == null ? null : artefato.getId().toString();
		}
		return "";
		
	}

}
