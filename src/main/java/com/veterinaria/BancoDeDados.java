package com.veterinaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private static List<Servicos> servicos = new ArrayList<>();
    private static List<Agenda> agendas = new ArrayList<>();
    public static void adicionarCliente(Cliente c){
        clientes.add(c);
    }

    public static List<Cliente> getCliente() {
        return clientes;
    }
    public static List<Cliente> getClientes() {
        return clientes;
    }
    public static List<Agenda> getAgendas() {
        return agendas;
    }
    public static void setAgendas(List<Agenda> agendas) {
        BancoDeDados.agendas = agendas;
    }
    public static List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    public static void setFuncionarios(List<Funcionario> funcionarios) {
        BancoDeDados.funcionarios = funcionarios;
    }
    public static List<Servicos> getServicos() {
        return servicos;
    }
    public static void setServicos(List<Servicos> servicos) {
        BancoDeDados.servicos = servicos;
    }
    public static String exibirClientes(){
         String mostrar = "";
        if(clientes.isEmpty()){
            mostrar = "Nenhum cliente cadastrado";
        }
        for(Cliente cliente : clientes){
            mostrar += "\nCliente "+ (clientes.indexOf(cliente) + 1);
            mostrar += Cadastro.exibirDados(cliente);
        }
        return mostrar;
    }

    public static void carregarDadosDeTeste() {
        
        // --- CLIENTE 1: João da Silva (Com 1 Pet) ---
        Endereço end1 = new Endereço("Centro", "Rua das Flores", 123, "12345-678", "Casa");
        Telefone tel1 = new Telefone("11", "98765-4321"); // Adapte se o seu construtor de Telefone for diferente
        
        List<Pet> petsJoao = new ArrayList<>();
        petsJoao.add(new Pet("Rex", LocalDate.of(2020, 5, 15), "Macho", "Vira-lata"));
        
        Cliente cliente1 = new Cliente("João da Silva", LocalDate.of(1985, 5, 15), "111.222.333-44", end1, tel1, "joao@email.com", petsJoao);
        adicionarCliente(cliente1);


        // --- CLIENTE 2: Maria Oliveira (Com 1 Pet) ---
        Endereço end2 = new Endereço("Jardins", "Avenida Paulista", 1500, "01310-100", "Apto 45");
        Telefone tel2 = new Telefone("21", "99999-8888");
        
        List<Pet> petsMaria = new ArrayList<>();
        petsMaria.add(new Pet("Luna", LocalDate.of(2020, 5, 15), "Fêmea", "Poodle"));
        
        Cliente cliente2 = new Cliente("Maria Oliveira", LocalDate.of(1995, 5, 15), "555.666.777-88", end2, tel2, "maria@email.com", petsMaria);
        adicionarCliente(cliente2);


        // --- CLIENTE 3: Carlos Eduardo (Com 2 Pets!) ---
        Endereço end3 = new Endereço("Copacabana", "Rua Barata Ribeiro", 789, "22040-001", "Bloco B");
        Telefone tel3 = new Telefone("31", "91122-3344");
        
        List<Pet> petsCarlos = new ArrayList<>();
        petsCarlos.add(new Pet("Thor", LocalDate.of(2018, 3, 10), "Macho", "Bulldog"));
        petsCarlos.add(new Pet("Mia", LocalDate.of(2022, 8, 20), "Fêmea", "Gato Persa")); // A lista aceita vários!
        
        Cliente cliente3 = new Cliente("Carlos Eduardo", LocalDate.of(1980, 3, 10), "999.888.777-66", end3, tel3, "carlos@email.com", petsCarlos);
        adicionarCliente(cliente3);

        System.out.println("✅ 3 Clientes de teste carregados com sucesso no Banco de Dados!");

        //Medico 1 - Dr. Ana Paula
        Endereço endMed1 = new Endereço("Centro", "Rua dos Médicos", 456, "12345-678", "Sala 10");
        Telefone telMed1 = new Telefone("11", "91234-5678");
        Medico medico1 = new Medico("Dr. Ana Paula", LocalDate.of(1980, 1, 20), "222.333.444-55", endMed1, telMed1, "ana.paula@email.com", 1, 5000.0f, "Médico Veterinário" , "CMRV12345");
        funcionarios.add(medico1);
        //Medico 2 - Dr. Bruno Silva
        Endereço endMed2 = new Endereço("Jardins", "Avenida dos Veterinários", 789, "01310-100", "Sala 20");
        Telefone telMed2 = new Telefone("21", "99876-5432");
        Medico medico2 = new Medico("Dr. Bruno Silva", LocalDate.of(1975, 6, 15), "333.444.555-66", endMed2, telMed2, "bruno.silva@email.com", 2, 5500.0f, "Médico Veterinário" , "CMRV67890");
        funcionarios.add(medico2);
        //Tosador 1 - Lucas Pereira
        Endereço endTos1 = new Endereço("Copacabana", "Rua dos Tosadores", 321, "22040-001", "Sala 5");
        Telefone telTos1 = new Telefone("31", "98765-4321");
        Tosador tosador1 = new Tosador("Lucas Pereira", LocalDate.of(1990, 4, 10), "444.555.666-77", endTos1, telTos1, "lucas.pereira@email.com", 5, 2500.0f, "Tosador");
        funcionarios.add(tosador1);


        //Atendente 1 - Carla Souza
        Endereço endAtend1 = new Endereço("Copacabana", "Rua dos Atendentes", 321, "22040-001", "Sala 5");
        Telefone telAtend1 = new Telefone("31", "98765-4321");
        Atendente atendente1 = new Atendente("Carla Souza", LocalDate.of(1990, 4, 10), "444.555.666-77", endAtend1, telAtend1, "carla.souza@email.com", 3, 3000.0f, "Atendente");
        funcionarios.add(atendente1);
        //Atendente 2 - Diego Fernandes
        Endereço endAtend2 = new Endereço("Centro", "Rua dos Atendentes", 654, "12345-678", "Sala 6");
        Telefone telAtend2 = new Telefone("11", "97654-3210");
        Atendente atendente2 = new Atendente("Diego Fernandes", LocalDate.of(1992, 9, 5), "555.666.777-88", endAtend2, telAtend2, "diego.fernandes@email.com", 4, 3000.0f, "Atendente");
        funcionarios.add(atendente2);
        //Serviço 1 - Consulta Veterinária
        Servicos servico1 = new Servicos("Consulta Veterinária", 150.0f);
        servicos.add(servico1);
        //Serviço 2 - Vacinação
        Servicos servico2 = new Servicos("Vacinação", 100.0f);
        servicos.add(servico2);
        //Serviço 3 - Cirurgia
        Servicos servico3 = new Servicos("Cirurgia", 500.0f);
        servicos.add(servico3);
        //serviço 4 - Banho e Tosa
        Servicos servico4 = new Servicos("Banho e Tosa", 80.0f);
        servicos.add(servico4);
        System.out.println("✅ 2 Médicos, 2 Atendentes, 1 Tosador e 4 Serviços de teste carregados com sucesso no Banco de Dados!");
        //agendar 3 serviços para os clientes
        Agenda agenda1 = new Agenda(LocalDate.of(2024, 7, 20), LocalDate.of(2024, 7, 20).atTime(10, 0).toLocalTime(), cliente1, servico1, medico1, agendas);
        Agenda agenda2 = new Agenda(LocalDate.of(2024, 7, 21), LocalDate.of(2024, 7, 21).atTime(14, 0).toLocalTime(), cliente2, servico2, medico2, agendas);
        Agenda agenda3 = new Agenda(LocalDate.of(2024, 7, 22), LocalDate.of(2024, 7, 22).atTime(9, 0).toLocalTime(), cliente3, servico4, tosador1, agendas);
        agenda1.adicionarAgenda(agenda1);   
        agenda2.adicionarAgenda(agenda2);
        agenda3.adicionarAgenda(agenda3);
            System.out.println("✅ 3 Agendas de teste carregados com sucesso no Banco de Dados!");

    }

}
