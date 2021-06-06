package com.app.pns.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.app.pns.entity.Usuario;
import com.app.pns.security.UsuarioPrincipal;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
	
	  @Autowired
	   UsuarioService usuarioService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		 Usuario usuario = usuarioService.getByUserName(userName).get();
	        return UsuarioPrincipal.build(usuario);
	}
}
