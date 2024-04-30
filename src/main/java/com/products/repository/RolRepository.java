package com.products.repository;

import com.products.entidad.authEntidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,Integer> {
}
