# Quais testes manter ou desenvolver e como desenvolvê-los.

## Tipos de teste para ser realizados e motivos

#### O objetivo é garantir a segurança da aplicação, validando corretamente:

    - Autenticação
    - Autorização por roles
    - Restrições de acesso aos endpoints

#### Por isso, não devem ser mantidos testes de camada de negócio, como:

    - Repository tests
    - Service tests
    - Testes de casos de uso (test cases tradicionais)
    
( Esses testes devem ser removidos. )

O foco passa a ser somente testes de segurança (Security / Integration Tests).    

#### Foque em testes que validam: 

  - Quem pode acessar o sistema
  - O que cada role pode ou não fazer
  - Se endpoints estão corretamente protegidos

#### Ações que devem ser realizadas
  - Crie um usuário.
  - Teste as roles desse usuario.
  - Teste pelo menos três requests por role. Pode ser um POST(GET, POST, PUT, DELETE).
  - A utilize a role "anonimo" para conectar a um usuario.

#### Ao final, os testes devem garantir que:

  - Cada endpoint exige autenticação quando necessário
  - Cada role possui acesso somente ao que lhe é permitido
  - Usuários anônimos não conseguem acessar recursos protegidos
