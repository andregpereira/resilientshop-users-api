package com.github.andregpereira.resilientshop.userapi.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@SequenceGenerator(name = "pais", sequenceName = "sq_paises", allocationSize = 1)
@Table(name = "paises", uniqueConstraints = { @UniqueConstraint(name = "uc_codigo", columnNames = "codigo") })
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais")
	@Column(name = "id_pais")
	private Long id;

	@Column(name = "nome", length = 45, nullable = false)
	private String nome;

	@Column(name = "codigo", length = 3, nullable = false)
	private String codigo;

	@OneToMany(mappedBy = "pais", targetEntity = Endereco.class)
	private List<Endereco> enderecos;

}
