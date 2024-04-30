package com.products.service;

import com.products.entidad.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    public List<Categoria> listarCategoria();

    public Categoria crearCategoria(Categoria categoria);

    public Optional<Categoria> buscarCateogiraPorID(Integer idCategoria);

}
