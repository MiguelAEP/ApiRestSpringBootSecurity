package com.products.repository;

import com.products.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    public List<Producto> findByCategoriaNombreCategoria(String nombreCategoria);

}
