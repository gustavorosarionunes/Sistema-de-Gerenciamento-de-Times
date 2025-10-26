# Sistema-de-Gerenciamento-de-Times
Java-Spring WEB


Este projeto é focado no desenvolvimento de um sistema web completo utilizando Spring Boot.

O sistema implementa todas as operações de um CRUD (Create, Read, Update, Delete) para gerenciar uma entidade `Time`. A funcionalidade principal inclui o cadastro de times de futebol, permitindo o upload e a atualização dos escudos de cada time.

## Tecnologias Utilizadas

* **Backend:** Java 17 e Spring Boot
    * **Spring Web:** Para a criação dos controllers e da aplicação web.
    * **Spring Data JPA:** Para a persistência de dados e comunicação com o banco.
    * **Hibernate:** Como implementação do JPA (ativado com `spring.jpa.hibernate.ddl-auto=update`).
* **Frontend:** Thymeleaf (para renderização das páginas HTML no lado do servidor).
* **Banco de Dados:** H2 Database (configurado em modo **arquivo**, para que os dados persistam após o reinício da aplicação).
* **Build:** Apache Maven (utilizando o Maven Wrapper `mvnw`).
* **Estilização:** Bootstrap 5 e Bootstrap Icons (via CDN) para um layout responsivo e moderno.

## Funcionalidades Principais

* **Listar Times (`/`):** Página principal que exibe todos os times cadastrados em uma tabela.
* **Cadastrar Time (`/cadastrar`):** Formulário para adicionar um novo time, incluindo o upload obrigatório do arquivo de escudo.
* **Visualizar Time (`/visualizar/{id}`):** Página de detalhes que exibe as informações de um time específico.
* **Editar Time (`/editar/{id}`):** Formulário para alterar os dados de um time. Permite enviar um novo escudo (que substitui e apaga o antigo) ou manter o escudo atual.
* **Excluir Time (`/excluir/{id}`):** Remove um time do banco de dados e também exclui o seu arquivo de escudo da pasta `uploads/`.
* **Persistência de Dados:** O banco de dados (`trabalho-db.mv.db`) e os escudos (`uploads/`) são salvos na raiz do projeto.

---

## Como Executar o Projeto

É necessário ter o **Java 17 (ou superior)** instalado na máquina.

### Opção 1: Pela IDE (Recomendado)

1.  Importe o projeto como um projeto Maven na sua IDE (IntelliJ, VSCode ou Eclipse).
2.  Espere a IDE baixar todas as dependências do Maven.
3.  Encontre o arquivo `src/main/java/com/example/trabalho/TrabalhoAvaliativoApplication.java`.
4.  Clique com o botão direito e execute o método `main()` (ou clique no ícone de "play" ▶).

### Opção 2: Pelo Terminal

1.  Abra um terminal (Prompt de Comando ou PowerShell) na pasta raiz do projeto (onde está o arquivo `pom.xml`).
2.  Execute o seguinte comando:

    ```bash
    # No Windows
    .\mvnw.cmd spring-boot:run
    
    # No Linux ou macOS
    ./mvnw spring-boot:run
    ```

### Acessando o Sistema

Após a inicialização do servidor, acesse os seguintes endereços no seu navegador:

* **Página Principal:** `http://localhost:8080`
* **Console do Banco de Dados:** `http://localhost:8080/h2-console`

---

## Acesso ao Banco de Dados (H2 Console)

Para visualizar os dados diretamente no banco enquanto a aplicação está rodando:

1.  Acesse `http://localhost:8080/h2-console`
2.  Na tela de login, insira as credenciais abaixo (as mesmas do `application.properties`):
    * **Driver Class:** `org.h2.Driver`
    * **JDBC URL:** `jdbc:h2:file:./trabalho-db`
    * **User Name:** `sa`
    * **Password:** `password`
3.  Clique em **"Connect"**.