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
import com.app.pns.entity.Producto;
import com.app.pns.service.ProductoService;

@RestController
@RequestMapping("/producto")
@CrossOrigin
public class ProductoController {

	@Autowired
	ProductoService productoService;

	@GetMapping("/lista")
	public ResponseEntity<List<Producto>> getLista() {
		List<Producto> lista = productoService.lista();
		return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Producto> getOne(@PathVariable int id) {
		if (!productoService.existePorId(id))
			return new ResponseEntity(new Mensaje("no existe ese producto"), HttpStatus.NOT_FOUND);
		Producto producto = productoService.obtenerPorId(id).get();
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/nuevo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody Producto producto) {
		if (StringUtils.isBlank(producto.getNombre()))
			return new ResponseEntity(new Mensaje("Nombre de producto obligatorio!"), HttpStatus.BAD_REQUEST);
		if ((Integer) producto.getPrecio() == null || producto.getPrecio() == 0)
			return new ResponseEntity(new Mensaje("Precio obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(producto.getDisponible()))
			return new ResponseEntity(new Mensaje("Disponibilidad obligatoria!"), HttpStatus.BAD_REQUEST);
		if (productoService.existePorNombre(producto.getNombre()))
			return new ResponseEntity(new Mensaje("Producto existente!"), HttpStatus.BAD_REQUEST);
		productoService.guardar(producto);
		return new ResponseEntity(new Mensaje("Producto guardado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable("id") int id) {
		if (!productoService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el producto!"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(producto.getNombre()))
			return new ResponseEntity(new Mensaje("Nombre de producto obligatorio!"), HttpStatus.BAD_REQUEST);
		if ((Integer) producto.getPrecio() == null || producto.getPrecio() == 0)
			return new ResponseEntity(new Mensaje("Precio obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(producto.getDisponible()))
			return new ResponseEntity(new Mensaje("Disponibilidad obligatoria!"), HttpStatus.BAD_REQUEST);
		if (productoService.existePorNombre(producto.getNombre())
				&& productoService.obtenerPorNombre(producto.getNombre()).get().getId() != id)
			return new ResponseEntity(new Mensaje("Producto existente!"), HttpStatus.BAD_REQUEST);
		Producto prodUpdate = productoService.obtenerPorId(id).get();
		prodUpdate.setNombre(producto.getNombre());
		prodUpdate.setPrecio(producto.getPrecio());
		prodUpdate.setDisponible(producto.getDisponible());
		productoService.guardar(prodUpdate);
		return new ResponseEntity(new Mensaje("Producto actualizado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/borrar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable int id) {
		if (!productoService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el producto!"), HttpStatus.NOT_FOUND);
		productoService.borrar(id);
		return new ResponseEntity(new Mensaje("Producto eliminado de la BBDD."), HttpStatus.OK);
	}

	/*
	 * Paginación
	 * 
	 * @GetMapping("/productos") public ResponseEntity<Page<Producto>> paginas(
	 * 
	 * @RequestParam(defaultValue = "0") int page,
	 * 
	 * @RequestParam(defaultValue = "8") int size,
	 * 
	 * @RequestParam(defaultValue = "nombre") String order,
	 * 
	 * @RequestParam(defaultValue = "true") boolean asc ){ Page<Producto> productos
	 * = productoService.paginas( PageRequest.of(page, size, Sort.by(order)));
	 * if(!asc) productos = productoService.paginas( PageRequest.of(page, size,
	 * Sort.by(order).descending())); return new
	 * ResponseEntity<Page<Producto>>(productos, HttpStatus.OK); }
	 */
}
