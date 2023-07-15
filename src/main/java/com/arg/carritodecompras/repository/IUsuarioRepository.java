package com.arg.carritodecompras.repository;

import com.arg.carritodecompras.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional <Usuario> findByEmail(String email);
}
