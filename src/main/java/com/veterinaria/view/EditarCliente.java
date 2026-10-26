package com.veterinaria.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.veterinaria.model.Cliente;
import com.veterinaria.App;
import com.veterinaria.model.*;
import com.veterinaria.service.ClienteService;
import jakarta.validation.ConstraintViolationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
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
        //header
        Image logo = new Image(CadastroPet.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView visulogoCadastro = new ImageView(logo);
        visulogoCadastro.setFitHeight(60);
        visulogoCadastro.setPreserveRatio(true);

        Label lblTituloCadastro = new Label("Editar Cliente: " + c1.getNome());
        lblTituloCadastro.getStyleClass().add("titulo");

        HBox header = new HBox(20);
        header.getChildren().addAll(visulogoCadastro, lblTituloCadastro);
        header.getStyleClass().add("header");
        //fimHeader

        //header1
        Image logo1 = new Image(Login.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView mostarLogo1 = new ImageView(logo1);
        mostarLogo1.setFitHeight(60);
        mostarLogo1.setPreserveRatio(true);

        Label lblTitulo1 = new Label("Lista de Agendas do Cliente: " + c1.getNome());
        lblTitulo1.getStyleClass().add("titulo");


        HBox linhaHeader1 = new HBox(20);
        linhaHeader1.setPadding(new Insets(30, 40, 40, 40));
        linhaHeader1.setAlignment(Pos.CENTER);
        linhaHeader1.getChildren().addAll(mostarLogo1, lblTitulo1);

        //header2
        Image logo2 = new Image(Login.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView mostarLogo2 = new ImageView(logo2);
        mostarLogo2.setFitHeight(60);
        mostarLogo2.setPreserveRatio(true);
        Label lblTitulo2 = new Label("Lista de Pets do Cliente: " + c1.getNome());
        lblTitulo2.getStyleClass().add("titulo");
        HBox linhaHeader2 = new HBox(20);
        linhaHeader2.setPadding(new Insets(30, 40, 40, 40));
        linhaHeader2.setAlignment(Pos.CENTER);
        linhaHeader2.getChildren().addAll(mostarLogo2, lblTitulo2);


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
        dpIdade.setPromptText("dd/mm/aaaa");
        TextField editorData = dpIdade.getEditor();

        // 2. Cria o "Ouvinte" que espia tudo o que é digitado em tempo real
        editorData.textProperty().addListener((observavel, valorAntigo, valorNovo) -> {

            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 8 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 8) {
                apenasNumeros = apenasNumeros.substring(0, 8);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 2 || i == 4) {
                    formatado.append("/");
                }
                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.contentEquals(formatado)) {

                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    editorData.setText(formatado.toString());
                    editorData.positionCaret(formatado.length());
                });
            }
        });
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

// 2. Coloca o tradutor no DatePicker
        dpIdade.setConverter(new StringConverter<LocalDate>() {

            // Transforma a Data (Objeto) em Texto para mostrar na tela
            @Override
            public String toString(LocalDate data) {
                if (data != null) {
                    return formatoBR.format(data);
                }
                return "";
            }

            // Transforma o Texto digitado no teclado em Data (Objeto)
            @Override
            public LocalDate fromString(String texto) {
                if (texto != null && !texto.trim().isEmpty()) {
                    try {
                        // Tenta ler no formato BR
                        return LocalDate.parse(texto, formatoBR);
                    } catch (Exception e) {
                        // Se o usuário digitar letras ou uma data impossível (32/15/2020),
                        // ele devolve nulo e o DatePicker apaga o erro sozinho!
                        return null;
                    }
                }
                return null;
            }
        });

        Label lblCpf = new Label("Cpf:");
        txtCpf = new TextField();
        txtCpf.setMaxWidth(150);

        txtCpf.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 11 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 11) {
                apenasNumeros = apenasNumeros.substring(0, 11);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 3 || i == 6) {
                    formatado.append(".");
                } else if (i == 9) {
                    formatado.append("-");
                }

                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.contentEquals(formatado)) {

                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    txtCpf.setText(formatado.toString());
                    txtCpf.positionCaret(formatado.length());
                });
            }
        });

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@email.com");
        txtEmail.setMaxWidth(150);

        Label lblDdd = new Label("Ddd:");
        txtDdd = new TextField();
        txtDdd.setMaxWidth(150);
        txtDdd.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

            if (valorNovo.length() > 2) {
                txtDdd.setText(valorAntigo);
            }

            if (!valorNovo.matches("\\d*")) {
                txtDdd.setText(valorNovo.replaceAll("[^\\d]", ""));
            }
        });

        Label lblTelefone = new Label("Telefone:");
        txtTelefone = new TextField();
        txtTelefone.setPromptText("99999-9999");
        txtTelefone.setMaxWidth(150);
        txtTelefone.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 9 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 9) {
                apenasNumeros = apenasNumeros.substring(0, 9);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 5) {
                    formatado.append("-");
                }
                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.contentEquals(formatado)) {

                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    txtTelefone.setText(formatado.toString());
                    txtTelefone.positionCaret(formatado.length());
                });
            }
        });

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
        txtNumero.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

            if (!valorNovo.matches("\\d*")) {
                txtNumero.setText(valorNovo.replaceAll("[^\\d]", ""));
            }
        });

        Label lblCep = new Label("Cep:");
        txtCep = new TextField();
        txtCep.setPromptText("00000-000");
        txtCep.setMaxWidth(150);
        txtCep.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 11 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 8) {
                apenasNumeros = apenasNumeros.substring(0, 8);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 5) {
                    formatado.append("-");
                }

                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.contentEquals(formatado)) {

                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    txtCep.setText(formatado.toString());
                    txtCep.positionCaret(formatado.length());
                });
            }
        });

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
        Accordion sanfonaPets = new Accordion();
        sanfonaPets.setMaxWidth(500);

        // 1. Limpa a sanfona velha
        sanfonaPets.getPanes().clear();

        VBox detalhes = new VBox(5);

        if (c1.getPet().isEmpty()) {
            detalhes.getChildren().add(new Label("Nenhum pet cadastrado."));
        }

        // 2. ORDENA a lista de pets EM ORDEM ALFABÉTICA!
        c1.getPet().sort(Comparator.comparing(Pet::getNome, String.CASE_INSENSITIVE_ORDER));

        // 2. Passa por cada pet no banco de dados
        for (Pet pet : c1.getPet()) {
            // 3. Cria a "caixa" (VBox) que vai ficar ESCONDIDA dentro da sanfona
            // Adiciona um título pros Pets com a numeração
            Label tituloPets = new Label("\n🐾 Pets " + (c1.getPet().indexOf(pet) + 1));
            tituloPets.setStyle("-fx-font-weight: bold;"); // Deixa negrito
            detalhes.getChildren().add(tituloPets);

            // Lista os pets dentro dessa mesma caixa

                //exibe os dados do pet
            detalhes.getChildren().add(new Label(pet.exibePet()));

            // 4. Cria a "Barra" (TitledPane) que fica visível, colocando a VBox dentro dela!
            TitledPane painelCliente = new TitledPane(pet.getNome(), detalhes);
            painelCliente.setMaxHeight(25);

            // 5. Adiciona essa barra na sanfona principal
            sanfonaPets.getPanes().add(painelCliente);

        }
        ScrollPane rolagem = new ScrollPane();
        rolagem.setContent(sanfonaPets);
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
        VBox.setVgrow(rolagem, Priority.ALWAYS);
        VBox.setVgrow(sanfonaPets, Priority.ALWAYS);

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
            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Atenção");
            confirmar.setHeaderText("CANCELAR CADASTRO!!!");
            confirmar.setContentText("TEM CERTEZA QUE QUER CANCELAR?");

            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmar.getButtonTypes().setAll(btnSim, btnNao);

            confirmar.showAndWait().ifPresent(resposta -> {
                if (resposta == btnSim) {
                    App.inserirCena(Home.criarCena(Login.getAtendente()));
                }
            });


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

        btnSalvarTudo.setOnAction(evento -> {
            try {
                dpIdade.setValue(dpIdade.getConverter().fromString(dpIdade.getEditor().getText()));
            } catch (Exception ex) {
                dpIdade.setValue(null); // Se deu erro na conversão, vira nulo para ser barrado no if
            }

            try {
                if (EditarCliente.temCampoVazioEndereco()) {
                    EditarCliente.mostarNaTela("Faltam dados do endereço do Cliente!", "Por favor, volte na aba 'Dados do Cliente' e preencha todos os campos.");
                    log.warn("Faltam dados do endereço do Cliente!");
                    return;
                } else if (EditarCliente.temCampoVazioDados()) {
                    EditarCliente.mostarNaTela("Faltam dados pessoais do Cliente!", "Por favor, volte na aba 'Dados do Cliente' e preencha todos os campos.");
                    log.warn("Faltam dados pessoais do Cliente!");
                    return;
                }

                int idadeNum = LocalDate.now().getYear() - dpIdade.getValue().getYear();

                if (EditarCliente.getTxtTelefone().getLength() < 9 || EditarCliente.getTxtDdd().getLength() < 2) {
                    EditarCliente.mostarNaTela("Quantidade de caracteres do ddd e telefone invalido", "Por favor, preencha o campo ddd e telefone corretamente");
                    log.warn("Quantidade de caracteres do ddd e telefone invalido");
                } else if (idadeNum < 18 || idadeNum > 120) {
                    EditarCliente.mostarNaTela("Idade do cliente invalida", "Por favor, preencha o campo idade corretamente(Apenas maiores de idade podem ter cadastro)");
                    log.warn("Idade do cliente invalida");
                } else if (EditarCliente.getTxtCpf().getLength() < 11) {
                    EditarCliente.mostarNaTela("Quantidade de caracteres do cpf invalido", "Por favor, preencha o campo cpf corretamente");
                    log.warn("Quantidade de caracteres do cpf invalido");
                } else if (EditarCliente.getTxtCep().getLength() < 8) {
                    EditarCliente.mostarNaTela("Quantidade de caracteres do cep invalido", "Por favor, preencha o campo cep corretamente");
                    log.warn("Quantidade de caracteres do cep invalido");
                } else if (EditarCliente.emailInvalido()) {
                    EditarCliente.mostarNaTela("E-mail Inválido", "Por favor, digite um e-mail no formato correto (exemplo@dominio.com).");
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

                    Alert concluido = new Alert(Alert.AlertType.INFORMATION);
                    concluido.setTitle("Cadastro Finalizado");
                    concluido.setHeaderText("Imprimindo Cadastro");

                    concluido.setContentText(exibirDados(c1));
                    concluido.showAndWait();
                    App.inserirCena(Home.criarCena(Login.getAtendente()));

                }
            } catch (IllegalArgumentException | ConstraintViolationException e) {
                log.warn("Erro de validação: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Erro inesperado:", e);
            }
        });

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
        Arrays.asList(txtCpf, dpIdade, txtNome).forEach(e -> e.setDisable(true));
        Arrays.asList(txtCpf, dpIdade, txtNome).forEach(e -> e.getStyleClass().add("naoEditavel"));

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

    public static Cliente pegarDados(List<Pet> p) {
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        String ddd = txtDdd.getText();
        String bairro = txtBairro.getText();
        String rua = txtRua.getText();
        int numero = Integer.parseInt(txtNumero.getText());
        String cep = txtCep.getText();
        String complemento = areaComplemento.getText();
        Endereco e = new Endereco(bairro, rua, numero, cep, complemento);
        Telefone t = new Telefone(ddd, telefone);

        return new Cliente(nome, dpIdade.getValue(), cpf, e, t, email, p);
    }

    public static void setC(Cliente c) {
        EditarCliente.c = c;
    }

    public static void mostarNaTela(String header, String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(header);
        alerta.setContentText(texto);
        alerta.showAndWait();
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
        for (Pet pet : c.getPet()) {
            exibirDados.append("\nPet ").append(c.getPet().indexOf(pet) + 1).append(pet.exibePet());
        }
        return exibirDados.toString();
    }

}
