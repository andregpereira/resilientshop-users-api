package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query(value = """
            SELECT e.*
            FROM tb_enderecos e
            NATURAL JOIN tb_usuarios u
            WHERE e.apelido ilike %:apelido%
              AND u.id_usuario=:idUsuario
              AND u.ativo=true
            """, nativeQuery = true)
    Optional<Endereco> findByApelidoAndUsuarioIdAndUsuarioAtivoTrue(@Param("apelido") String apelido,
            @Param("idUsuario") Long idUsuario);

    Optional<Endereco> findByApelido(String apelido);

    List<Endereco> findByUsuario(Usuario usuario);

    void deleteByUsuarioId(Long idUsuario);

}
