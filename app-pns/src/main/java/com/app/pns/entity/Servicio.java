package com.app.pns.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Servicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String tipoServicio;

	@NotNull
	private String precioMujer;

	private int precioHombre;

	@NotNull
	private String precioNinio;

	private int precioAdulto;

	public Servicio() {

	}

	public Servicio(String tipoServicio, String precioMujer, int precioHombre, String precioNinio, int precioAdulto) {
		this.tipoServicio = tipoServicio;
		this.precioMujer = precioMujer;
		this.precioHombre = precioHombre;
		this.precioNinio = precioNinio;
		this.precioAdulto = precioAdulto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getPrecioMujer() {
		return precioMujer;
	}

	public void setPrecioMujer(String precioMujer) {
		this.precioMujer = precioMujer;
	}

	public int getPrecioHombre() {
		return precioHombre;
	}

	public void setPrecioHombre(int precioHombre) {
		this.precioHombre = precioHombre;
	}

	public String getPrecioNinio() {
		return precioNinio;
	}

	public void setPrecioNinio(String precioNinio) {
		this.precioNinio = precioNinio;
	}

	public int getPrecioAdulto() {
		return precioAdulto;
	}

	public void setPrecioAdulto(int precioAdulto) {
		this.precioAdulto = precioAdulto;
	}
}
