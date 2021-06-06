package com.app.pns.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pns.entity.Cita;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
	
	List<Cita> findByOrderById();
	
	Optional<Cita> findByUsuarioId(String userId);
	
	boolean existsByHora(Integer hora);
	
	boolean existsByFecha(Date fecha);
	
	/*
	Optional<Cita> findByFecha(Date fecha);
		
	boolean existsByFecha(Date fecha);
	
	Optional<Cita> findByHora(Time hora);
	
	boolean existsByHora(Time hora);
	*/
}
