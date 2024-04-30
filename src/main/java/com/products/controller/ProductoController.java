package com.products.controller;

import com.products.entidad.Categoria;
import com.products.entidad.Producto;
import com.products.service.CategoriaServiceIMP;
import com.products.service.ProductoServiceIMP;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoServiceIMP productoServiceIMP;

    @Autowired
    private CategoriaServiceIMP categoriaServiceIMP;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productosListado = productoServiceIMP.listarProductos();
        System.out.println("lista" + productosListado);
        return ResponseEntity.ok(productosListado);
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<Optional<Producto>> buscarProductoPorId(@PathVariable Integer idProducto) {
        Optional<Producto> productoBuscado = productoServiceIMP.buscarProductoPorID(idProducto);
        if (!productoBuscado.isPresent()) {
            ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(productoBuscado, HttpStatus.OK);
    }


    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<Producto>> buscarProductoPorNombreCategoria(@PathVariable String nombreCategoria) {

        Categoria categoriaBuscado = categoriaServiceIMP.buscarCategoriaPorNombre(nombreCategoria);
        if (categoriaBuscado == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        List<Producto> listaProductoPorNombreCategoria = productoServiceIMP.listarProductosPorNombreCaterogia(nombreCategoria);
        System.out.println("listaProductoPorNombreCategoria controller" + listaProductoPorNombreCategoria);
        return ResponseEntity.ok(listaProductoPorNombreCategoria);

    }


    @PostMapping("/crear/categoriaID/{idCategoria}")
    public ResponseEntity<Producto> crearProducto(@RequestBody @Valid Producto producto, @PathVariable Integer idCategoria) {
        Optional<Categoria> categoriaBuscado = categoriaServiceIMP.buscarCateogiraPorID(idCategoria);
        System.out.println("categoriaBuscado" + categoriaBuscado);
        if (!categoriaBuscado.isPresent()) return ResponseEntity.unprocessableEntity().build();

        Producto productoCreado = new Producto();
        productoCreado.setCategoria(categoriaBuscado.get());
        productoCreado.setNombreProducto(producto.getNombreProducto());
        productoCreado.setCantidad(producto.getCantidad());
        productoCreado.setPrecio(producto.getPrecio());

        Producto prod = productoServiceIMP.crearProducto(productoCreado);
        return new ResponseEntity<>(prod, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{idProducto}")
    public ResponseEntity<Producto> borrarProducto(@PathVariable Integer idProducto) {
        productoServiceIMP.eliminarProducto(idProducto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody @Valid Producto producto, @PathVariable Integer idProducto) {
        Producto productoGuardar = productoServiceIMP.actualizarProducto(producto, idProducto);
        return ResponseEntity.ok(productoGuardar);
    }


}
