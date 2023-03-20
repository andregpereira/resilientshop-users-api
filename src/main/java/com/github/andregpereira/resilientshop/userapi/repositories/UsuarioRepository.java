package com.github.andregpereira.resilientshop.userapi.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByCpf(String cpf);

	@Query(value = "select * from tb_usuarios u where u.nome ilike %:nome% and u.sobrenome ilike %:sobrenome%", nativeQuery = true)
	Page<Usuario> findByNome(@Param("nome") String nome, @Param("sobrenome") String sobrenome, Pageable pageable);

}
