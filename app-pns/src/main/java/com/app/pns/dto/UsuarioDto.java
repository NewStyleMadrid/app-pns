package com.app.pns.dto;

import javax.validation.constraints.NotNull;

public class UsuarioDto {
	
	@NotNull
	private String nombre;

	@NotNull
	private String apellidos;

	@NotNull
	private String userName;

	@NotNull
	private String email;

}
