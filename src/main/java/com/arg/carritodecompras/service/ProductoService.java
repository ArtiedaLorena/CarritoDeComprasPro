package com.arg.carritodecompras.service;

import com.arg.carritodecompras.model.Producto;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ProductoService {
    public Producto save(Producto producto);
    public Optional<Producto> get(Long id);
    public void update(Producto producto);
    public void delete(Long id);
}
