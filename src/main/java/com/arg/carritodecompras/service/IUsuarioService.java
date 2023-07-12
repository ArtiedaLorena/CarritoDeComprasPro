package com.arg.carritodecompras.service;

import com.arg.carritodecompras.model.Usuario;


import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Long id);
}
