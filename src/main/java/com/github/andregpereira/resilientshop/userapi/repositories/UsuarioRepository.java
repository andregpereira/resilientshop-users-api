package com.github.andregpereira.resilientshop.userapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Page<Usuario> findByCpf(String cpf, Pageable pageable);

	@Query(value = "select * from usuarios u where u.nome ilike %:nome% and u.sobrenome ilike %:sobrenome%", nativeQuery = true)
	Page<Usuario> findByName(@Param("nome") String nome, @Param("sobrenome") String sobrenome, Pageable pageable);

}
