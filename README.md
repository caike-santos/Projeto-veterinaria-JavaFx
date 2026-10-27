# 🐾 Projeto Clínica Veterinária - Módulo Atendente

Aplicação desktop desenvolvida em **Java 21** com **JavaFX** e **Spring Boot**, voltada para o gerenciamento do atendimento em uma clínica veterinária. O sistema contempla o fluxo completo do **Atendente**: autenticação segura, cadastro e edição de clientes e pets, e agendamento de serviços com médicos veterinários e tosadores.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**: Linguagem principal do projeto.
* **JavaFX 21**: Construção programática da interface gráfica (GUI) com estilização via **CSS**.
* **Spring Boot 3.3.5**: Injeção de dependências, gerenciamento de transações e inicialização da aplicação.
* **Spring Data JPA / Hibernate**: Persistência de dados, mapeamento objeto-relacional (ORM), controle de concorrência otimista (`@Version`) e *soft delete* (`@SQLDelete` / `@SQLRestriction`).
* **MySQL**: Banco de dados relacional (`veterinaria_db`), com criação automática do banco caso não exista.
* **Bean Validation (Hibernate Validator)**: Validação declarativa nas entidades e serviços (`@Valid`, `@CPF`, `@Email`, `@NotBlank`, `@Past`, `@FutureOrPresent`, etc.).
* **Spring Security Crypto (BCrypt)**: Criptografia de senhas dos funcionários com `BCryptPasswordEncoder`.
* **Lombok**: Redução de boilerplate (`@Getter`, `@Setter`, `@EqualsAndHashCode`, `@Slf4j`).
* **Maven**: Gerenciamento de dependências e execução do projeto.

---

## ⚙️ Funcionalidades Principais

* **🔐 Autenticação com BCrypt**:
  * Login por usuário e senha validado com hash BCrypt.
  * Controle de acesso por perfil (`Atendente`) e opção de encerramento de sessão (*Deslogar*).
* **🏠 Tela Inicial (Home)**:
  * Busca de clientes por CPF com máscara automática: se o cliente já existir, direciona para a tela de edição; caso contrário, oferece o cadastro imediato.
  * Aba de listagem geral de todos os clientes em ordem alfabética (em lista sanfona/`Accordion`) com seus respectivos dados e pets.
* **👥 Gestão de Clientes**:
  * Cadastro completo com dados pessoais, telefone (`@Embedded`) e endereço (`@Embedded`).
  * Edição de dados cadastrais e visualização centralizada de pets e agendamentos do cliente.
  * Validação de maioridade (18 a 120 anos), CPF único e válido (`@CPF`), CEP e e-mail.
* **🐶 Gestão de Pets**:
  * Vínculo de múltiplos pets por cliente (`@OneToMany`), com nome, data de nascimento, gênero (`enum Genero`) e raça.
  * Adição e remoção de pets diretamente na tela de edição do cliente, com *soft delete* no banco.
* **📅 Gestão de Agendamentos**:
  * Agendamento de serviços (`Consulta Veterinária`, `Vacinação`, `Cirurgia`, `Banho e Tosa`) associando **Cliente**, **Pet**, **Serviço** e **Funcionário** habilitado para a função.
  * Filtro dinâmico de profissionais de acordo com o serviço selecionado (`ComboBox` tipado com `StringConverter`).
  * Validação contra conflitos de horário do cliente e datas/horários inválidos.
  * Histórico de valor (`BigDecimal`) registrado no momento do agendamento.
* **🌱 Carga Inicial de Dados (Seed)**:
  * Executada automaticamente na primeira inicialização ([`SpringApp.java`](src/main/java/com/veterinaria/SpringApp.java)) caso o banco esteja vazio, cadastrando clientes, pets, serviços, atendentes, médicos veterinários, tosador e agendamentos de exemplo.

---

## 🏗️ Arquitetura e Organização de Pacotes

```text
src/main/java/com/veterinaria/
├── App.java                 # Ciclo de vida do JavaFX (Stage, Scene e troca de telas)
├── SpringApp.java           # Configuração @SpringBootApplication, Beans e Seed inicial
├── Main.java                # Ponto de entrada auxiliar
├── model/                   # Entidades JPA, objetos embutidos (@Embeddable) e enums
│   ├── Pessoa.java          # @MappedSuperclass base para Cliente e Funcionario
│   ├── Cliente.java         # Entidade de clientes com lista de Pets
│   ├── Pet.java             # Entidade de pets dos clientes
│   ├── Funcionario.java     # Entidade abstrata (SINGLE_TABLE) com senha BCrypt
│   ├── Atendente.java       # Subclasse de Funcionario
│   ├── Medico.java          # Subclasse de Funcionario (com CRMV)
│   ├── Tosador.java         # Subclasse de Funcionario
│   ├── Servico.java         # Serviços prestados e preço (BigDecimal)
│   ├── Agenda.java          # Agendamentos (Cliente + Pet + Servico + Funcionario)
│   ├── Endereco.java        # @Embeddable de endereço
│   ├── Telefone.java        # @Embeddable de telefone
│   ├── Genero.java          # Enum (Macho, Femea)
│   └── Profissao.java       # Enum (Medico_Veterinario, Tosador, Atendente)
├── repository/              # Interfaces Spring Data JPA
├── service/                 # Regras de negócio, transações (@Transactional) e validações
├── utils/                   # Utilitários reutilizáveis de UI, formatação e tratamento de erros
│   ├── Acoes.java           # Executor centralizado com tratamento de exceções e ConstraintViolation
│   ├── Alertas.java         # Diálogos padronizados (erro, info, confirmar, mostrarViolacoes)
│   ├── Componentes.java     # Componentes JavaFX reutilizáveis (header, listaSanfona, configurarComboBox)
│   ├── Datas.java           # DatePicker padrão BR (dd/MM/yyyy), parseHora e cálculo de idade
│   └── Mascaras.java        # Máscara genérica para TextField (ex.: CPF, CEP, telefone, data, hora)
└── view/                    # Telas construídas programaticamente em JavaFX
```

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* **Java JDK 21** instalado e configurado no `JAVA_HOME`.
* **MySQL Server** rodando localmente na porta `3306` (configurável em [`application.properties`](src/main/resources/application.properties)).
* **Apache Maven** (ou uso do wrapper `./mvnw` incluso no projeto).

### Configuração do Banco de Dados
Por padrão, o arquivo [`src/main/resources/application.properties`](src/main/resources/application.properties) conecta em:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_db?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

### Passos para Execução
No terminal, na raiz do projeto, execute:

```bash
./mvnw clean javafx:run
```

### 🔑 Credenciais de Teste (Seed Inicial)
| Perfil | Usuário | Senha |
| :--- | :--- | :--- |
| **Atendente** | `Carla` | `123` |
| **Atendente** | `Diego` | `123` |
