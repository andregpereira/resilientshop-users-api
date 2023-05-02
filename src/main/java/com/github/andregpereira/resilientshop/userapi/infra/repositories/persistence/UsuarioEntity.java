package com.github.andregpereira.resilientshop.userapi.infra.repositories.persistence;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuarios", uniqueConstraints = {@UniqueConstraint(name = "uc_cpf", columnNames = "cpf")})
@SequenceGenerator(name = "usuario", sequenceName = "sq_usuarios", allocationSize = 1)
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(length = 14, nullable = false)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @Column(nullable = false)
    private LocalDate dataModificacao;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "usuario")
    private List<Endereco> enderecos;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioEntity usuario = (UsuarioEntity) o;
        return ativo == usuario.ativo && Objects.equals(id, usuario.id) && Objects.equals(nome,
                usuario.nome) && Objects.equals(sobrenome, usuario.sobrenome) && Objects.equals(cpf,
                usuario.cpf) && Objects.equals(telefone, usuario.telefone) && Objects.equals(dataCriacao,
                usuario.dataCriacao) && Objects.equals(dataModificacao, usuario.dataModificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, cpf, telefone, dataCriacao, dataModificacao, ativo);
    }

}
