
# API Veterinária

Projeto desenvolvido para o estudo do conceito de **REST API**, desafio que foi proposto pelo instrutores 
[Michel Menezes](https://www.linkedin.com/in/michelmpin/), [Clécio Silva](https://www.linkedin.com/in/clecio-silva/) e [Ubiratan Passos](https://www.linkedin.com/in/ubiratan-passos-ubione/)
do programa Starter do Grupo GFT.

O projeto tem como base a ideia de um **CRUD** para um sistema de Clínica Veterinária, 
que conta com Animais (Cachorros), Clientes (Tutores), Veterinários, Atendimentos, Usuários e Raças que foram obtidas
a partir de requisições feitas a API do [The Dog API](https://thedogapi.com/), tudo seguindo o padrão REST para a criação de uma **RESTFul API**. 

A API conta com recursos de Criação, Leitura (Total ou por atributos específicos), Atualização, Exclusão, Pesquisa das **264** raças oferecidas pela [The Dog API](https://thedogapi.com/), 
assim como pesquisa de imagens (aleatória ou por ID da raça/imagem) das raças e GIFs aleatórios de cachorros.

O Sistema também possui diversas tecnologias, como:
- **Spring Security** para proteção de dados e acessos via Token JWT da Auth0;
- **Swagger** para documentação da REST API;
- **Caelum Stella** para a verficação da autenticidade de CPF;
- **WebClient** para requisições a APIs externas;
- **JUnit 5** para testes nas classes de Controller, Services e ExceptionsHandler;
- **MySQL Workbench** para sincronização com banco de dados;
- **Spring Data JPA** para administração dos dados com o MySQL Driver;
- **ExceptionsHandlers** para tratamento de erros;
- Outras (Tecnologias e IDE utilizada na aba de "**_Tecnologias Utilizadas_**").
## API REST

É uma interface para a programação de programas que segue os princípios REST(*Representational State Transfer*).

**_Níveis do Rest Glory_**
- **Nível 3**: Hypermedia Controls;
- **Nível 2**: Verbos HTTP;
- **Nível 1**: Recuros;
- **Nível 0**: Pântano do POX.

Leia mais clicando [aqui](https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api).

## Relacionamentos
**_OBERSERVAÇÃO_**: Ao registrar uma nova entidade, caso o registro peça um ID de atendimento/animal/cliente/veterinário você **pode utilizar algum dos ja cadastrados na população automática do banco de dados** ou deixar nulo
para depois na **criação desse associar com o ID da entidade que vc criou com o atributo nulo**.

Para **deixar um atributo nulo**, caso ele peça um **valor único apague toda a linha que pede aquele atributo**, e **se for uma lista [ ], apague o que tiver dentro dela e deixe ela vazia**

**A tentativa de associar um ID a mais em uma entidade que já possui esse espaço ocupado gerará em uma Exception**, por exemplo, tentar adicionar mais um cliente num atendimento que ja possui um cliente colocando o ID desse atendimento no cadastro do cliente.

**_Confira os relacionamento abaixo_**:

Atendimento:

| ENTIDADE RELACIONADA|          RELACIONAMENTO        | ACEITA (ID) |
| ------------------- | -------------------------------|------------ |
| Veterinário         | ManyToOne                      | Um Único    |
| Cliente             | ManyToOne                      | Um Único    |
| Animal              | ManyToOne                      | Um Único    |

Veterinário:

| ENTIDADE RELACIONADA|          RELACIONAMENTO        |  ACEITA (ID) |
| ------------------- | -------------------------------|------------- |
| Atendimento         | OneToMany                      | Vários (List)|

Cliente:

| ENTIDADE RELACIONADA|          RELACIONAMENTO        |  ACEITA (ID) |
| ------------------- | -------------------------------|------------- |
| Atendimento         | OneToMany                      | Vários (List)|
| Animal              | OneToMany                      | Vários (List)|

Animal:

| ENTIDADE RELACIONADA|          RELACIONAMENTO        |  ACEITA (ID) |
| ------------------- | -------------------------------|------------- |
| Cliente             | ManyToOne                      | Um Único     |
| Atendimento         | OneToMany                      | Vários (List)|
| Raça                | ManyToOne                      | Um Único     |

Raça:

| ENTIDADE RELACIONADA|          RELACIONAMENTO        |  ACEITA (ID) |
| ------------------- | -------------------------------|------------- |
| Animal              | ManyToOne                      | Um Único     |

*+ Outros internos*

# "Run" no projeto

Abaixo estão modos para a **inicialização** do projeto:

## Testes (JUnit 5)

Para visualizar os testes realizados e rodar eles, faça o *download* do projeto do modo que desejar e em um projeto **_Spring Starter_**
inicializado na sua IDE, então abra o projeto e localize o seguinte caminho **src > test > java**, caso queira iniciar todos os testes de uma vez, 
aperte com o **botão direito** no pacote "com.gft.veterinario" e então **Run as > JUnit Test**.

Os **testes podem ser inicializados também de forma individual**, acessando o pacote de preferência e realizando o mesmo processo descrito acima na classe desejada.
Classes que possuem testes:
- Controllers (Animal, Atendimento, Cliente, Veterinário);
- Exceptions (CustomRestExceptionHandler);
- Services (Cliente);
- ApiVeterinarioApplication.

## Inicialização Padrão (Spring Boot)

Para iniciar o projeto, faça o *download* do projeto do modo que desejar e em um projeto spring starter
inicializado na sua IDE, com o projeto dentro dele, aperte o botão direito no projeto, e então
**"Run as > Spring Boot App"**.

O projeto possui a biblioteca do **Swagger** que pode ser acessado a partir do localhost iniciado (ex.: http://localhost:8080/swagger-ui.html), onde
estão listadas todas as requisições da API e a possibilidade de realizar elas, que também podem ser feitas através de outros "HTTP clients",
por exemplo, através do **Postman**.

> É necessário a utilização do MySql Workbench para a implementação com o banco de dados e atualize o application.properties em src > main > resources > application.properties com os dados do seu Workbench (usuário, senha e outros caso necessário).

**_ATENÇÃO_**: O projeto possui a implementação do **Spring Security**, sendo necessário que haja a autenticação via usuário e senha, na qual ja possuí
dois cadastrados:
`````
VETERINÁRIO (veterinario)
usuário: veterinarioInicial
senha: 123
___________________________

CLIENTE (cliente)
usuário: clienteInicial
senha: 123
`````
A criação de um novo usuário, sendo ele cliente ou veterinário é realizado automaticamente ao salvar uma nova entidade, sendo o **cpf o usuário junto com a senha fornecida**.

Após a autenticação no endpoint correto, copie o **token gerado no JSON** e abra o menu de "*Authorize*" do swagger, localizado na direita acima das requisições,
então escreva **"Bearer [seu token aqui]"** sem os colchetes, a partir disso as requisições devem estar permitidas nos endpoints abaixo dependendo do cargo do usuário que você se autenticou:
## Endpoints da API

**_OBERSERVAÇÃO_**: Para criar um **animal** (cachorro) como vira-lata, ou seja, **sem raça definida basta definir a sua raça como null**, seguindo os passos antes citado sobre como deixar null. Para saber o ID das demais raças confira o endpoint para buscar todas as raças ou pelo nome dela

**Autenticação**

| MÉTODOS|             TRAJETO            |      FUNÇÃO        |   PERMISSÕES   |
| ------ | -------------------------------|------------------- |--------------- |
| POST   | /v1/autenticacoes              | Gerar Token JWT    |     Todos      |

**Animal**

| MÉTODOS|             TRAJETO                      |              FUNÇÃO             |       PERMISSÕES       |
| ------ | ---------------------------------------- |-------------------------------- | ---------------------- |
| GET    | /v1/animais/                             | Busca Todas os Animais          |   veterinario          |
| POST   | /v1/animais/                             | Salva Animal                    |   veterinario, cliente |
| GET    | /v1/animais/{id}                         | Busca Animal por ID             |   veterinario          |            
| PUT    | /v1/animais/{id}                         | Atualiza Animal por ID          |   veterinario          |
| DELETE | /v1/animais/{id}                         | Deleta Animal por ID            |   veterinario          |
| GET    | /v1/animais/buscaPorCpf                  | Busca Animais por CPF do Cliente|   veterinario, cliente |

**Atendimento**

| MÉTODOS|             TRAJETO                      |                 FUNÇÃO               |       PERMISSÕES       |
| ------ | ---------------------------------------- |------------------------------------- | ---------------------- |
| GET    | /v1/atendimentos/                        | Busca Todas os Atendimentos          |   veterinario          |
| POST   | /v1/atendimentos/                        | Salva Atendimento                    |   veterinario          |
| GET    | /v1/atendimentos/{id}                    | Busca Atendimento por ID             |   veterinario          |            
| PUT    | /v1/atendimentos/{id}                    | Atualiza Atendimento por ID          |   veterinario          |
| DELETE | /v1/atendimentos/{id}                    | Deleta Atendimento por ID            |   veterinario          |
| GET    | /v1/atendimentos/buscaPorCpf             | Busca Atendimentos por CPF do Cliente|   veterinario, cliente |

**Cliente**

| MÉTODOS|             TRAJETO                      |                 FUNÇÃO               |       PERMISSÕES       |
| ------ | ---------------------------------------- |------------------------------------- | ---------------------- |
| GET    | /v1/clientes/                            | Busca Todas os Clientes              |   veterinario          |
| POST   | /v1/clientes/                            | Salva Cliente                        |   Todos                |
| GET    | /v1/clientes/{id}                        | Busca Cliente por ID                 |   veterinario          |            
| PUT    | /v1/clientes/{id}                        | Atualiza Cliente por ID              |   veterinario, cliente |
| DELETE | /v1/clientes/{id}                        | Deleta Cliente por ID                |   veterinario          |
| GET    | /v1/clientes/buscaPorCpf                 | Busca Cliente por CPF do Cliente     |   veterinario, cliente |

**Raça**

| MÉTODOS|             TRAJETO                      |              FUNÇÃO             |   PERMISSÕES   |
| ------ | ---------------------------------------- |-------------------------------- | -------------- |
| GET    | /v1/raca/busca/{id}                      | Busca Raça por ID               |   Autenticados |
| GET    | /v1/raca/busca/porNome                   | Busca Raça por Nome             |   Autenticados |
| GET    | /v1/raca/busca/todas                     | Busca Todas as Raças            |   Autenticados |            
| GET    | /v1/raca/gif/aleatorio                   | Busca Gif Aleatório             |   Todos        |
| GET    | /v1/raca/imagem/aleatoria                | Busca Imagem Aleatória          |   Todos        |
| GET    | /v1/raca/imagem/peloIdRaca               | Busca Imagem por ID da Raça     |   Autenticados |
| GET    | /v1/raca/imagemGif/busca/{id}            | Busca Imagem/Gif por ID da mídia|   Autenticados |

**Usuário**

| MÉTODOS|             TRAJETO                      |                 FUNÇÃO               |       PERMISSÕES       |
| ------ | ---------------------------------------- |------------------------------------- | ---------------------- |
| GET    | /v1/usuarios/                            | Busca Todas os Usuários              |   veterinario          |
| PUT    | /v1/usuarios/                            | Altera Senha do Usuário              |   veterinario, cliente |
| GET    | /v1/usuarios/{id}                        | Busca Usuário por ID                 |   veterinario          |            
| DELETE | /v1/usuarios/{id}                        | Deleta Usuário por ID                |   veterinario          |

**_OBSERVAÇÃO:_** Para mudar a senha deve ser fornecido o CPF utilizado na criação da entidade e a senha antiga, informa então a senha nova
**Veterinário**.

| MÉTODOS|             TRAJETO                      |                   FUNÇÃO                 |       PERMISSÕES       |
| ------ | ---------------------------------------- |----------------------------------------- | ---------------------- |
| GET    | /v1/veterinarios/                        | Busca Todas os Veterinários              |   veterinario          |
| POST   | /v1/veterinarios/                        | Salva Veterinário                        |   veterinario, cliente |
| GET    | /v1/veterinarios/{id}                    | Busca Veterinário por ID                 |   veterinario          |            
| PUT    | /v1/veterinarios/{id}                    | Atualiza Veterinário por ID              |   veterinario, cliente |
| DELETE | /v1/veterinarios/{id}                    | Deleta Veterinário por ID                |   veterinario          |
| GET    | /v1/veterinarios/buscaPorCpf             | Busca Veterinário por CPF do Veterinário |   veterinario, cliente |

## Tecnologias Utilizadas

- [Spring Tools Suite 4 v.4.14.1](https://spring.io/tools);
- [Java 17](https://docs.oracle.com/en/java/);
- [Spring Boot](https://spring.io/projects/spring-boot);
- [Spring Security](https://spring.io/projects/spring-security);
- [Spring Data](https://spring.io/projects/spring-data);
- [Spring Validation](https://www.baeldung.com/spring-boot-bean-validation);
- [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux);
- [Spring Test](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#unit-testing);
- [Spring DevTools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html);
- [MySQL Workbench](https://dev.mysql.com/doc/workbench/en/);
- [Swagger](https://swagger.io/);
- [Caelum Stella](https://github.com/caelum/caelum-stella);
- [WebClient](https://www.baeldung.com/spring-5-webclient);
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/);
- [Auth0 JWT](https://auth0.com/docs/secure/tokens/json-web-tokens);
- Outras.

## Autor e Redes Sociais
Projeto desenvolvido por:

***Guilherme Albani Camargo***
[![github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white
)](https://github.com/GuiACamargo) 
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/guilhermecamargodev/)






