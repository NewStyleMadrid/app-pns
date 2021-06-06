package com.app.pns.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.pns.entity.Rol;
import com.app.pns.enums.RolNombre;
import com.app.pns.repository.RolRepository;

@Service
@Transactional
public class RolService {

	@Autowired
	RolRepository rolRepository;

	public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
		return rolRepository.findByRolNombre(rolNombre);
	}

	// Crear Relaciones de ROL
	 public void save(Rol rol){
	        rolRepository.save(rol);
	  }
}
