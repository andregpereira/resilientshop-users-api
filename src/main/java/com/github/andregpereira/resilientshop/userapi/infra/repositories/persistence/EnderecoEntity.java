package com.github.andregpereira.resilientshop.userapi.infra.repositories.persistence;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_enderecos")
@SequenceGenerator(name = "endereco", sequenceName = "sq_enderecos", allocationSize = 1)
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
    @Column(name = "id_endereco")
    private Long id;

    @Column(length = 45, nullable = false)
    private String apelido;

    @Column(length = 9, nullable = false)
    private String cep;

    @Column(length = 45, nullable = false)
    private String estado;

    @Column(length = 45, nullable = false)
    private String cidade;

    @Column(length = 45, nullable = false)
    private String bairro;

    @Column(length = 45, nullable = false)
    private String rua;

    @Column(length = 10, nullable = false)
    private String numero;

    @Column(length = 45)
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "id_pais", nullable = false, foreignKey = @ForeignKey(name = "fk_id_pais"))
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_id_usuario"))
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EnderecoEntity endereco = (EnderecoEntity) o;
        return Objects.equals(id, endereco.id) && Objects.equals(apelido, endereco.apelido) && Objects.equals(cep,
                endereco.cep) && Objects.equals(estado, endereco.estado) && Objects.equals(cidade,
                endereco.cidade) && Objects.equals(bairro, endereco.bairro) && Objects.equals(rua,
                endereco.rua) && Objects.equals(numero, endereco.numero) && Objects.equals(complemento,
                endereco.complemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apelido, cep, estado, cidade, bairro, rua, numero, complemento);
    }

}
