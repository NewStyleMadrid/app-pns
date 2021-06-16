package com.app.pns.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.app.pns.entity.Cita;
import com.app.pns.service.CitaService;
import com.app.pns.service.UsuarioService;

@RestController
@RequestMapping("/cita")
@CrossOrigin
public class CitaController {

	@Autowired
	CitaService citaService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	JavaMailSender javaMailSender;

	@GetMapping("/lista")
	public ResponseEntity<List<Cita>> getLista() {
		List<Cita> lista = citaService.lista();
		return new ResponseEntity<List<Cita>>(lista, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Cita> getOne(@PathVariable int id) {
		if (!citaService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe la cita!"), HttpStatus.NOT_FOUND);
		Cita cita = citaService.obtenerPorId(id).get();
		return new ResponseEntity<Cita>(cita, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/nueva")
	public ResponseEntity<?> create(@RequestBody Cita cita) {
		if (StringUtils.isBlank(cita.getTipoServicio()))
			return new ResponseEntity(new Mensaje("Servicio obligatorio!"), HttpStatus.BAD_REQUEST);
		if (cita.getFecha() == null)
			return new ResponseEntity(new Mensaje("Fecha obligatoria!"), HttpStatus.BAD_REQUEST);
		if (citaService.existePorHora(cita.getHora()))
			return new ResponseEntity(new Mensaje("Hora no disponible!"), HttpStatus.BAD_REQUEST);

		// Email
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("peluqueria.newstyle.madrid@gmail.com");
		mail.setTo(cita.getUsuario().getEmail());
		mail.setSubject("Peluquería New Style Madrid");
		mail.setText("¡Cita realizada en New Style Madrid!" + "\n\nEstos son tus datos de cita:" + "\nServicio: "
				+ cita.getTipoServicio() + "\nHora: " + cita.getHora() + "\nFecha: " + cita.getFecha()
				+ "\n\nPuedes cancelar tus cita ingresando en nuestra página y recuerda que puedes contactarnos en peluqueria.newstyle.madrid@gmail.com"
				+ "\n\nGRACIAS POR TU CONFIANZA");
		javaMailSender.send(mail);
		citaService.guardar(cita);
		return new ResponseEntity(new Mensaje("Cita guardada!"), HttpStatus.CREATED);

	}

	// Falta meter bien los datos //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@RequestBody Cita cita, @PathVariable("id") int id) {
		if (!citaService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe la cita!"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(cita.getTipoServicio()))
			return new ResponseEntity(new Mensaje("Servicio obligatorio!"), HttpStatus.BAD_REQUEST);
		if (cita.getHora() == null)
			return new ResponseEntity(new Mensaje("Hora obligatoria!"), HttpStatus.BAD_REQUEST);
		if (cita.getFecha() == null)
			return new ResponseEntity(new Mensaje("Fecha obligatoria!"), HttpStatus.BAD_REQUEST);
		if (citaService.existePorId(cita.getId()) && citaService.obtenerPorId(cita.getId()).get().getId() != id)
			return new ResponseEntity(new Mensaje("Cita existente!"), HttpStatus.BAD_REQUEST);
		Cita citaUpdate = citaService.obtenerPorId(id).get();
		citaUpdate.setTipoServicio(cita.getTipoServicio());
		citaUpdate.setFecha(cita.getFecha());
		citaUpdate.setHora(cita.getHora());
		citaService.guardar(citaUpdate);
		return new ResponseEntity(new Mensaje("Cita actualizado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		if (!citaService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe la cita!"), HttpStatus.NOT_FOUND);
		citaService.borrar(id);
		return new ResponseEntity(new Mensaje("Cita eliminada de la BBDD."), HttpStatus.OK);
	}

}
