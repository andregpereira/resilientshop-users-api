package com.github.andregpereira.resilientshop.userapi.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@SequenceGenerator(name = "endereco", sequenceName = "sq_endereco", allocationSize = 1)
@Table(name = "enderecos")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
	@Column(name = "id_endereco")
	private Long id;

	@Column(length = 45, nullable = false)
	private String rua;

	@Column(length = 10, nullable = false)
	private Integer numero;

	@Column(length = 9, nullable = false)
	private String cep;

	@Column(length = 45)
	private String complemento;

	@Column(nullable = false)
	private String cidade;

	@Column(nullable = false)
	private String estado;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pais", foreignKey = @ForeignKey(name = "fk_id_pais"), nullable = false)
	private Pais pais;

}
