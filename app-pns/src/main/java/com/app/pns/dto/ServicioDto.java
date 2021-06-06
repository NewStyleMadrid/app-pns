package com.app.pns.dto;

public class ServicioDto {

	private String tipoServicio;

	private String precioMujer;

	private int precioHombre;

	private String precioNinio;

	private int precioAdulto;

	public ServicioDto() {

	}

	public ServicioDto(String tipoServicio, String precioMujer, int precioHombre, String precioNinio, int precioAdulto) {
		this.tipoServicio = tipoServicio;
		this.precioMujer = precioMujer;
		this.precioHombre = precioHombre;
		this.precioNinio = precioNinio;
		this.precioAdulto = precioAdulto;
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
