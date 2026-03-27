# java-bank

Este repositório contém a nova versão do **java-bank**, agora usando Spring Boot e Docker para evoluir o sistema bancário que comecei em Java puro e Hibernate. O objetivo é transformar esse projeto em uma API REST robusta, escalável e pronta para produção.
## Objetivo do projeto

- Migrar a aplicação legada em Java/Hibernate para Spring Boot.
- Expor operações bancárias via API REST (contas, clientes, transações).
- Containerizar a aplicação com Docker para facilitar desenvolvimento e deploy.
- Praticar boas práticas de arquitetura, testes e documentação.

## Segunda Versão

### Tecnologias utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **Containerização:** Docker e Docker Compose
- **Build:** Maven (`pom.xml`)

### Estrutura do projeto

A estrutura de pacotes principal fica em `src/main/java/dev/java/bank`

- `controller`: orquestra chamadas entre camada de serviço e camada de modelo.
- `dto`: records responsáveis pelo cuidado com os dados evitando vazamentos desnecessários
- `enums`: tipos enumerados (por exemplo, tipos de conta, tipos de transação).
- `mapper`: mappers responsáveis pelo mapeamento dos dados evitando vazamentos e aumentando funcionalidade geral
- `model`: entidades de domínio do sistema bancário (contas, clientes, transações etc.).
- `repository`: classes responsáveis por armazenar e recuperar dados em memória (simulando um repositório).
- `service`: regras de negócio, validações e operações como saque, depósito e transferência.
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
   
2. Acesse o projeto e inicie a aplicação com o Docker
   ```bash
   docker compose up -d
   ```
   
3. Verifique se o container está executando
   - Se não estiver, tente rodar a aplicação manualmente
   ``` bash
   docker ps
   ```

### Próximos passos e melhorias:

#### Algumas ideias de evolução:
- Implementar as entidades e relacionamentos do domínio bancário.
- Criar os primeiros endpoints REST.
- Adicionar testes unitários e de integração.
- Melhorar documentação da API (Swagger/OpenAPI).