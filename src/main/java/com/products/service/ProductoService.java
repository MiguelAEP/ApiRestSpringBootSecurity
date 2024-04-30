package com.products.service;

import com.products.entidad.Categoria;
import com.products.entidad.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService{

    public List<Producto> listarProductos();

    public Producto crearProducto(Producto producto);

    public Optional<Producto> buscarProductoPorID(Integer idProducto);

    public void  eliminarProducto(Integer idProducto );

    public Producto actualizarProducto(Producto producto , Integer idProducto);

    public List<Producto> listarProductosPorNombreCaterogia(String nombreCategoria);
}
