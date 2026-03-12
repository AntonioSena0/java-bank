# java-bank

Um sistema bancário simples feito em Java 17, desenvolvido com foco em estudos de orientação a objetos, camadas de serviço e boas práticas de código.

## Objetivo do projeto

- Simular operações básicas de um banco (criação de contas, depósitos, saques, transferências e histórico de transações).
- Servir como base para evolução contínua conforme meu aprendizado em Java.

## Primeira Versão

### Tecnologias utilizadas

- **Linguagem:** Java 17
- **Build:** Maven (`pom.xml`)

### Estrutura do projeto

A estrutura de pacotes principal fica em `src/main/java/org/example`

- `app`: classes de inicialização da aplicação (ponto de entrada, setup inicial).
- `controller`: orquestra chamadas entre camada de serviço e camada de modelo.
- `enums`: tipos enumerados (por exemplo, tipos de conta, tipos de transação).
- `model`: entidades de domínio do sistema bancário (contas, clientes, transações etc.).
- `repository`: classes responsáveis por armazenar e recuperar dados em memória (simulando um repositório).
- `service`: regras de negócio, validações e operações como saque, depósito e transferência.

### Funcionalidades (primeira versão)

- Criação e gerenciamento de contas bancárias.
- Operações de depósito, saque e transferência com regras básicas de validação.

### Como executar o projeto

1. Certifique-se de ter o Java 17 instalado.
2. Clone o repositório:

   ```bash
   git clone git@github.com:AntonioSena0/java-bank.git
   cd java-bank
   ```
   
3. Compile e rode com Maven
   ```bash
   mvn clean compile
   mvn exec:java
   ```
    Se estiver utilizando uma IDE como IntelliJ, basta importar o projeto Maven e executar a classe principal em `org.example.app`.

### Próximos passos e melhorias:

#### Algumas ideias de evolução para as próximas versões:
- Persistência em banco de dados (PostgreSQL).
- Camada de API REST usando Spring Boot.
- Dockerização da API.
- Autenticação de usuários e segurança.
- Testes unitários e de integração.
- Tratamento mais robusto de erros e validações.