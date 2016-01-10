package br.com.tcc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.tcc.model.Grupo;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.UsuarioRepository;
import br.com.tcc.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		
		UsuarioRepository usuarios = CDIServiceLocator.getBean(UsuarioRepository.class);
		Usuario usuario = usuarios.porEmail(email);
		UsuarioSistema user = null;
		
		if(usuario != null){
			user = new UsuarioSistema(usuario, getGrupo(usuario));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupo(Usuario usuario) {
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Grupo grupo = new Grupo();
		grupo = usuario.getGrupo();
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		
		return authorities;
	}
	
}
