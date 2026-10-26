package com.veterinaria;

import com.veterinaria.model.*;
import com.veterinaria.repository.AgendaRepository;
import com.veterinaria.repository.ClienteRepository;
import com.veterinaria.repository.FuncionarioRepository;
import com.veterinaria.repository.ServicoRepository;
import com.veterinaria.view.Login;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class App extends Application {
    private static Stage palcoPrincipal;
    private static ConfigurableApplicationContext springContext;
    @Override
    public void init() {
        // 1. Inicia o Spring Boot ANTES de abrir a tela do JavaFX
        // headless(false) é importante para aplicações Desktop
        springContext = new SpringApplicationBuilder(App.class)
                .headless(false)
                .run();
    }
    @Override
    public void start(Stage stage) {
        // 2. O Spring já está rodando! Agora inicia o JavaFX normalmente
        palcoPrincipal = stage;
        palcoPrincipal.setTitle("Pagina do Atendente");
        inserirCena(Login.criarCena());
        palcoPrincipal.show();
    }
    @Override
    public void stop() {
        // 3. Quando fechar a janela, encerra o Spring Boot e o banco com segurança
        springContext.close();
        Platform.exit();
    }
    // Método auxiliar para pegar qualquer @Service ou @Repository nas suas telas atuais
    public static <T> T getBean(Class<T> classeDoBean) {
        return springContext.getBean(classeDoBean);
    }

    public static void inserirCena(Parent novoLayout){
        if (palcoPrincipal.getScene() != null) {
            
            // Agora sim! Como o novoLayout não tem dono, podemos colocar ele aqui!
            palcoPrincipal.getScene().setRoot(novoLayout);
            
        } else {
            // Se for a primeira vez (Tela de Login)
            Scene cenaInicial = new Scene(novoLayout, 1200, 800);
            
            // Aplica o CSS global na nossa única cena do aplicativo
            String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            cenaInicial.getStylesheets().add(cssGlobal);
            
            palcoPrincipal.setScene(cenaInicial);
            
            // Maximiza a janela para sempre
            //palcoPrincipal.setMaximized(true);
        }
    }
    

    public static void main(String[] args) {
        launch();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner carregarDadosDeTeste(
            ClienteRepository clienteRepository,
            ServicoRepository servicoRepository,
            FuncionarioRepository funcionarioRepository,
            AgendaRepository agendaRepository,
            PasswordEncoder passwordEncoder,
            TransactionTemplate transactionTemplate
    ) {
        return args -> transactionTemplate.executeWithoutResult(status -> {
                    // Só insere os dados de teste se o banco estiver vazio (roda apenas 1 vez!)
                    if (servicoRepository.count() > 0 || funcionarioRepository.count() > 0 || clienteRepository.count() > 0) {
                        System.out.println("ℹ️ Dados de teste já existem no banco de dados. Pulando carga inicial.");
                        return;
                    }

                    // --- CLIENTE 1: João da Silva (Com 1 Pet) ---
                    Endereco end1 = new Endereco("Centro", "Rua das Flores", 123, "12345-678", "Casa");
                    Telefone tel1 = new Telefone("11", "98765-4321");
                    List<Pet> petsJoao = new ArrayList<>();
                    petsJoao.add(new Pet("Rex", LocalDate.of(2020, 5, 15), Genero.Macho, "Vira-lata"));
                    Cliente cliente1 = clienteRepository.save(new Cliente(
                            "João da Silva", LocalDate.of(1985, 5, 15), "529.982.247-25", end1, tel1, "joao@email.com", petsJoao
                    ));

                    // --- CLIENTE 2: Maria Oliveira (Com 1 Pet) ---
                    Endereco end2 = new Endereco("Jardins", "Avenida Paulista", 1500, "01310-100", "Apto 45");
                    Telefone tel2 = new Telefone("21", "99999-8888");
                    List<Pet> petsMaria = new ArrayList<>();
                    petsMaria.add(new Pet("Luna", LocalDate.of(2020, 5, 15), Genero.Femea, "Poodle"));
                    Cliente cliente2 = clienteRepository.save(new Cliente(
                            "Maria Oliveira", LocalDate.of(1995, 5, 15), "123.456.789-09", end2, tel2, "maria@email.com", petsMaria
                    ));

                    // --- CLIENTE 3: Carlos Eduardo (Com 2 Pets) ---
                    Endereco end3 = new Endereco("Copacabana", "Rua Barata Ribeiro", 789, "22040-001", "Bloco B");
                    Telefone tel3 = new Telefone("31", "91122-3344");
                    List<Pet> petsCarlos = new ArrayList<>();
                    petsCarlos.add(new Pet("Thor", LocalDate.of(2018, 3, 10), Genero.Macho, "Bulldog"));
                    petsCarlos.add(new Pet("Mia", LocalDate.of(2022, 8, 20), Genero.Femea, "Gato Persa"));
                    Cliente cliente3 = clienteRepository.save(new Cliente(
                            "Carlos Eduardo", LocalDate.of(1980, 3, 10), "987.654.321-00", end3, tel3, "carlos@email.com", petsCarlos
                    ));
                    System.out.println("✅ 3 Clientes de teste salvos no Banco de Dados!");

                    // --- SERVIÇOS ---
                    Servico servico1 = servicoRepository.save(new Servico("Consulta Veterinária", 150.0f, Profissao.Medico_Veterinario));
                    Servico servico2 = servicoRepository.save(new Servico("Vacinação", 100.0f, Profissao.Medico_Veterinario));
                    Servico servico3 = servicoRepository.save(new Servico("Cirurgia", 500.0f, Profissao.Medico_Veterinario));
                    Servico servico4 = servicoRepository.save(new Servico("Banho e Tosa", 80.0f, Profissao.Tosador));
                    Servico servico5 = servicoRepository.save(new Servico("Agendamento", 0.0f, Profissao.Atendente));

                    // --- FUNCIONÁRIOS ---
                    Endereco endAtend1 = new Endereco("Copacabana", "Rua dos Atendentes", 321, "22040-001", "Sala 5");
                    Telefone telAtend1 = new Telefone("31", "98765-4321");
                    Atendente atendente1 = new Atendente("Carla Souza", LocalDate.of(1990, 4, 10), "111.444.777-35", endAtend1, telAtend1, "carla.souza@email.com", null, 3000.0f, Arrays.asList(servico5), "Carla", "123");
                    funcionarioRepository.save(atendente1);

                    Endereco endAtend2 = new Endereco("Centro", "Rua dos Atendentes", 654, "12345-678", "Sala 6");
                    Telefone telAtend2 = new Telefone("11", "97654-3210");
                    Atendente atendente2 = new Atendente("Diego Fernandes", LocalDate.of(1992, 9, 5), "222.555.888-46", endAtend2, telAtend2, "diego.fernandes@email.com", null, 3000.0f, Arrays.asList(servico5), "Diego", "123");
                    funcionarioRepository.save(atendente2);

                    Endereco endMed1 = new Endereco("Centro", "Rua dos Médicos", 456, "12345-678", "Sala 10");
                    Telefone telMed1 = new Telefone("11", "91234-5678");
                    Medico medico1 = new Medico("Dr. Ana Paula", LocalDate.of(1980, 1, 20), "333.666.999-57", endMed1, telMed1, "ana.paula@email.com", null, 5000.0f, Arrays.asList(servico1, servico2, servico3), "CMRV12345", "Ana", "156");
                    medico1 = funcionarioRepository.save(medico1);

                    Endereco endMed2 = new Endereco("Jardins", "Avenida dos Veterinários", 789, "01310-100", "Sala 20");
                    Telefone telMed2 = new Telefone("21", "99876-5432");
                    Medico medico2 = new Medico("Dr. Bruno Silva", LocalDate.of(1975, 6, 15), "444.777.111-07", endMed2, telMed2, "bruno.silva@email.com", null, 5500.0f, Arrays.asList(servico2, servico3), "CMRV67890", "Bruno", "489");
                    medico2 = funcionarioRepository.save(medico2);

                    Endereco endTos1 = new Endereco("Copacabana", "Rua dos Tosadores", 321, "22040-001", "Sala 5");
                    Telefone telTos1 = new Telefone("31", "98765-4321");
                    Tosador tosador1 = new Tosador("Lucas Pereira", LocalDate.of(1990, 4, 10), "777.888.999-41", endTos1, telTos1, "lucas.pereira@email.com", null, 2500.0f, Arrays.asList(servico4), "Lucas", "255");
                    tosador1 = funcionarioRepository.save(tosador1);
                    System.out.println("✅ 2 Médicos, 2 Atendentes, 1 Tosador e 5 Serviços de teste salvos no Banco de Dados!");

                    // --- AGENDAS ---
                    Agenda agenda1 = new Agenda(LocalDate.now().plusDays(1), LocalTime.of(10, 0), cliente1, petsJoao.getFirst(), servico1, medico1);
                    Agenda agenda2 = new Agenda(LocalDate.now().plusDays(2), LocalTime.of(14, 0), cliente2, petsMaria.getFirst(), servico2, medico2);
                    Agenda agenda3 = new Agenda(LocalDate.now().plusDays(3), LocalTime.of(9, 0), cliente3, petsCarlos.getFirst(), servico4, tosador1);
                    agendaRepository.saveAll(Arrays.asList(agenda1, agenda2, agenda3));
                    System.out.println("✅ 3 Agendas de teste salvas no Banco de Dados!");
                }
        );
    }
}
