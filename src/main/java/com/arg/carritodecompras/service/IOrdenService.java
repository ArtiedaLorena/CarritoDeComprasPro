package com.arg.carritodecompras.service;

import com.arg.carritodecompras.model.Orden;

import java.util.List;

public interface IOrdenService {
    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();

}
