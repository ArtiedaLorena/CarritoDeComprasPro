package com.arg.carritodecompras.service;

import com.arg.carritodecompras.model.Orden;
import com.arg.carritodecompras.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);

    Optional<Orden> findById(Long id);
}
