package com.products.service;

import com.products.entidad.Categoria;
import com.products.entidad.Producto;
import com.products.exceptions.ResourceNotFound;
import com.products.repository.CategoriaRepository;
import com.products.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceIMP implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto crearProducto(Producto producto ) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarProductoPorID(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Override
    public void eliminarProducto(Integer idProducto) {
        productoRepository.deleteById(idProducto);
    }

    @Override
    public Producto actualizarProducto(Producto producto , Integer idProducto) {
        System.out.println("producto" + producto );
        Producto productoExiste = productoRepository.findById(idProducto).orElseThrow(()->new ResourceNotFound("productoExiste","producto",idProducto));
        System.out.println("productoExiste" + productoExiste );

        Producto productoGuardar = new Producto();

        productoGuardar.setId(productoExiste.getId());
        productoGuardar.setNombreProducto(producto.getNombreProducto());
        productoGuardar.setPrecio(producto.getPrecio());
        productoGuardar.setCantidad(producto.getCantidad());
        productoGuardar.setCategoria(productoExiste.getCategoria());
        System.out.println("productoGuardar" + productoGuardar );
        return productoRepository.save(productoGuardar);
    }

    @Override
    public List<Producto> listarProductosPorNombreCaterogia(String nombreCategoria) {
        return productoRepository.findByCategoriaNombreCategoria(nombreCategoria);
    }


}
