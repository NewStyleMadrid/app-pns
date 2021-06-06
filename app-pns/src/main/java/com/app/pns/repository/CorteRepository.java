package com.app.pns.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pns.entity.Corte;

@Repository
public interface CorteRepository extends JpaRepository<Corte, Integer> {
	   List<Corte> findByOrderById();
	   
	   Optional<Corte> findByName(String name);
		
	   boolean existsByName(String name);
}