package com.github.andregpereira.resilientshop.userapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SequenceGenerator(name = "pais", sequenceName = "sq_paises", allocationSize = 1)
@Table(name = "paises")
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais")
	@Column(name = "id_pais")
	private Long id;

	@Column(name = "nome", length = 45, nullable = false)
	private String nome;

	@Column(name = "codigo", length = 4, nullable = false)
	private String codigo;

}
