package com.github.andregpereira.resilientshop.userapi.repositories;

import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);

    Optional<Usuario> findByIdAndAtivoTrue(Long id);

    Optional<Usuario> findByIdAndAtivoFalse(Long id);

    Optional<Usuario> findByCpfAndAtivoTrue(String cpf);

    @Query(value = """
            select * from tb_usuarios u
            where u.nome ilike %:nome% and u.sobrenome ilike %:sobrenome% and u.ativo=true
            """, nativeQuery = true)
    Page<Usuario> findByNomeAndAtivoTrue(@Param("nome") String nome, @Param("sobrenome") String sobrenome,
            Pageable pageable);

    Page<Usuario> findByAtivoTrue(Pageable pageable);

}
