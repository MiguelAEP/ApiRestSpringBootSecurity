package com.products.repository;

import com.products.entidad.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

    public Categoria findByNombreCategoria(String nombreCategoria);
}
