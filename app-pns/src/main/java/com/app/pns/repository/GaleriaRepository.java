package com.app.pns.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pns.entity.GaleriaHome;

@Repository
public interface GaleriaRepository extends JpaRepository<GaleriaHome, Integer> {
	   List<GaleriaHome> findByOrderById();
	   
	   Optional<GaleriaHome> findByName(String name);
		
	   boolean existsByName(String name);
}