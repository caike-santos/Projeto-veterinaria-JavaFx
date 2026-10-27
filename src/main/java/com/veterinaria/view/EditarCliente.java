package com.veterinaria.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.veterinaria.model.Cliente;
import com.veterinaria.App;
import com.veterinaria.model.*;
import com.veterinaria.service.ClienteService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import com.veterinaria.utils.Datas;
import com.veterinaria.utils.Mascaras;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EditarCliente {
    @Getter
    private static TextField txtNome;
    @Getter
    private static DatePicker dpIdade;
    @Getter
    private static TextField txtCpf;
    @Getter
    private static TextField txtEmail;
    @Getter
    private static TextField txtDdd;
    @Getter
    private static TextField txtTelefone;
    @Getter
    private static TextField txtBairro;
    @Getter
    private static TextField txtRua;
    @Getter
    private static TextField txtCep;
    @Getter
    private static TextField txtNumero;
    @Getter
    private static TextArea areaComplemento;
    @Getter
    private static Cliente c;

    public static Parent criarCena(Cliente c1) {
        c = c1;
        HBox header = Componentes.header("Editar Cliente: " + c1.getNome());

        HBox linhaHeader1 = Componentes.header("Lista de Agendas do Cliente: " + c1.getNome());
        linhaHeader1.setPadding(new Insets(30, 40, 40, 40));

        HBox linhaHeader2 = Componentes.header("Lista de Pets do Cliente: " + c1.getNome());
        linhaHeader2.setPadding(new Insets(30, 40, 40, 40));


        GridPane gridDados = new GridPane();
        gridDados.setHgap(10);
        gridDados.setVgap(15);

        Label lblSubTituloDados = new Label("Dados Pessoais");
        lblSubTituloDados.getStyleClass().add("subtitulo");

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setMaxWidth(150);

        Label lblIdade = new Label("Data de Nascimento:");
        dpIdade = new DatePicker();
        dpIdade.setMaxWidth(150);
        Datas.configurarPickerBR(dpIdade, false);

        Label lblCpf = new Label("Cpf:");
        txtCpf = new TextField();
        txtCpf.setMaxWidth(150);

        Mascaras.aplicar(txtCpf, "###.###.###-##");


        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@email.com");
        txtEmail.setMaxWidth(150);

        Label lblDdd = new Label("Ddd:");
        txtDdd = new TextField();
        txtDdd.setMaxWidth(150);
        Mascaras.aplicar(txtDdd, "##");

        Label lblTelefone = new Label("Telefone:");
        txtTelefone = new TextField();
        txtTelefone.setPromptText("99999-9999");
        txtTelefone.setMaxWidth(150);
        Mascaras.aplicar(txtTelefone, "#####-####");


        gridDados.add(lblSubTituloDados, 0, 0, 2, 1);
        GridPane.setHalignment(lblSubTituloDados, javafx.geometry.HPos.CENTER);

        gridDados.add(lblNome, 0, 1);
        gridDados.add(txtNome, 1, 1);

        gridDados.add(lblIdade, 0, 2);
        gridDados.add(dpIdade, 1, 2);

        gridDados.add(lblCpf, 0, 3);
        gridDados.add(txtCpf, 1, 3);

        gridDados.add(lblEmail, 0, 4);
        gridDados.add(txtEmail, 1, 4);

        gridDados.add(lblDdd, 0, 5);
        gridDados.add(txtDdd, 1, 5);

        gridDados.add(lblTelefone, 0, 6);
        gridDados.add(txtTelefone, 1, 6);

        GridPane gridEndereco = new GridPane();
        gridEndereco.setHgap(10);
        gridEndereco.setVgap(15);

        Label lblSubTituloEndereco = new Label("Endereço");
        lblSubTituloEndereco.getStyleClass().add("subtitulo");

        Label lblBairro = new Label("Bairro:");
        txtBairro = new TextField();
        txtBairro.setMaxWidth(150);

        Label lblRua = new Label("Rua:");
        txtRua = new TextField();
        txtRua.setMaxWidth(150);

        Label lblNumero = new Label("Numero:");
        txtNumero = new TextField();
        txtNumero.setMaxWidth(150);
        Mascaras.aplicar(txtNumero, "######");

        Label lblCep = new Label("Cep:");
        txtCep = new TextField();
        txtCep.setPromptText("00000-000");
        txtCep.setMaxWidth(150);
        Mascaras.aplicar(txtCep, "#####-###");


        Label lblComplemento = new Label("Complemento:");
        areaComplemento = new TextArea();
        areaComplemento.setEditable(true);
        areaComplemento.setMaxWidth(150);
        areaComplemento.setPrefHeight(75);

        gridEndereco.add(lblSubTituloEndereco, 0, 0, 2, 1);
        GridPane.setHalignment(lblSubTituloEndereco, javafx.geometry.HPos.CENTER);

        gridEndereco.add(lblBairro, 0, 1);
        gridEndereco.add(txtBairro, 1, 1);

        gridEndereco.add(lblRua, 0, 2);
        gridEndereco.add(txtRua, 1, 2);

        gridEndereco.add(lblCep, 0, 3);
        gridEndereco.add(txtCep, 1, 3);

        gridEndereco.add(lblNumero, 0, 4);
        gridEndereco.add(txtNumero, 1, 4);

        gridEndereco.add(lblComplemento, 0, 5);
        gridEndereco.add(areaComplemento, 1, 5);

        GridPane gridPets = new GridPane();
        gridPets.setVgap(15);
        gridPets.setHgap(10);

        Label lblPet = new Label("Pets Cadastrados");
        lblPet.getStyleClass().add("subtitulo");
        GridPane.setHalignment(lblPet, javafx.geometry.HPos.CENTER);
        gridPets.add(lblPet, 0, 0, 2, 1);

        //fazer uma sanfona de pets do cliente
        List<TitledPane> paineisPets = new ArrayList<>();

        if (c1.getPets().isEmpty()) {
            paineisPets.add(new TitledPane("Nenhum pet cadastrado", new VBox()));
        } else {
            c1.getPets().sort(Comparator.comparing(Pet::getNome, String.CASE_INSENSITIVE_ORDER));
            for (Pet pet : c1.getPets()) {
                VBox detalhes = new VBox(5);
                Label tituloPets = new Label("\n🐾 Pets " + (c1.getPets().indexOf(pet) + 1));
                tituloPets.setStyle("-fx-font-weight: bold;");
                detalhes.getChildren().add(tituloPets);
                detalhes.getChildren().add(new Label(pet.exibePet()));

                paineisPets.add(new TitledPane(pet.getNome(), detalhes));
            }
        }

        VBox coluna = Componentes.listaSanfona(paineisPets);

        //adicionar a sanfona na grid
        gridPets.add(coluna, 0, 1, 2, 1);
        GridPane.setVgrow(coluna, Priority.ALWAYS);

        Button btnCadastrar = new Button("Novo Pet");

        gridPets.add(btnCadastrar, 0, gridPets.getRowCount());
        GridPane.setHalignment(btnCadastrar, javafx.geometry.HPos.CENTER);

        Button btnRemover = new Button("Remover Pet");
        gridPets.add(btnRemover, 1, gridPets.getRowCount() - 1);

        HBox cadastro = new HBox(100);
        cadastro.getChildren().addAll(gridDados, gridEndereco);

        Arrays.asList(header, cadastro).forEach(linha -> linha.setAlignment(Pos.CENTER));

        Button btnSalvarTudo = new Button("Salvar dados do cliente");
        btnSalvarTudo.getStyleClass().add("btn_salvar");

        Button btnCancelar = new Button("Cancelar");

        btnCancelar.setOnAction(evento -> {
            if (Alertas.confirmar("CANCELAR EDIÇÃO!!!", "TEM CERTEZA QUE QUER CANCELAR?")) {
                App.inserirCena(Home.criarCena(Login.getAtendente()));
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroPet.criarCena();
            AddPet.abrirJanela();
            App.inserirCena(EditarCliente.criarCena(EditarCliente.getC()));
        });

        btnRemover.setOnAction(e -> {
            RemoverPet.abrirJanela();
            App.inserirCena(EditarCliente.criarCena(EditarCliente.getC()));
        });

        HBox rodape = new HBox(50);
        rodape.setAlignment(Pos.CENTER);

        rodape.setPadding(new Insets(20, 30, 30, 30));

        rodape.getChildren().addAll(btnSalvarTudo, btnCancelar);

        btnSalvarTudo.setOnAction(evento -> Acoes.executar(() -> {
            try {
                dpIdade.setValue(dpIdade.getConverter().fromString(dpIdade.getEditor().getText()));
            } catch (Exception ex) {
                dpIdade.setValue(null); // Se deu erro na conversão, vira nulo para ser barrado no if
            }

            if (EditarCliente.temCampoVazioEndereco()) {
                Alertas.erro("Faltam dados do endereço do Cliente!", "Por favor, volte na aba 'Dados do Cliente' e preencha todos os campos.");
                log.warn("Faltam dados do endereço do Cliente!");
                return;
            } else if (EditarCliente.temCampoVazioDados()) {
                Alertas.erro("Faltam dados pessoais do Cliente!", "Por favor, volte na aba 'Dados do Cliente' e preencha todos os campos.");
                log.warn("Faltam dados pessoais do Cliente!");
                return;
            }

            int idadeNum = Datas.idade(dpIdade.getValue());

            if (EditarCliente.getTxtTelefone().getLength() < 9 || EditarCliente.getTxtDdd().getLength() < 2) {
                Alertas.erro("Quantidade de caracteres do ddd e telefone invalido", "Por favor, preencha o campo ddd e telefone corretamente");
                log.warn("Quantidade de caracteres do ddd e telefone invalido");
            } else if (idadeNum < 18 || idadeNum > 120) {
                Alertas.erro("Idade do cliente invalida", "Por favor, preencha o campo idade corretamente(Apenas maiores de idade podem ter cadastro)");
                log.warn("Idade do cliente invalida");
            } else if (EditarCliente.getTxtCpf().getLength() < 11) {
                Alertas.erro("Quantidade de caracteres do cpf invalido", "Por favor, preencha o campo cpf corretamente");
                log.warn("Quantidade de caracteres do cpf invalido");
            } else if (EditarCliente.getTxtCep().getLength() < 8) {
                Alertas.erro("Quantidade de caracteres do cep invalido", "Por favor, preencha o campo cep corretamente");
                log.warn("Quantidade de caracteres do cep invalido");
            } else if (EditarCliente.emailInvalido()) {
                Alertas.erro("E-mail Inválido", "Por favor, digite um e-mail no formato correto (exemplo@dominio.com).");
                log.warn("E-mail Inválido");
            } else {
                c1.getEndereco().setBairro(txtBairro.getText());
                c1.getEndereco().setCep(txtCep.getText());
                c1.getTelefone().setDdd(txtDdd.getText());
                c1.setEmail(txtEmail.getText());
                c1.getEndereco().setNumero(Integer.parseInt(txtNumero.getText()));
                c1.getEndereco().setRua(txtRua.getText());
                c1.getTelefone().setNumero(txtTelefone.getText());
                c1.getEndereco().setComplemento(areaComplemento.getText());

                ClienteService clienteService = App.getBean(ClienteService.class);
                EditarCliente.setC(clienteService.atualizarDados(c1));

                Alertas.info("Imprimindo Cadastro", exibirDados(c1));
                App.inserirCena(Home.criarCena(Login.getAtendente()));
            }
        }));

        VBox vertical = new VBox(50);
        vertical.setAlignment(Pos.CENTER);
        vertical.getChildren().addAll(header, cadastro, rodape);

        String num2 = String.valueOf(c1.getEndereco().getNumero());
        txtCpf.setText(c1.getCpf());
        txtBairro.setText(c1.getEndereco().getBairro());
        txtCep.setText(c1.getEndereco().getCep());
        txtDdd.setText(c1.getTelefone().getDdd());
        txtEmail.setText(c1.getEmail());
        dpIdade.setValue(c1.getDataNascimento());
        txtNome.setText(c1.getNome());
        txtNumero.setText(num2);
        txtRua.setText(c1.getEndereco().getRua());
        txtTelefone.setText(c1.getTelefone().getNumero());
        areaComplemento.setText(c1.getEndereco().getComplemento());

        //Arrays.asList(txtBairro, txtCep, txtDdd, txtEmail, txtNumero, txtTelefone).forEach(e -> e.);
        Arrays.asList(txtCpf, dpIdade).forEach(e -> e.setDisable(true));
        Arrays.asList(txtCpf, dpIdade).forEach(e -> e.getStyleClass().add("naoEditavel"));

        VBox colunaAgendas = new VBox(15);
        colunaAgendas.getChildren().addAll(linhaHeader1, Agendamentos.criarCena());

        HBox linhaGridPets = new HBox(10);
        linhaGridPets.getChildren().addAll(gridPets);
        linhaGridPets.setAlignment(Pos.CENTER);

        VBox colunaPets = new VBox(15);
        colunaPets.getChildren().addAll(linhaHeader2, linhaGridPets);
        colunaPets.setAlignment(Pos.TOP_CENTER);

        TabPane painelDeAbas = new TabPane();

        Tab abaEditar = new Tab("Editar Cliente");
        abaEditar.setClosable(false);

        abaEditar.setContent(vertical);

        Tab abaListaAgendas = new Tab("Lista de Agendas");
        abaListaAgendas.setClosable(false);

        abaListaAgendas.setContent(colunaAgendas);

        Tab abaListaPets = new Tab("Lista de Pets");
        abaListaPets.setClosable(false);
        abaListaPets.setContent(colunaPets);

        painelDeAbas.getTabs().addAll(abaEditar, abaListaAgendas, abaListaPets);

        VBox verticalHome = new VBox(120);
        verticalHome.getChildren().addAll(painelDeAbas);

        VBox.setVgrow(painelDeAbas, Priority.ALWAYS);

        return verticalHome;
    }

    public static void setC(Cliente c) {
        EditarCliente.c = c;
    }

    public static boolean temCampoVazioEndereco() {
        return txtBairro.getText().trim().isEmpty() || txtCep.getText().trim().isEmpty() || txtNumero.getText().trim().isEmpty() || txtRua.getText().trim().isEmpty();
    }

    public static boolean temCampoVazioDados() {
        return txtCpf.getText().trim().isEmpty() || txtDdd.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || dpIdade.getValue() == null || txtNome.getText().trim().isEmpty() || txtTelefone.getText().trim().isEmpty();
    }

    public static boolean emailInvalido() {
        String emailDigitado = txtEmail.getText().trim();
        String regraEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        return !emailDigitado.matches(regraEmail);
    }

    public static String exibirDados(Cliente c) {
        StringBuilder exibirDados = new StringBuilder();
        exibirDados = new StringBuilder(c.exibeDados());
        for (Pet pet : c.getPets()) {
            exibirDados.append("\nPet ").append(c.getPets().indexOf(pet) + 1).append(pet.exibePet());
        }
        return exibirDados.toString();
    }

}
