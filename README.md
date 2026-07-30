# 🐾 Projeto Clínica Veterinária - Módulo Atendente

Este projeto é uma aplicação desktop desenvolvida em **Java** com **JavaFX**, com o objetivo de gerenciar o fluxo de atendimento em uma clínica veterinária. O sistema é focado na visão do **Atendente**, permitindo o controle de clientes, pets, funcionários (médicos e tosadores), além do agendamento de serviços.

## 🚀 Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e ferramentas:

*   **Java 21**: Linguagem de programação principal.
*   **JavaFX 21**: Framework para a construção da Interface Gráfica de Usuário (GUI).
*   **Maven**: Gerenciador de dependências e automação de build.
*   **CSS**: Utilizado para a estilização global das telas (sem a utilização de FXML, as interfaces são construídas de forma programática).

## ⚙️ Funcionalidades Principais

O sistema possui diversas funcionalidades para o gerenciamento da clínica:

*   **🔐 Autenticação**: Tela de Login para acesso seguro ao sistema.
*   **🏠 Dashboard (Home)**: Tela inicial com atalhos para as principais áreas da aplicação.
*   **👥 Gestão de Clientes**:
    *   Cadastro de novos clientes com informações pessoais, endereço e contato.
    *   Exibição da lista de clientes cadastrados.
    *   Edição de informações de clientes existentes.
*   **🐶 Gestão de Pets**:
    *   Cadastro de pets associados aos seus respectivos donos.
    *   Adição e remoção de pets.
*   **📅 Gestão de Agendamentos**:
    *   Agendamento de serviços (Consulta Veterinária, Vacinação, Cirurgia, Banho e Tosa, etc.).
    *   Associação de agendamentos com Clientes, Pets e Funcionários (Médicos ou Tosadores).
    *   Listagem e remoção de agendamentos.
*   **💾 Banco de Dados em Memória**:
    *   O sistema utiliza a classe `BancoDeDados.java` para persistir dados temporariamente em memória através de Coleções Java (`ArrayList`).
    *   Dados de teste pré-carregados para facilitar a validação e testes das funcionalidades (Clientes, Pets, Serviços e Funcionários).

## 🛠️ Como Executar o Projeto

### Pré-requisitos
*   [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) ou superior instalado.
*   [Apache Maven](https://maven.apache.org/download.cgi) instalado e configurado nas variáveis de ambiente.

### Passos para execução
1. Abra o terminal e navegue até o diretório raiz do projeto.
2. Compile e execute o projeto utilizando o plugin do JavaFX pelo Maven com o seguinte comando:

```bash
mvn clean javafx:run
```

## 🏗️ Arquitetura do Projeto

*   **GUI Programática**: Toda a interface de usuário (`Login.java`, `Home.java`, `CadastroCliente.java`, etc.) foi desenvolvida utilizando código Java puro para instanciar e organizar os componentes do JavaFX (`VBox`, `HBox`, `Label`, `TextField`, etc.), garantindo um controle detalhado sobre o layout.
*   **Estilização**: As classes CSS estão centralizadas em `src/main/resources/styles/style.css` para manter o design consistente em todas as cenas.
*   **Separação de Responsabilidades**: Modelos de dados (`Cliente`, `Pet`, `Medico`, `Tosador`, `Agenda`, `Servicos`) estão separados das classes que gerenciam a visualização, mantendo a organização do código.
