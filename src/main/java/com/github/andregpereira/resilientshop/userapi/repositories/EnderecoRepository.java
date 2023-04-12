package com.github.andregpereira.resilientshop.userapi.repositories;

import com.github.andregpereira.resilientshop.userapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
