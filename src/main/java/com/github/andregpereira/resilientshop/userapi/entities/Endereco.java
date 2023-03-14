package com.github.andregpereira.resilientshop.userapi.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "endereco", sequenceName = "sq_enderecos", allocationSize = 1)
@Table(name = "enderecos")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
	@Column(name = "id_endereco")
	private Long id;

	@Column(name = "rua", length = 45, nullable = false)
	private String rua;

	@Column(name = "numero", length = 60, nullable = false)
	private Integer numero;

	@Column(name = "cep", length = 9, nullable = false)
	private String cep;

	@Column(name = "complemento", length = 45, nullable = false)
	private String complemento;

	@Column(name = "cidade", length = 45, nullable = false)
	private String cidade;

	@Column(name = "estado", length = 45, nullable = false)
	private String estado;

	@ManyToOne
	@JoinColumn(name = "id_pais", foreignKey = @ForeignKey(name = "fk_id_pais"), nullable = false)
	private Pais pais;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(nome = "id_usuario")
	private Usuario usuario;

}
