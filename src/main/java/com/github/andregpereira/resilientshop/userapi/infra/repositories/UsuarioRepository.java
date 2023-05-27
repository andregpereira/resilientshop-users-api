package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
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

    Page<Usuario> findAllByAtivoTrue(Pageable pageable);

    @Query(value = """
            SELECT * FROM tb_usuarios u
            WHERE u.nome ilike %:nome% AND u.sobrenome ilike %:sobrenome% AND u.ativo=true
            """, nativeQuery = true)
    Page<Usuario> findAllByNomeAndSobrenomeAndAtivoTrue(@Param("nome") String nome,
            @Param("sobrenome") String sobrenome, Pageable pageable);

}
