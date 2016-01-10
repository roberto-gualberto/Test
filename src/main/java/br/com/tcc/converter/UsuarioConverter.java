package br.com.tcc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tcc.model.Usuario;
import br.com.tcc.repository.UsuarioRepository;
import br.com.tcc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	// @Inject nao funciona em conversores :(
	private UsuarioRepository usuarioRepository;

	public UsuarioConverter() {
		usuarioRepository = CDIServiceLocator
				.getBean(UsuarioRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Usuario retorno = null;

		if (value != null) {
			Long id = new Long(value);

			retorno = usuarioRepository.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}
		return "";
		
	}

}
