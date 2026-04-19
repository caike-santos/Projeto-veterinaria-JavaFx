package com.veterinaria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastrarAgenda {
    private static ComboBox<String> cbmServico;
    private static DatePicker data;
    private static TextField txtHora;
    private static  ComboBox<String> cbmFuncionario;
    public static void abrirJanela(){
        Stage popup = new Stage();
        popup.setTitle("Remover Agenda");

        Image logo = new Image(CadastrarAgenda.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView visulogoCadastro = new ImageView(logo);
        visulogoCadastro.setFitHeight(60);
        visulogoCadastro.setPreserveRatio(true);

        Label lblTituloRemover = new Label(" Cadastrar Agenda");
        lblTituloRemover.getStyleClass().add("titulo");

        

        HBox header = new HBox(20);
        header.getChildren().addAll(visulogoCadastro, lblTituloRemover);
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER);
        //fimHeader

        Label lblServico = new Label("Serviço:");
        cbmServico = new ComboBox<>();
        for (Servicos servico : BancoDeDados.getServicos()) {
            cbmServico.getItems().add(servico.getNome());
        }
        cbmServico.setPromptText("Selecione o serviço");
        cbmServico.setMaxWidth(250);

        Label lblData = new Label("Data do agendamento:");
        data = new DatePicker();
        data.setPromptText("Selecione a data");
        data.setMaxWidth(250);

        Label lblHora = new Label("Hora do agendamento:");
        txtHora = new TextField();
        txtHora.setPromptText("Ex: 09:00");
        txtHora.setMaxWidth(250);

        
        Label lblFuncionario = new Label("Funcionário:");
        cbmFuncionario = new ComboBox<>();
        cbmFuncionario.setPromptText("Selecione o funcionário");
        cbmFuncionario.setMaxWidth(250);

        // Listener para atualizar os funcionários quando um serviço é selecionado
        cbmServico.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            cbmFuncionario.getItems().clear(); // Limpa os funcionários anteriores
            
            if (newVal != null) { // Verifica se um serviço foi selecionado
                for (Funcionario funcionario : BancoDeDados.getFuncionarios()) {
                    for (Servicos servico : funcionario.getFuncoes()) {
                        if (servico.getNome().equals(newVal)) {
                            cbmFuncionario.getItems().add(funcionario.getNome());
                            break; // Evita adicionar o mesmo funcionário duas vezes
                        }
                    }
                }
            }
        });

        Button  btnCadastrar = new Button("Cadastrar");
        HBox linhaBtn = new HBox();
        linhaBtn.getChildren().add(btnCadastrar);
        linhaBtn.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.add(header, 0, 0, 3, 1);
        grid.add(lblServico, 0, 1);
        grid.add(cbmServico, 1, 1); 
        grid.add(lblData, 0, 2);
        grid.add(data, 1, 2);
        grid.add(lblHora, 0, 3);
        grid.add(txtHora, 1, 3);
        grid.add(lblFuncionario, 0, 4);
        grid.add(cbmFuncionario, 1, 4);
        grid.add(linhaBtn, 0, 5, 3, 1);
        GridPane.setHalignment(linhaBtn, javafx.geometry.HPos.CENTER);

        grid.setVgap(10);
        grid.setPadding(new Insets(30, 20, 20, 20));
        HBox linhaGridBox = new HBox();
        linhaGridBox.getChildren().add(grid);
        linhaGridBox.setAlignment(Pos.CENTER);


        VBox principal = new VBox(50);
        principal.getChildren().addAll(header, linhaGridBox); 
        principal.setAlignment(Pos.CENTER);
        

        btnCadastrar.setOnAction(evento -> {
            
            
             
            if(cbmServico.getSelectionModel().isEmpty() || data.getValue() == null || txtHora.getText().trim().isEmpty() || cbmFuncionario.getSelectionModel().isEmpty()){
                CadastroCliente.mostarNaTela("Faltam dados do Agendamento!", "Por favor, preencha todos os campos para cadastrar o agendamento.");
                return;
            }else{
                // Aceita formatos como "9:00", "09:00", "9:30:00", "09:30:00"
                DateTimeFormatter[] formatadores = {
                    DateTimeFormatter.ofPattern("H:mm"),      // 9:00
                    DateTimeFormatter.ofPattern("HH:mm"),     // 09:00
                    DateTimeFormatter.ofPattern("H:mm:ss"),   // 9:00:00
                    DateTimeFormatter.ofPattern("HH:mm:ss")   // 09:00:00
                };
                
                LocalTime hora = null;
                String horaTexto = txtHora.getText().trim();
                
                for (DateTimeFormatter fmt : formatadores) {
                    try {
                        hora = LocalTime.parse(horaTexto, fmt);
                        break;
                    } catch (Exception e) {
                        // Tenta o próximo formato
                    }
                }
                
                if (hora == null) {
                    CadastroCliente.mostarNaTela("Formato de hora inválido!", "Use o formato: HH:mm ou HH:mm:ss\nExemplos: 09:00 ou 09:30:00");
                    return;
                }
                
                Agenda novaAgenda = new Agenda( data.getValue(), hora, EditarCliente.getC(), BancoDeDados.getServicos().get(cbmServico.getSelectionModel().getSelectedIndex()), BancoDeDados.getFuncionarios().get(cbmFuncionario.getSelectionModel().getSelectedIndex()), BancoDeDados.getAgendas());
                novaAgenda.adicionarAgenda(novaAgenda);
                

                Alert concluido = new Alert(Alert.AlertType.INFORMATION);
                concluido.setTitle("Cadastro Finalizado");
                concluido.setHeaderText("Imprimindo Cadastro");
                //String nomes = ""; 
                /*for(Pet pet : CadastroPet.getListaPet()){
                   nomes += pet.getNome() + ", ";
                }*/
                //concluido.setContentText(c.getNome() + " seus(s) pet(s) " + nomes + "foi cadastrado com Sucesso");
                
                concluido.setContentText("Agendamento para o serviço " + cbmServico.getSelectionModel().getSelectedItem() + " no dia " + data.getValue().toString() + " às " + txtHora.getText().trim() + " com o funcionário " + cbmFuncionario.getSelectionModel().getSelectedItem() + " foi cadastrado com Sucesso");
                concluido.showAndWait();
                popup.close();
            }
            
        });
        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();
    }
}
