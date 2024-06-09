package com.github.andregpereira.resilientshop.userapi.infra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_usuarios", uniqueConstraints = {@UniqueConstraint(name = "uc_cpf", columnNames = "cpf"),
        @UniqueConstraint(name = "uc_email", columnNames = "email")})
@SequenceGenerator(name = "usuario", sequenceName = "sq_usuarios", allocationSize = 1)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String nomeSocial;

    @Column(length = 14, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String email;

    @Column(length = 20)
    private String celular;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate dataModificacao;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Usuario usuario))
            return false;
        return ativo == usuario.ativo && Objects.equals(id, usuario.id) && Objects.equals(nome,
                usuario.nome) && Objects.equals(nomeSocial, usuario.nomeSocial) && Objects.equals(cpf,
                usuario.cpf) && Objects.equals(dataNascimento, usuario.dataNascimento) && Objects.equals(email,
                usuario.email) && Objects.equals(celular, usuario.celular) && Objects.equals(dataCriacao,
                usuario.dataCriacao) && Objects.equals(dataModificacao, usuario.dataModificacao) && Objects.equals(
                enderecos, usuario.enderecos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, nomeSocial, cpf, dataNascimento, email, celular, dataCriacao, dataModificacao, ativo,
                enderecos);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]").add("id=" + id).add(
                "nome='" + nome + "'").add("apelido='" + nomeSocial + "'").add("cpf='" + cpf + "'").add(
                "dataNascimento=" + dataNascimento).add("email='" + email + "'").add("celular='" + celular + "'").add(
                "dataCriacao=" + dataCriacao).add("dataModificacao=" + dataModificacao).add("ativo=" + ativo).add(
                "enderecos=" + enderecos).toString();
    }

}
