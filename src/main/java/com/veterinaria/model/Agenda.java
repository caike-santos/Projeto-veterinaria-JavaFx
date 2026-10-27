package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "agendas")
@SQLDelete(sql = "UPDATE agendas Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public class Agenda {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@EqualsAndHashCode.Include
	private UUID id;

	@Column(nullable = false)
	@NotNull(message = "A data é obrigatória")
	@FutureOrPresent(message = "A data não pode ser anterior a atual")
	private LocalDate data;

	@Column(nullable = false)
	private LocalTime hora;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", nullable = false)
	@NotNull(message = "O cliente é obrigatório")
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pet_id", nullable = false)
	@NotNull(message = "O pet é obrigatório")
	private Pet pet;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "servico_id", nullable = false)
	@NotNull(message = "O serviço é obrigatório")
	private Servico servico;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_id", nullable = false)
	@NotNull(message = "O funcionario é obrigatório")
	private Funcionario funcionario;

	@NotNull(message = "O valor é obrigatório")
	private BigDecimal valor;

	private boolean ativo = true;
	@Version
	private Long versao;
	
	public Agenda(LocalDate data, LocalTime hora, Cliente cliente, Pet pet, Servico servico, Funcionario funcionario) {
		this.data = data;
		this.hora = hora;
		this.cliente = cliente;
		this.pet = pet;
		this.servico = servico;
		this.funcionario = funcionario;
		this.valor = servico.getPreco();
	}

	public Agenda(){}


		public String exibirAgenda() {
            //exibe data no formato dd/MM/yyyy
            return "Data: " + getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                    "Hora: " + getHora().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n" +
                    "Cliente: " + getCliente().getNome() + "\n" +
                    "Pet: " + getPet().getNome() + "\n" +
                    "Serviço: " + getServico().getNome() + "\n" +
                    "Funcionário: " + getFuncionario().getNome() + "\n" +
                    "Valor: R$ " + getServico().getPreco() + "\n";
		}
	
		}
	
	


	
	
	
	
	
	


