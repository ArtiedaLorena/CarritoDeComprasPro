package com.arg.carritodecompras.service;

import com.arg.carritodecompras.model.Producto;
import com.arg.carritodecompras.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{
    //Implementacion de los metodos inyectamos a la clase un objeto a traves del autowired
    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);

    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

}
