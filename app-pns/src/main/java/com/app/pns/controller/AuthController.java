package com.app.pns.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.pns.dto.JwtDTO;
import com.app.pns.dto.LoginUsuario;
import com.app.pns.dto.Mensaje;
import com.app.pns.dto.NuevoUsuario;
import com.app.pns.entity.Rol;
import com.app.pns.entity.Usuario;
import com.app.pns.enums.RolNombre;
import com.app.pns.security.UsuarioPrincipal;
import com.app.pns.security.jwt.JwtProvider;
import com.app.pns.service.RolService;
import com.app.pns.service.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	JwtProvider jwtProvider;
	
	@GetMapping("/lista")
	public ResponseEntity<List<Usuario>> getLista() {
		List<Usuario> lista = usuarioService.lista();
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Usuario> getOne(@PathVariable int id) {
		if (!usuarioService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el usuario!"), HttpStatus.NOT_FOUND);
		Usuario usuario = usuarioService.obtenerPorId(id).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/detallePerfil/{userName}")
	public ResponseEntity<Usuario> detallePerfil(@PathVariable String userName) {
		if (!usuarioService.existePorUserName(userName))
			return new ResponseEntity(new Mensaje("No existe el usuario!"), HttpStatus.NOT_FOUND);
		Usuario usuario = usuarioService.obtenerPorUserName(userName).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/perfil/{userName}")
	public ResponseEntity<Usuario> perfil(@PathVariable String userName) {
		if (!usuarioService.existePorUserName(userName))
			return new ResponseEntity(new Mensaje("No existe el usuario!"), HttpStatus.NOT_FOUND);
		Usuario usuario = usuarioService.obtenerPorUserName(userName).get();
		
		//Email indicado la modificación del perfil
  		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("peluqueria.newstyle.madrid@gmail.com");
        mail.setTo(usuario.getEmail());
        mail.setSubject("Peluquería New Style Madrid");
        mail.setText("¡Tú perfil ha sido actualizado!"
          		+ "\n\nEstos son tus datos del perfil actualizado:"
          		+ "\nNombre y apellido:" + usuario.getNombre() +" "+ usuario.getApellidos()
          		+ "\nNombre de usuario: " + usuario.getUserName()
          		+ "\nEmail: " + usuario.getEmail()
          		+ "\nContraseña: " + usuario.getPassword()
          		+ "\n\nPuedes editar tus datos ingresando en nuestra página y recuerda que puedes contactarnos en peluqueria.newstyle.madrid@gmail.com"
          		+ "\n\nGRACIAS POR TU CONFIANZA");
          javaMailSender.send(mail);
          
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@RequestBody Usuario usuario, @PathVariable("id") int id) {
		if (!usuarioService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el usuario!"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(usuario.getNombre()))
			return new ResponseEntity(new Mensaje("Nombre obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(usuario.getApellidos()))
			return new ResponseEntity(new Mensaje("Apellidos obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(usuario.getUserName()))
			return new ResponseEntity(new Mensaje("Usuario obligatorio!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(usuario.getPassword()))
			return new ResponseEntity(new Mensaje("Contraseña obligatoria!"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(usuario.getEmail()))
			return new ResponseEntity(new Mensaje("Correo obligatorio!"), HttpStatus.BAD_REQUEST);
		if (usuarioService.existePorNombre(usuario.getUserName())
				&& usuarioService.getByUserName(usuario.getUserName()).get().getId() != id)
			return new ResponseEntity(new Mensaje("Usuario existente!"), HttpStatus.BAD_REQUEST);
		Usuario uUpdate = usuarioService.obtenerPorId(id).get();
		 uUpdate.setNombre(usuario.getNombre());
		 uUpdate.setApellidos(usuario.getApellidos());
		 uUpdate.setUserName(usuario.getUserName());
		 uUpdate.setPassword(passwordEncoder.encode(usuario.getPassword()));
		 uUpdate.setEmail(usuario.getEmail());
		 
		//Email, envio de datos actualizados
	  		SimpleMailMessage mail = new SimpleMailMessage();
	        mail.setFrom("peluqueria.newstyle.madrid@gmail.com");
	        mail.setTo(usuario.getEmail());
	        mail.setSubject("Peluquería New Style Madrid");
	        mail.setText("¡Tus datos han sido actualizados!"
	          		+ "\n\nEstos son tus datos actualizados:"
	          		+ "\nNombre y apellido:" + usuario.getNombre() +" "+ usuario.getApellidos()
	          		+ "\nNombre de usuario: " + usuario.getUserName()
	          		+ "\nEmail: " + usuario.getEmail()
	          		+ "\nContraseña: " + usuario.getPassword()
	          		+ "\n\nPuedes editar tus datos ingresando en nuestra página y recuerda que puedes contactarnos en peluqueria.newstyle.madrid@gmail.com"
	          		+ "\n\nGRACIAS POR TU CONFIANZA");
	          javaMailSender.send(mail);
		usuarioService.guardar(uUpdate);
		return new ResponseEntity(new Mensaje("Usuario actualizado!"), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/borrar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable int id) {
		if (!usuarioService.existePorId(id))
			return new ResponseEntity(new Mensaje("No existe el usuario!"), HttpStatus.NOT_FOUND);
		usuarioService.borrar(id);
		return new ResponseEntity(new Mensaje("Usuario eliminado de la BBDD."), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity(new Mensaje("campos vacíos o email inválido"), HttpStatus.BAD_REQUEST);
		if (usuarioService.existePorNombre(nuevoUsuario.getUserName()))
			return new ResponseEntity(new Mensaje("El usuario ya existe!"), HttpStatus.BAD_REQUEST);
		if (usuarioService.existePorEmail(nuevoUsuario.getEmail()))
			return new ResponseEntity(new Mensaje("El email ya existe!"), HttpStatus.BAD_REQUEST);
		Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellidos(), nuevoUsuario.getUserName(),
				nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
		Set<String> rolesStr = nuevoUsuario.getRoles();
		Set<Rol> roles = new HashSet<>();
		for (String rol : rolesStr) {
			switch (rol) {
			case "admin":
				Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get();
				roles.add(rolAdmin);
				break;
			default:
				Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
				roles.add(rolUser);
			}
		}
		usuario.setRoles(roles);
		usuarioService.guardar(usuario);
		
		//Email envio de datos de registro
  		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("peluqueria.newstyle.madrid@gmail.com");
        mail.setTo(nuevoUsuario.getEmail());
        mail.setSubject("Peluquería New Style Madrid");
        mail.setText("¡Te has registrado correctamente en New Style Madrid!"
          		+ "\n\nEstos son tus datos de registro:"
          		+ "\nNombre y apellido: " + nuevoUsuario.getNombre() +" "+ nuevoUsuario.getApellidos()
          		+ "\nNombre de usuario: " + nuevoUsuario.getUserName()
          		+ "\nEmail: " + nuevoUsuario.getEmail()
          		+ "\nContraseña: " + nuevoUsuario.getPassword()
          		+ "\n\nPuedes editar tus datos ingresando en nuestra página y recuerda que puedes contactarnos en peluqueria.newstyle.madrid@gmail.com"
          		+ "\n\nGRACIAS POR TU CONFIANZA");
          javaMailSender.send(mail);
		
		return new ResponseEntity(new Mensaje("Usuario registrado!"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/login")
	public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors())
			return new ResponseEntity(new Mensaje("Usuario o contraseña incorrectos"), HttpStatus.BAD_REQUEST);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUsuario.getUserName(), loginUsuario.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Devuelve objeto con nombre de usuario y roles.
		String jwt = jwtProvider.generateToken(authentication);
		
		UsuarioPrincipal usuario = (UsuarioPrincipal) authentication.getPrincipal();		
		
		JwtDTO jwtDTO = new JwtDTO(jwt, usuario.getId());
		return new ResponseEntity<JwtDTO>(jwtDTO, HttpStatus.OK);
		
	}
	
	/*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/refresh")
	public ResponseEntity<JwtDTO> refreshTkn(@RequestBody JwtDTO jwtDTO) throws ParseException{
		String tkn=jwtProvider.refreshTkn(jwtDTO);
		JwtDTO jwt=new JwtDTO(tkn);
		return new ResponseEntity(jwt, HttpStatus.OK);
	}
	*/
}