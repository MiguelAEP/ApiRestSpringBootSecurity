package com.products.repository;

import com.products.entidad.authEntidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario,Integer> {
    public Optional<Usuario> findByUsername(String username);



}
