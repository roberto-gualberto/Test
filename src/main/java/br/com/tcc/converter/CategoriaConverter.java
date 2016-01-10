package br.com.tcc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tcc.model.Categoria;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.CategoriaRepository;
import br.com.tcc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter{

	//@Inject nao funciona em conversores :(
	private CategoriaRepository categoriaRepository;
	
	
	public CategoriaConverter() {
		categoriaRepository = CDIServiceLocator.getBean(CategoriaRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		Categoria retorno = null;
		
		if(value != null){
			Long id = new Long(value);
			
			retorno = categoriaRepository.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value != null){
			Categoria categoria = (Categoria) value;
			return categoria.getId() == null ? null : categoria.getId().toString();
		}
		return "";
	}

}
