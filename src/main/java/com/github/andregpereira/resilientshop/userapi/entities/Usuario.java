package com.github.andregpereira.resilientshop.userapi.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
	@Column(name = "id_usuario")
	private Long id_usuario;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "sobrenome", nullable = false)
	private String sobrenome;

	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;

	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDate dataCriacao;

	@Column(name = "data_modificacao", nullable = false)
	private LocalDate dataModificacao;

}
