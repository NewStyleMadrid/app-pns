package com.app.pns.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.app.pns.entity.Usuario;

public class UsuarioPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String apellidos;
	private String userName;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal(int id, String nombre, String apellidos, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	/*
	 * Se crea una lista de privilegios (GrantedAutoriry) y se le asignan en el
	 * constructor al UsuarioPrincipal que devuelve el método.
	 */
	
	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
		return new UsuarioPrincipal(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getUserName(),
				usuario.getEmail(), usuario.getPassword(), authorities);
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}


	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
