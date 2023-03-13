package com.github.andregpereira.resilientshop.userapi.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
		@UniqueConstraint(name = "telefone_uc", columnNames = "telefone"),
		@UniqueConstraint(name = "cpf_uc", columnNames = "cpf")
		})
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

	@Column(name = "nome", length = 35, nullable = false)
	private String nome;

	@Column(name = "sobrenome", length = 60, nullable = false)
	private String sobrenome;

	@Column(name = "telefone", length = 20, nullable = false)
	private String telefone;

	@Column(name = "cpf", length = 11, nullable = false)
	private String cpf;

	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDate dataCriacao;

	@Column(name = "data_modificacao")
	private LocalDate dataModificacao;

}
