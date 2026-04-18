package com.veterinaria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Agenda {
	private LocalDate data;
	private LocalTime hora;
	private Cliente cliente;
	private Servicos servico;
	private Funcionario funcionario;
	private List<Agenda> agendas = new ArrayList<>();
	
	
	public Agenda(LocalDate data, LocalTime hora, Cliente cliente, Servicos servico, Funcionario funcionario, List<Agenda> agendas) {
		this.data = data;
		this.hora = hora;
		this.cliente = cliente;
		this.servico = servico;
		this.funcionario = funcionario;
		this.agendas = agendas;
	}

	public void adicionarAgenda(Agenda agenda) {
		agendas.add(agenda);
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Servicos getServico() {
		return servico;
	}

	public void setServico(Servicos servico) {
		this.servico = servico;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

		public String exibirAgenda() {
			StringBuilder sb = new StringBuilder();
			sb.append("Data: " + getData() + "\n");
			sb.append("Hora: " + getHora() + "\n");
			sb.append("Cliente: " + getCliente().getNome() + "\n");
			sb.append("Serviço: " + getServico().getNome() + "\n");
			sb.append("Funcionário: " + getFuncionario().getNome() + "\n");
			sb.append("Valor: R$ " + getServico().getPreco() + "\n");
			return sb.toString();
		}
	
		}
	
	


	
	
	
	
	
	


