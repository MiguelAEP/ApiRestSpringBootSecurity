package com.products.repository;

import com.products.entidad.authEntidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
    public List<Rol> findByRoleEnumIn(List<String> roleName);
   // findRoleEntitiesByRoleEnumIn
}
