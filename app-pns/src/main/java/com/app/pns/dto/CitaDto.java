package com.app.pns.dto;

import java.util.Date;
import javax.validation.constraints.NotBlank;

import com.app.pns.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

public class CitaDto {

	@NotNull
	@NotBlank
	private String tipoServicio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha;


	private Usuario usuario;

	public CitaDto() {
		// Vacío
	}

	public CitaDto(@NotBlank String tipoServicio, Date fecha, Usuario usuario) {
		this.tipoServicio = tipoServicio;
		this.fecha = fecha;
		this.usuario = usuario;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
