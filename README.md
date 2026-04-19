# Guia de Execução: Oferta de Software

Este guia apresenta as instruções simplificadas para configurar e executar o projeto, seja para testes rápidos de desenvolvimento ou para um ambiente de produção completo.

---

## 1. Pré-requisitos

Para rodar o ambiente completo da aplicação, você precisará das seguintes ferramentas:

- **Mise** (Gerenciador de tarefas e ambiente)
    
- **Maven 3**
    
- **Docker** (Necessário para os serviços de banco de dados e ambiente em contêiner)
    

> **Nota:** Se o seu objetivo for apenas testar a aplicação de forma rápida e local, você precisará ter instalado apenas o **Mise** (ou o Maven diretamente).

---

## 2. Configuração das Variáveis de Ambiente

Antes de executar o projeto, você deve configurar suas variáveis de ambiente. Crie um arquivo chamado `.env` na raiz do projeto (utilizando as propriedades do arquivo `.env.sample` como base) e preencha com as configurações abaixo:

Snippet de código

```
PROFILE=prod
OFERTA_PORT=8000
DB_HOST=localhost 
DB_PORT=5432
DB_DATABASE=postgres
DB_USER=postgres
DB_PASSWORD=postgres
KEYS_PATH=keys
ALLOWED_ORIGINS=http://localhost:4200
ADMIN_USER=admin
ADMIN_EMAIL=admin@admin.com
ADMIN_PASSWORD=admin
```

### Entendendo as Variáveis:

- **DB_HOST:** Define o endereço do banco de dados. Utilize `localhost` se for rodar o projeto localmente pelo Mise/Maven. Se for rodar a aplicação através do Docker, altere para `postgres`.
    
- **OFERTA_PORT:** É a porta na qual o serviço do backend será executado.
    
- **KEYS_PATH:** Diretório onde o sistema criará ou buscará as chaves criptográficas usadas para validar os tokens de acesso de usuários.
    
- **Usuário Admin:** Toda vez que o sistema iniciar, ele criará um usuário administrador usando os valores definidos em `ADMIN_USER`, `ADMIN_EMAIL` e `ADMIN_PASSWORD`. Se você alterar esses valores no `.env` e reiniciar o sistema, um novo administrador será criado no banco.
    
- **Banco de Dados de Teste:** O sistema pode ser executado em modo de teste. Nesse modo, ele ignora o PostgreSQL externo e sobe um banco em memória (H2 simulando o modelo do PostgreSQL), garantindo que o frontend continue operando normalmente para testes rápidos.
    

---

## 3. Como Executar

O projeto utiliza o arquivo `mise.toml` para padronizar e facilitar a execução de comandos. Você pode escolher rodar a aplicação de duas formas principais:

### Opção A: Execução Local (Testes e Desenvolvimento)

Esta é a maneira mais rápida de rodar e testar o código da aplicação de forma independente, utilizando apenas o Maven por baixo dos panos.

|**Ação**|**Comando**|**Descrição**|
|---|---|---|
|**Executar o sistema**|`mise run run`|Sobe a aplicação Spring Boot na porta especificada.|
|**Gerar o Build**|`mise run build`|Compila o projeto sem rodar os testes unitários.|

### Opção B: Execução via Docker (Produção ou Ambiente Completo)

Utilize estes comandos para rodar a aplicação junto com o banco de dados em contêineres.

|**Ação**|**Comando**|**Descrição**|
|---|---|---|
|**Iniciar o Ambiente**|`mise run docker_run`|Constrói a imagem e sobe a aplicação e o banco em segundo plano.|
|**Parar o Ambiente**|`mise run docker_stop`|Encerra os processos e derruba os contêineres do Docker.|
