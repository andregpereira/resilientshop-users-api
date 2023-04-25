package com.github.andregpereira.resilientshop.userapi.infra.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_paises")
@SequenceGenerator(name = "pais", sequenceName = "sq_paises", allocationSize = 1)
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais")
    @Column(name = "id_pais")
    private Long id;

    @Column(name = "nome", length = 45, nullable = false)
    private String nome;

    @Column(name = "codigo", length = 4, nullable = false)
    private String codigo;

    @OneToMany(mappedBy = "pais")
    private List<Endereco> enderecos;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pais pais = (Pais) o;
        return Objects.equals(id, pais.id) && Objects.equals(nome, pais.nome) && Objects.equals(codigo, pais.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, codigo);
    }

}
