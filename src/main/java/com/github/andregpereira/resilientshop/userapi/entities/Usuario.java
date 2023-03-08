package com.github.andregpereira.resilientshop.userapi.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@SequenceGenerator(name = "usuario", sequenceName = "usuario_sq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
	@Column(name = "id_usuario")
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "sobrenome", nullable = false)
	private String sobrenome;

	@Column(name = "cpf", length = 11, nullable = false, unique = true)
	private String cpf;

	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDate dataCriacao;
	private LocalDate dataModificacao;

}
