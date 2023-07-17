package com.arg.carritodecompras.repository;

import com.arg.carritodecompras.model.Orden;
import com.arg.carritodecompras.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuario(Usuario usuario);

}
