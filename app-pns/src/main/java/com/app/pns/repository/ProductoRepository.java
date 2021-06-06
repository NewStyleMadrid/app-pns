package com.app.pns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pns.entity.Producto;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	 List<Producto> findByOrderById();
	  
	 Optional<Producto> findByNombre(String nombre);
	
	 boolean existsByNombre(String nombre);
}