package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "agendas")
@SQLDelete(sql = "UPDATE agendas Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public class Agenda {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
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
	@JoinColumn(name = "servico_id", nullable = false)
	@NotNull(message = "O serviço é obrigatório")
	private Servico servico;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_id", nullable = false)
	@NotNull(message = "O funcionario é obrigatório")
	private Funcionario funcionario;

	private boolean ativo = true;
	@Version
	private Long versao;
	
	public Agenda(LocalDate data, LocalTime hora, Cliente cliente, Servico servico, Funcionario funcionario) {
		this.data = data;
		this.hora = hora;
		this.cliente = cliente;
		this.servico = servico;
		this.funcionario = funcionario;
	}

	public Agenda(){}


		public String exibirAgenda() {
			StringBuilder sb = new StringBuilder();
			//exibe data no formato dd/MM/yyyy
			sb.append("Data: " + getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
			sb.append("Hora: " + getHora().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n");
			sb.append("Cliente: " + getCliente().getNome() + "\n");
			sb.append("Serviço: " + getServico().getNome() + "\n");
			sb.append("Funcionário: " + getFuncionario().getNome() + "\n");
			sb.append("Valor: R$ " + getServico().getPreco() + "\n");
			return sb.toString();
		}
	
		}
	
	


	
	
	
	
	
	


