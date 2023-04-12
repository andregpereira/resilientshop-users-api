package com.github.andregpereira.resilientshop.userapi.repositories;

import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

    Optional<Pais> findByNomeOrCodigo(String nome, String codigo);

}
