package com.veterinaria.view;

import java.util.Arrays;

import com.veterinaria.App;
import com.veterinaria.model.Atendente;
import com.veterinaria.model.Cliente;
import com.veterinaria.service.ClienteService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import com.veterinaria.utils.Mascaras;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Home {
    @Getter
    private static TextField txtCpf;

    public static Parent criarCena(Atendente atendente) {
        Region mola = new Region();
        HBox.setHgrow(mola, Priority.ALWAYS);

        Button btnDeslogar = new Button("Deslogar");
        btnDeslogar.getStyleClass().add("btnSecundario");

        HBox linhaHeader = Componentes.header("Tela de busca");
        linhaHeader.setPadding(new Insets(30, 40, 40, 40));
        linhaHeader.setAlignment(Pos.CENTER_LEFT);
        linhaHeader.getChildren().addAll(mola, btnDeslogar);

        Region mola1 = new Region();
        HBox.setHgrow(mola1, Priority.ALWAYS);

        Button btnDeslogar1 = new Button("Deslogar");
        btnDeslogar1.getStyleClass().add("btnSecundario");

        HBox linhaHeader1 = Componentes.header("Tela de cadastro");
        linhaHeader1.setPadding(new Insets(30, 40, 40, 40));
        linhaHeader1.setAlignment(Pos.CENTER_LEFT);
        linhaHeader1.getChildren().addAll(mola1, btnDeslogar1);


        Label lblSubTitulo = new Label("Bem-Vindo(a) " + atendente.getNome());
        lblSubTitulo.getStyleClass().add("subtitulo");

        HBox linhaSubTitulo = new HBox(10);
        linhaSubTitulo.getChildren().add(lblSubTitulo);

        Label lblBuscar = new Label("Buscar cliente, insira o CPF");
        lblBuscar.getStyleClass().add("subtitulo");
        HBox linhaBuscar = new HBox();
        linhaBuscar.getChildren().add(lblBuscar);

        txtCpf = new TextField();
        txtCpf.setMaxWidth(250);
        Mascaras.aplicar(txtCpf, "###.###.###-##");

        HBox linhaCpf = new HBox();
        linhaCpf.getChildren().add(txtCpf);

        Button btnBuscar = new Button("Buscar");
        HBox linhaBtnBuscar = new HBox();
        linhaBtnBuscar.getChildren().add(btnBuscar);
        btnBuscar.getStyleClass().add("btnSecundario");

        VBox conteudoBuscar = new VBox(30);
        conteudoBuscar.getChildren().addAll(linhaSubTitulo, linhaBuscar, linhaCpf, linhaBtnBuscar);

        VBox colunaBuscar = new VBox(100);
        colunaBuscar.getChildren().addAll(linhaHeader, conteudoBuscar);

        VBox colunaCadastrar = new VBox(15);
        colunaCadastrar.getChildren().addAll(linhaHeader1, ExibirClientes.criarCena());

        Arrays.asList(linhaSubTitulo, linhaBtnBuscar, linhaBuscar, linhaCpf).forEach(linha -> linha.setAlignment(Pos.CENTER));

        TabPane painelDeAbas = new TabPane();

        Tab abaBusca = new Tab("Busca de Cliente");
        abaBusca.setClosable(false);

        abaBusca.setContent(colunaBuscar);

        Tab abaListaClientes = new Tab("Lista dos clientes");
        abaListaClientes.setClosable(false);

        abaListaClientes.setContent(colunaCadastrar);

        painelDeAbas.getTabs().addAll(abaBusca, abaListaClientes);

        VBox verticalHome = new VBox(120);
        verticalHome.getChildren().addAll(painelDeAbas);

        VBox.setVgrow(painelDeAbas, Priority.ALWAYS);

        btnDeslogar.setOnAction(evento -> {
            Login.setAtendente(null);
            App.inserirCena(Login.criarCena());
        });

        btnDeslogar1.setOnAction(evento -> {
            Login.setAtendente(null);
            App.inserirCena(Login.criarCena());
        });

        btnBuscar.setOnAction(evento -> Acoes.executar(() -> {
            ClienteService clienteService = App.getBean(ClienteService.class);
            String cpf = txtCpf.getText();
            Cliente cliente = clienteService.buscarCliente(cpf, atendente.getNome());

            if (cliente == null) {
                if (Alertas.confirmar("Cliente não encontrado!", "O cpf de numero: " + cpf + " não foi encontrado, quer cadastrá-lo?")) {
                    App.inserirCena(Cadastro.criarCena(atendente));
                }
            } else {
                if (Alertas.confirmar("Cliente encontrado", "Cliente " + cliente.getNome() + " está cadastrado, quer acessar os seus dados?")) {
                    App.inserirCena(EditarCliente.criarCena(cliente));
                }
            }
        }));

        return verticalHome;

    }
}
