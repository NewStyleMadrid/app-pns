package com.app.pns.util;

/**
 * ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS, COMENTAMOS CODIGO PARA UN FUTURO USO.
 *
 */

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.app.pns.entity.Rol;
import com.app.pns.enums.RolNombre;
import com.app.pns.service.RolService;

@Component
public class CrearRol implements CommandLineRunner {
	
	@Autowired
	RolService rolService;
	@Override
	public void run(String... args) throws Exception {
		
		 Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN); Rol rolUser = new
		 Rol(RolNombre.ROLE_USER); rolService.save(rolAdmin);
		 rolService.save(rolUser);	
	}

}
	*/