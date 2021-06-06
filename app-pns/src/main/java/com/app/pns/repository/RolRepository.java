package com.app.pns.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pns.entity.Rol;
import com.app.pns.enums.RolNombre;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

	 Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
