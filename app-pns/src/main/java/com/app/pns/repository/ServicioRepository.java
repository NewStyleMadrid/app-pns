package com.app.pns.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pns.entity.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
	
	 List<Servicio> findByOrderById();
	  
	 Optional<Servicio> findByTipoServicio(String tipoServicio);
	
	 boolean existsByTipoServicio(String tipoServicio);
}
