package com.products.repository;

import com.products.entidad.authEntidades.Permisions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permisions,Integer> {
}
