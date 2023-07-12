package com.arg.carritodecompras.repository;

import com.arg.carritodecompras.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long> {
}
