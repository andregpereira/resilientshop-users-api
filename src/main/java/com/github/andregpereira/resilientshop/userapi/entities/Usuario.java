package com.github.andregpereira.resilientshop.userapi.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@SequenceGenerator(name = "usuario", sequenceName = "sq_usuario", allocationSize = 1)
@Table(name = "tb_usuarios", uniqueConstraints = { @UniqueConstraint(name = "uc_cpf", columnNames = "cpf") })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
	@Column(name = "id_usuario")
	private Long id;

	@Column(length = 255, nullable = false)
	private String nome;

	@Column(length = 255, nullable = false)
	private String sobrenome;

	@Column(length = 14, nullable = false)
	private String cpf;

	@Column(length = 20)
	private String telefone;

	@Column(nullable = false, updatable = false)
	private LocalDate dataCriacao;

	@Column
	private LocalDate dataModificacao;

	@Column(nullable = false)
	private boolean ativo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco", foreignKey = @ForeignKey(name = "fk_id_endereco"))
	private Endereco endereco;

}
