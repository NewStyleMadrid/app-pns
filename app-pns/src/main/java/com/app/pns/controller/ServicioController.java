package com.app.pns.controller;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.pns.dto.Mensaje;
import com.app.pns.entity.Servicio;
import com.app.pns.service.ServicioService;

@RestController
@RequestMapping("/servicio")
@CrossOrigin
public class ServicioController {

	@Autowired
	ServicioService servicioService;

	@GetMapping("/lista")
	public ResponseEntity<List<Servicio>> getLista() {
		List<Servicio> lista = servicioService.lista();
		return new ResponseEntity<List<Servicio>>(lista, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Servicio> getOne(@PathVariable int id) {
		if (!servicioService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el servicio!"), HttpStatus.NOT_FOUND);
		Servicio servicio = servicioService.obtenerPorId(id).get();
		return new ResponseEntity<Servicio>(servicio, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/nuevo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody Servicio servicio) {
		
		if (StringUtils.isBlank(servicio.getTipoServicio()))
			return new ResponseEntity(new Mensaje("Nombre de producto obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(servicio.getPrecioMujer()))
			return new ResponseEntity(new Mensaje("Precio mujer obligatorio!"), HttpStatus.BAD_REQUEST);
		if ((Integer) servicio.getPrecioHombre() == null || servicio.getPrecioHombre() == 0)
			return new ResponseEntity(new Mensaje("Precio hombre obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(servicio.getPrecioNinio()))
			return new ResponseEntity(new Mensaje("Precio niño obligatorio!"), HttpStatus.BAD_REQUEST);
		if ((Integer) servicio.getPrecioAdulto() == null || servicio.getPrecioAdulto() == 0)
			return new ResponseEntity(new Mensaje("Precio adulto obligatorio!"), HttpStatus.BAD_REQUEST);
		if (servicioService.existePorTipoServicio(servicio.getTipoServicio()))
			return new ResponseEntity(new Mensaje("Producto existente!"), HttpStatus.BAD_REQUEST);
		
		//Guardamos registro del nuevo servicio en la BBDD
		servicioService.guardar(servicio);
		
		return new ResponseEntity(new Mensaje("Producto guardado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody Servicio servicio, @PathVariable("id") int id) {
		
		if (!servicioService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el producto!"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(servicio.getTipoServicio()))
			return new ResponseEntity(new Mensaje("Nombre de producto obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(servicio.getPrecioMujer()))
			return new ResponseEntity(new Mensaje("Precio mujer obligatorio!"), HttpStatus.BAD_REQUEST);
		if ((Integer) servicio.getPrecioHombre() == null || servicio.getPrecioHombre() == 0)
			return new ResponseEntity(new Mensaje("Precio hombre obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(servicio.getPrecioNinio()))
			return new ResponseEntity(new Mensaje("Precio niño obligatorio!"), HttpStatus.BAD_REQUEST);
		if ((Integer) servicio.getPrecioAdulto() == null || servicio.getPrecioAdulto() == 0)
			return new ResponseEntity(new Mensaje("Precio adulto obligatorio!"), HttpStatus.BAD_REQUEST);
		if (servicioService.existePorTipoServicio(servicio.getTipoServicio())
				&& servicioService.obtenerPorTipoServicio(servicio.getTipoServicio()).get().getId() != id)
			return new ResponseEntity(new Mensaje("Servicio existente!"), HttpStatus.BAD_REQUEST);
		
		Servicio serviUpdate = servicioService.obtenerPorId(id).get();
		
		serviUpdate.setTipoServicio(servicio.getTipoServicio());
		serviUpdate.setPrecioMujer(servicio.getPrecioMujer());
		serviUpdate.setPrecioHombre(servicio.getPrecioHombre());
		serviUpdate.setPrecioNinio(servicio.getPrecioNinio());
		serviUpdate.setPrecioAdulto(servicio.getPrecioAdulto());
		
		//Guardamos datos actualizados.
		servicioService.guardar(serviUpdate);
		
		return new ResponseEntity(new Mensaje("Producto actualizado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/borrar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable int id) {
		if (!servicioService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el servicio!"), HttpStatus.NOT_FOUND);
		servicioService.borrar(id);
		return new ResponseEntity(new Mensaje("Servicio eliminado de la BBDD."), HttpStatus.OK);
	}
}
