package com.github.andregpereira.resilientshop.userapi.entities;

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_enderecos")
@SequenceGenerator(name = "endereco", sequenceName = "sq_endereco", allocationSize = 1)
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
	@Column(name = "id_endereco")
	private Long id;

	@Column(length = 9, nullable = false)
	private String cep;

	@Column(nullable = false)
	private String estado;

	@Column(nullable = false)
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
	@JoinColumn(name = "id_pais", foreignKey = @ForeignKey(name = "fk_id_pais"), nullable = false)
	private Pais pais;

}
