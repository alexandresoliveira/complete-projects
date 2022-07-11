# blog

## Para iniciar

### Requisitos

- Git 2.24.3
- Java 11
- Maven 3.6.3
- Spring Boot 2.4.4
- NodeJS 14.16.0
- Angular 8
- Docker 20.10.5
- docker-compose 1.28.5

### Passos

```bash
# clone o projeto
$ git clone git@github.com:alexandresoliveira/blog.git

# entre na pasta blog
$ cd blog

# inicie os serviços de banco de dados
$ docker-compose up -d
```

Para iniciar o projeto você pode importar para a sua **_ide_** preferida ou executar o plugin do **_spring-boot_**

- IDE

  Verifique as versões e configurações do maven e jdk na sua IDE.

- Spring Boot plugin

  ```bash
  # inicie o projeto backend
  $ cd blog-backend
  $ mvn spring-boot:run -P dev
  ```

O aplicativo vai estar disponível no endereço [http://localhost:8080](http://localhost:8080).

No mesmo repositório, existe um workspace do [Insomnia](https://insomnia.rest/download) com rotas já definidas para testes e verificação de parâmetros.

Em outro terminal ...

Para iniciar o front-end, siga os commandos

```bash
# entrar na pasta blog-frontend
$ cd blog-frontend

# iniciar a aplicação
$ ng serve
```

O aplicativo vai estar disponível no endereço [http://localhost:4200](http://localhost:4200).

## O que tem

- cadastro de usuário
- login com autenticação/autorização usando jwt
- post
  - criar
  - listar
  - deletar
- comment
  - criar
  - deletar
- album
  - criar
  - listar
  - deletar
  - adicionar fotos
