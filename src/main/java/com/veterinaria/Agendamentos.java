package com.veterinaria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Agendamentos {
    public static Parent criarCena() {
        
        Label lblTitulo = new Label("Agendamentos");
        lblTitulo.getStyleClass().add("titulo");

        Accordion sanfonaAgendas = new Accordion();
        sanfonaAgendas.setMaxWidth(500);

        // 1. Limpa a sanfona velha
        sanfonaAgendas.getPanes().clear(); 

        // 2. ORDENA O BANCO DE DADOS EM data e hora!
        BancoDeDados.getAgendas().sort(Comparator.comparing(Agenda::getData).thenComparing(Agenda::getHora));
        int i = 1;
    // 2. Passa por cada agenda no banco de dados
    for (Agenda agenda : BancoDeDados.getAgendas()) {
        //verifica se o cpf da agenda é igual ao cpf do cliente logado no home, se for, exibe a agenda
        if(agenda.getCliente().getCpf().equals(Home.getTxtCpf().getText())){
           
        
        // 3. Cria a "caixa" (VBox) que vai ficar ESCONDIDA dentro da sanfona
        VBox detalhes = new VBox(5); 
        
         // Ajuste conforme seus métodos
        
        // Adiciona um título pras agendas com a numeraçao
        
        Label tituloAgendas = new Label("Agenda " + i++); // Deixa negrito
        detalhes.getChildren().add(tituloAgendas);

        // Adiciona as infos da agenda
        detalhes.getChildren().add(new Label(agenda.exibirAgenda()));
        

        // 4. Cria a "Barra" (TitledPane) que fica visível, colocando a VBox dentro dela!
        TitledPane painelCliente = new TitledPane(agenda.getData().toString() + " - " + agenda.getHora().toString(), detalhes);
        painelCliente.setMaxHeight(25);

        // 5. Adiciona essa barra na sanfona principal
        sanfonaAgendas.getPanes().add(painelCliente);

        

      }  
    }

    ScrollPane rolagem = new ScrollPane();
        rolagem.setContent(sanfonaAgendas);
        rolagem.setFitToWidth(true);
        rolagem.addEventFilter(ScrollEvent.SCROLL, evento -> {
    
    // 1. Defina a sua velocidade aqui (Quanto maior, mais rápido). 
    // O valor 0.003 costuma ser excelente, mas você pode testar 0.005, 0.01, etc.
    double velocidade = 0.005; 
    
    // 2. Pega a força que o usuário girou a rodinha e multiplica pela sua velocidade
    double movimento = evento.getDeltaY() * velocidade;
    
    // 3. Pega a posição atual da barra de rolagem (Vvalue) e subtrai o movimento
    rolagem.setVvalue(rolagem.getVvalue() - movimento);
    
    // 4. A mágica: "Mata" o evento padrão do JavaFX para impedir que ele role super devagar junto com o nosso
    evento.consume(); 
});
         rolagem.setMaxWidth(500);
    VBox coluna = new VBox(25);
        coluna.getChildren().addAll(rolagem);
        coluna.setPadding(new Insets(30, 20, 20, 20));
        coluna.setAlignment(Pos.CENTER);
        VBox.setVgrow(rolagem,  Priority.ALWAYS);
        VBox.setVgrow(sanfonaAgendas,  Priority.ALWAYS);
        Button btnCadastrar = new Button("Novo Agendamento");
        GridPane grid = new GridPane();
        grid.add(lblTitulo, 0, 0, 3, 1);
        grid.add(coluna, 0, 1, 3, 1);
        grid.add(btnCadastrar, 0, 2);
        GridPane.setHalignment(btnCadastrar, javafx.geometry.HPos.CENTER);
        Button btnRemover = new Button("Remover Agendamento");
        grid.add(btnRemover, 2, 2);
        GridPane.setHalignment(lblTitulo, javafx.geometry.HPos.CENTER);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 20, 20, 20));
        HBox linhaBotoes = new HBox(10);
        linhaBotoes.getChildren().addAll(btnCadastrar, btnRemover);
        linhaBotoes.setAlignment(Pos.CENTER);
        HBox linhaTitulo = new HBox();
        linhaTitulo.getChildren().add(lblTitulo);
        linhaTitulo.setAlignment(Pos.CENTER);
        HBox linhaConteudo = new HBox();
        linhaConteudo.getChildren().add(coluna);
        linhaConteudo.setAlignment(Pos.CENTER);
        
        btnCadastrar.setOnAction(e -> {
            CadastrarAgenda.abrirJanela();

        });
        btnRemover.setOnAction(e -> {
            RemoverAgenda.abrirJanela();
        });
        return new VBox(20, linhaTitulo, linhaConteudo, linhaBotoes);


}
    
    public static String exibeAgendas(List<Agenda> agendas){
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------\n");
        sb.append("Agenda:\n");
        for(Agenda agenda : agendas){
            sb.append("-----------------------------\n");
            sb.append("Agenda " + (agendas.indexOf(agenda) + 1) + ":\n");
            sb.append(agenda.exibirAgenda());
        }
        return sb.toString();
    }

    public static String exibeAgenda(List<Agenda> agendas, Agenda agenda){
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------\n");
        sb.append("Agenda " + (agendas.indexOf(agenda) + 1) + ":\n");
        sb.append(agenda.exibirAgenda());
        return sb.toString();
    }

    public void mudarDataHorario(String nomeCliente, List<Agenda> agendas, LocalDate data, LocalTime hora) {
		Agenda pesquisa = pesquisaNome(nomeCliente, agendas);
		pesquisa.setData(data);
		pesquisa.setHora(hora);
	}

    public void deletarAgendamento(String nomeCliente, List<Agenda> agendamentos) {
		Iterator<Agenda> agendamento = agendamentos.iterator();
		boolean removido = false;
		
		while(agendamento.hasNext()) {
			Agenda a = agendamento.next();
			
			if(a.getCliente().getNome().equalsIgnoreCase(nomeCliente)) {
                agendamento.remove();
				removido = true;
			}
			
		}
		
		if(!removido) {
            System.out.println("-----------------------------");
			System.out.println("Cliente " + nomeCliente + " não encrontrado pra remoção.");
            System.out.println("-----------------------------");
		} else { 
            System.out.println("-----------------------------");
			System.out.println("Cliente " + nomeCliente + " removido");
            System.out.println("-----------------------------");
		}
		
		
	}

    public Agenda pesquisaNome(String nomeCliente, List<Agenda> agendas) {
		if(agendas.isEmpty()) {
            return null;
        }
		for(Agenda agendamento : agendas) {
			if(agendamento.getCliente().getNome().equalsIgnoreCase(nomeCliente)){
				return agendamento;
			}
		}
		
		return null;
	}
}
