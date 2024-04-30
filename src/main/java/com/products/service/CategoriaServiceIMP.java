package com.products.service;

import com.products.entidad.Categoria;
import com.products.exceptions.ResourceNotFound;
import com.products.repository.CategoriaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceIMP implements CategoriaService{

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public List<Categoria> listarCategoria() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> buscarCateogiraPorID(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    public Categoria buscarCategoriaPorNombre(String nombreCategoria){
        return categoriaRepository.findByNombreCategoria(nombreCategoria);
    }

}
