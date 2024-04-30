package com.products.repository;

import com.products.entidad.authEntidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario,Integer> {
    public Optional<Usuario> findByUsername(String username);



}
