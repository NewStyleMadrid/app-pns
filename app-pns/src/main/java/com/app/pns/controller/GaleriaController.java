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
import com.app.pns.entity.GaleriaHome;
import com.app.pns.service.GaleriaService;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class GaleriaController {

	@Autowired
	GaleriaService galeriaService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/lista")
	public ResponseEntity<List<GaleriaHome>> list() {
		List<GaleriaHome> list = galeriaService.lista();
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
		BufferedImage bufferImg = ImageIO.read(multipartFile.getInputStream());
		if (bufferImg == null) {
			return new ResponseEntity(new Mensaje("Galeria no válida!"), HttpStatus.BAD_REQUEST);
		}
		Map resultado = galeriaService.upload(multipartFile);
		GaleriaHome galeria = new GaleriaHome((String) resultado.get("original_filename"), (String) resultado.get("url"),
				(String) resultado.get("public_id"));
		galeriaService.guardar(galeria);
		return new ResponseEntity(new Mensaje("Galeria subida!"), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<GaleriaHome> getOne(@PathVariable int id) {
		if (!galeriaService.exists(id))
			return new ResponseEntity(new Mensaje("No existe el diseño!"), HttpStatus.NOT_FOUND);
		GaleriaHome galeria = galeriaService.obtenerPorId(id).get();
		return new ResponseEntity<GaleriaHome>(galeria, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody GaleriaHome galeriaHome, @PathVariable("id") int id) {
		if (!galeriaService.exists(id))
			return new ResponseEntity(new Mensaje("No existe el diseño!"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(galeriaHome.getName()))
			return new ResponseEntity(new Mensaje("Nombre obligatorio!"), HttpStatus.BAD_REQUEST);
		if (galeriaService.existePorNombre(galeriaHome.getName())
				&& galeriaService.obtenerPorNombre(galeriaHome.getName()).get().getId() != id)
			return new ResponseEntity(new Mensaje("Producto existente!"), HttpStatus.BAD_REQUEST);
		GaleriaHome imgUpdate = galeriaService.obtenerPorId(id).get();
		imgUpdate.setName(galeriaHome.getName());
		galeriaService.guardar(imgUpdate);
		return new ResponseEntity(new Mensaje("Producto actualizado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		if (!galeriaService.exists(id))
			return new ResponseEntity(new Mensaje("No existe!"), HttpStatus.NOT_FOUND);
		GaleriaHome galeria = galeriaService.getOne(id).get();
		@SuppressWarnings("unused")
		Map resultado = galeriaService.delete(galeria.getImagenId());
		galeriaService.borrar(id);
		return new ResponseEntity(new Mensaje("GaleriaHome eliminada de la BBDD"), HttpStatus.OK);
	}
}
