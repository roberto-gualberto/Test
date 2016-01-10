package br.com.tcc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tcc.model.Artefato;
import br.com.tcc.model.Categoria;
import br.com.tcc.model.Grupo;
import br.com.tcc.repository.ArtefatoRepository;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.repository.GrupoRepository;
import br.com.tcc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

	// @Inject nao funciona em conversores :(
	private GrupoRepository grupoRepository;

	public GrupoConverter() {
		grupoRepository = CDIServiceLocator
				.getBean(GrupoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Grupo retorno = null;

		if (value != null) {
			Long id = new Long(value);

			retorno = grupoRepository.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Grupo grupo = (Grupo) value;
			return grupo.getId() == null ? null : grupo.getId().toString();
		}
		return "";
		
	}

}
