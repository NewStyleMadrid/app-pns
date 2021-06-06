package com.app.pns.dto;

public class Mensaje {

	/* Nos muestra mensaje de error en el cliente */

	private String mensaje;

	public Mensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
