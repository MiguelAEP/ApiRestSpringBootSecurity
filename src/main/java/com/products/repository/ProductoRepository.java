package com.products.repository;

import com.products.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    public List<Producto> findByCategoriaNombreCategoria(String nombreCategoria);

}
