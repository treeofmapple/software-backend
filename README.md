# Guia de Execução: Oferta de Software

Este guia apresenta as instruções simplificadas para configurar e executar o projeto, abrangendo desde testes rápidos de desenvolvimento sem dependências externas até um ambiente de produção completo.

---

## 1. Pré-requisitos

Para trabalhar com o projeto, você precisará de algumas ferramentas básicas:

- **Mise** (Gerenciador de tarefas e ambiente)
    
- **Maven 3**
    
- **Docker** (Necessário apenas para rodar os serviços de banco de dados e o ambiente em contêiner no modo de produção)
    

> **Nota:** Se o seu objetivo for apenas testar a aplicação localmente, você precisará ter instalado apenas o **Mise** (ou o Maven diretamente).

---

## 2. Configuração das Variáveis de Ambiente

O comportamento do sistema muda de acordo com as variáveis configuradas. Para configurar seu ambiente, crie um arquivo chamado `.env` na raiz do projeto, utilizando o arquivo `.env.sample` como base.

### Variáveis para o Modo de Produção:

Se você for rodar o ambiente completo com o banco de dados PostgreSQL, preencha o arquivo com as seguintes configurações:

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

- **DB_HOST:** Utilize `localhost` se for rodar o projeto localmente pelo Mise/Maven. Se for rodar tudo através do Docker, altere para `postgres`.
    
- **Usuário Admin:** O sistema criará um administrador usando `ADMIN_USER`, `ADMIN_EMAIL` e `ADMIN_PASSWORD` na inicialização. Qualquer alteração nessas variáveis criará um novo administrador ao reiniciar o sistema.
    

### Variáveis para o Modo de Teste (Simplificado):

Se você deseja apenas executar o código para testes, **não é necessário** preencher as variáveis de conexão com o banco de dados de produção. Basta configurar o perfil da aplicação:

Snippet de código

```
PROFILE=test
OFERTA_PORT=8000
KEYS_PATH=keys
```

Neste cenário, a aplicação ignora o PostgreSQL externo e sobe um banco em memória local (H2, simulando o modelo do PostgreSQL). Isso garante que o backend e o frontend funcionem normalmente sem a necessidade de contêineres ou configurações avançadas.

---

## 3. Como Executar

O projeto utiliza o arquivo `mise.toml` para padronizar e facilitar a execução de comandos. Escolha a opção que melhor se adapta à sua necessidade no momento:

### Opção A: Execução Rápida (Modo de Teste)

A maneira mais ágil de validar o código. Utilizando o perfil de teste no `.env`, você não precisa se preocupar com o banco de dados.

|**Ação**|**Comando**|**Descrição**|
|---|---|---|
|**Executar o sistema**|`mise run run`|Sobe a aplicação Spring Boot (com banco H2 em memória) na porta especificada.|

### Opção B: Execução Local (Modo de Produção)

Ideal para desenvolver testando a integração com um banco de dados real, rodando a aplicação na sua máquina e o banco via Docker.

|**Ação**|**Comando**|**Descrição**|
|---|---|---|
|**Subir o Banco**|`mise run postgres_up`|Inicia apenas o contêiner do PostgreSQL em segundo plano.|
|**Executar o sistema**|`mise run run`|Sobe a aplicação conectando-se ao PostgreSQL local.|
|**Gerar o Build**|`mise run build`|Compila o projeto sem rodar os testes unitários.|

### Opção C: Execução via Docker (Ambiente Completo)

Utilize estes comandos para rodar toda a infraestrutura (aplicação e banco de dados) isolada em contêineres. Lembre-se de ajustar o `DB_HOST` para `postgres` no `.env`.

|**Ação**|**Comando**|**Descrição**|
|---|---|---|
|**Iniciar o Ambiente**|`mise run docker_run`|Constrói a imagem da aplicação e sobe junto com o banco em segundo plano.|
|**Parar o Ambiente**|`mise run docker_stop`|Encerra os processos e derruba todos os contêineres do Docker Compose.|



