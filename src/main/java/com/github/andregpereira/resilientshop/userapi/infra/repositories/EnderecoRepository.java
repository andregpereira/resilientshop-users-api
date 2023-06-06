package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
//    @Query(value = """
//            SELECT e.*
//            FROM tb_enderecos e
//            NATURAL JOIN tb_usuarios u
//            WHERE e.apelido ilike %:apelido%
//              AND u.id_usuario=:idUsuario
//              AND u.ativo=true
//            """, nativeQuery = true)
//    Optional<Endereco> findByApelidoAndUsuarioIdAndUsuarioAtivoTrue(@Param("apelido") String apelido,
//            @Param("idUsuario") Long idUsuario);

    Optional<Endereco> findByApelidoAndUsuarioId(String apelido, Long idUsuario);

    Page<Endereco> findAllByUsuarioId(Long idUsuario, Pageable pageable);

}
