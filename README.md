# java-bank

Um sistema bancário simples feito em Java 17, desenvolvido com foco em estudos de orientação a objetos, camadas de serviço e boas práticas de código.

## Objetivo do projeto

- Simular operações básicas de um banco (criação de contas, depósitos, saques, transferências e histórico de transações).
- Servir como base para evolução contínua conforme meu aprendizado em Java.

## Primeira Versão

### Tecnologias utilizadas

- **Linguagem:** Java 17
- **ORM** Hibernate
- **Database** PostgreSQL
- **Build:** Maven (`pom.xml`)

### Estrutura do projeto

A estrutura de pacotes principal fica em `src/main/java/org/example`

- `controller`: orquestra chamadas entre camada de serviço e camada de modelo.
- `dto`: records responsáveis pelo cuidado com os dados evitando vazamentos desnecessários
- `enums`: tipos enumerados (por exemplo, tipos de conta, tipos de transação).
- `mapper`: mappers responsáveis pelo mapeamento dos dados evitando vazamentos e aumentando funcionalidade geral
- `model`: entidades de domínio do sistema bancário (contas, clientes, transações etc.).
- `repository`: classes responsáveis por armazenar e recuperar dados em memória (simulando um repositório).
- `service`: regras de negócio, validações e operações como saque, depósito e transferência.
- `util`: classe utilizada para inicialização e definição do hibernate
- `view`: interfaces customizadas para a applicação.
- `Application`: classe de inicialização da aplicação (ponto de entrada, setup inicial).

### Funcionalidades (segunda versão)

- Criação e gerenciamento de contas bancárias.
- Operações de depósito, saque e transferência com regras básicas de validação.
- Visão de administrador com controle de transações e contas.
- Banco de dados rodando via docker-compose.
- Utilização de Hibernate + PostgreSQL para banco de dados real.

### Como executar o projeto

1. Clone o repositório:

   ```bash
   git clone https://github.com/AntonioSena0/java-bank.git
   ```
   
2. Acesse o projeto e inicie o banco de dados com o Docker
   ```bash
   docker compose up -d
   ```
   
3. Rode a aplicação através do arquivo Application
   ``` bash
   mvn clean package ; mvn compile exec:java
   ```

### Próximos passos e melhorias:

#### Algumas ideias de evolução para as próximas versões:
- Camada de API REST usando Spring Boot.
- Testes unitários e de integração.
- Tratamento mais robusto de erros e validações.