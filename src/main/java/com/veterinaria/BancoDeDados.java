package com.veterinaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static List<Cliente> clientes = new ArrayList<>();
   
    public static void adicionarCliente(Cliente c){
        clientes.add(c);
    }

    public static List<Cliente> getCliente() {
        return clientes;
    }
    public static List<Cliente> getClientes() {
        return clientes;
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
    }

}
