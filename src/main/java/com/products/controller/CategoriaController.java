package com.products.controller;

import com.products.entidad.Categoria;

import com.products.service.CategoriaServiceIMP;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaServiceIMP categoriaServiceIMP;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarProductos() {
        List<Categoria> categoriaListado = categoriaServiceIMP.listarCategoria();
        return ResponseEntity.ok(categoriaListado);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Optional<Categoria>> buscarCategoriaPorId(@PathVariable Integer idCategoria) {
        Optional<Categoria> categoriaBuscado = categoriaServiceIMP.buscarCateogiraPorID(idCategoria);
        if (categoriaBuscado.isEmpty()) {
            ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(categoriaBuscado, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody @Valid Categoria categoria) {
        Categoria categoriaCreado = categoriaServiceIMP.crearCategoria(categoria);
        return new ResponseEntity<>(categoriaCreado, HttpStatus.CREATED);

    }

}
