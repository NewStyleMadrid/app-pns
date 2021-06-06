package com.app.pns.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.app.pns.dto.Mensaje;
import com.app.pns.entity.Corte;
import com.app.pns.service.CorteService;

@RestController
@RequestMapping("/corte")
@CrossOrigin
public class CorteController {

	@Autowired
	CorteService corteService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/lista")
	public ResponseEntity<List<Corte>> list() {
		List<Corte> list = corteService.lista();
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/upload")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
		BufferedImage bufferImg = ImageIO.read(multipartFile.getInputStream());
		if (bufferImg == null) {
			return new ResponseEntity(new Mensaje("Imagen no válida!"), HttpStatus.BAD_REQUEST);
		}
		Map resultado = corteService.upload(multipartFile);
		Corte corte = new Corte((String) resultado.get("original_filename"), (String) resultado.get("url"),
				(String) resultado.get("public_id"));
		corteService.guardar(corte);
		return new ResponseEntity(new Mensaje("Corte subida!"), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Corte> getOne(@PathVariable int id) {
		if (!corteService.exists(id))
			return new ResponseEntity(new Mensaje("No existe el diseño!"), HttpStatus.NOT_FOUND);
		Corte corte = corteService.obtenerPorId(id).get();
		return new ResponseEntity<Corte>(corte, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody Corte corte, @PathVariable("id") int id) {
		if (!corteService.exists(id))
			return new ResponseEntity(new Mensaje("No existe el diseño!"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(corte.getName()))
			return new ResponseEntity(new Mensaje("Nombre obligatorio!"), HttpStatus.BAD_REQUEST);
		if (corteService.existePorNombre(corte.getName())
				&& corteService.obtenerPorNombre(corte.getName()).get().getId() != id)
			return new ResponseEntity(new Mensaje("Producto existente!"), HttpStatus.BAD_REQUEST);
		Corte imgUpdate = corteService.obtenerPorId(id).get();
		imgUpdate.setName(corte.getName());
		corteService.guardar(imgUpdate);
		return new ResponseEntity(new Mensaje("Producto actualizado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		if (!corteService.exists(id))
			return new ResponseEntity(new Mensaje("No existe!"), HttpStatus.NOT_FOUND);
		Corte corte = corteService.getOne(id).get();
		@SuppressWarnings("unused")
		Map resultado = corteService.delete(corte.getImagenId());
		corteService.borrar(id);
		return new ResponseEntity(new Mensaje("Corte eliminada de la BBDD"), HttpStatus.OK);
	}
}
