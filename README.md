# Sistema de Gerenciamento de Corridas

O Sistema de Gerenciamento de Corridas é uma aplicação robusta e escalável baseada em microservices, projetada para simplificar vários aspectos do gerenciamento de corridas. Aproveitando a arquitetura de microservices, o Gateway de API e o Balanceamento de Carga do Servidor de Nomes, este sistema oferece funcionalidades abrangentes para criar, monitorar e manter eventos de corrida.

## Caracteristicas de Cada Microservice:

### MS-Cars (Microserviço de Carros):

- Gerencia informações detalhadas sobre veículos, incluindo marca, modelo e ano.
- Permite a associação de motoristas a carros específicos, garantindo que não haja motoristas completamente idênticos e nem carros completamente idênticos.
- Oferece um único conjunto de operações CRUD (Criar, Ler, Atualizar e Excluir) para o gerenciamento de carros, com os pilotos.

### MS-Races (Microserviço de Corridas):

- Gerencia todo o ciclo de vida de eventos de corrida e de pistas.
- Para uma corrida acontecer, deve haver uma pista.
- Consome dados via Open Feign de ms-Cars e recupera no máximo 10 carros.
- Permite a seleção aleatória de 3 a 10 carros para participar de uma corrida.
- Implementa regras de corrida, onde um carro só pode ultrapassar o carro à sua frente, garantindo uma competição justa.
- Envia automaticamente o resultado da corrida para uma fila RabbitMQ, permitindo análise e processamento posterior.

### MS-History (Microserviço de Histórico):

- Consome dados da fila MS-Races, recebe resultados de corrida e os salva no banco de dados.
- Registra a data em que cada entrada é inserida no banco de dados, garantindo um histórico completo e rastreável de todas as corridas realizadas.
- Fornece acesso a informações detalhadas sobre eventos de corrida anteriores.

## Tecnologias Utilizadas:

* Java 17
* Spring Boot 3.1.3
* MongoDB
* Postgres
* Coverage
* OpenFeign
* RabbitMQ
* Docker
* Docker-Compose
* API Gateway
* Eureka Server
* ZipKin - (Sistema de rastreamento distribuído)
* Spring Security
  
## Requisitos

Para instalar e executar o Sistema de Gerenciamento de Corridas, você precisará dos seguintes requisitos:

- Docker
- Docker Compose

## Instalação

Para instalar o Sistema de Gerenciamento de Corridas, siga estas etapas:

- Clone o repositório do GitHub:

```shel
  git clone https://github.com/GabrielMorais2/challenge-III-compass.git
```

- Entre no diretório do projeto:

```shel
  cd challenge-III-compass
```

- Execute o docker-compose para iniciar o sistema:

```shel
  docker-compose -d up
```

O sistema será iniciado em http://localhost:9090/.

## Uso

Para usar o Sistema de Gerenciamento de Corridas, você precisará de um token JWT válido. Você pode obter um token JWT registrando um usuário e fazendo o login.

 - Para registrar um usuário, envie uma solicitação POST para o endpoint /v1/users/register com os seguintes dados:

```json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "password"
}
```

 - Para fazer login, envie uma solicitação POST para o endpoint /v1/users/login com os seguintes dados:

```json
{
  "email": "johndoe@example.com",
  "password": "password"
}
```

O token JWT será retornado no header da resposta.

Aqui estão alguns exemplos de como usar o Sistema de Gerenciamento de Corridas:

- Criar uma pista: Para criar uma race, você precisa ter um track registrado. Registre o track enviando uma solicitação POST para o endpoint /v1/tracks com os seguintes dados:
- 
```json
{
    "name": "corrida x",
    "country": "Brasil"
}
```

- Criar uma Race: Com o ID do track criado, você pode criar uma race enviando uma solicitação POST para o endpoint /v1/races/create com os seguintes dados:

```json
{
    "name": "corrida x",
    "idTrack":"6501b64b1471b22b16eefa4b",
    "date": "2024-08-12"
}
```
Resposta:

```json
{
    "id": "6501b6541471b22b16eefa4c",
    "name": "teste",
    "status": "CREATED",
    "date": "2024-08-12",
    "track": {
        "id": "6501b64b1471b22b16eefa4b",
        "name": "teste",
        "country": "Brasil"
    },
    "cars": [
        {
            "id": 12,
            "brand": "Mazda",
            "model": "CX-5",
            "pilot": {
                "name": "Ava Hall",
                "age": 25
            },
            "year": "2022",
            "position": 0
        },
        {
            "id": 1,
            "brand": "Toyota",
            "model": "Corolla",
            "pilot": {
                "name": "John Doe",
                "age": 35
            },
            "year": "2022",
            "position": 0
        },
        {
            "id": 8,
            "brand": "Audi",
            "model": "A4",
            "pilot": {
                "name": "Sophia White",
                "age": 28
            },
            "year": "2022",
            "position": 0
        }
    ]
}
```
- Iniciar uma Corrida: Com o ID da corrida, você pode iniciar uma corrida enviando uma solicitação POST para o endpoint /v1/races/run/{id da corrida}. A resposta incluirá a lista de carros e suas posições na corrida, além da corrida com o status "FINISHED".

```json
{
    "id": "6501c2af1471b22b16eefa4d",
    "name": "teste",
    "status": "FINISHED",
    "date": "2024-08-12",
    "track": {
        "id": "6501b64b1471b22b16eefa4b",
        "name": "teste",
        "country": "Brasil"
    },
    "cars": [
        {
            "id": 8,
            "brand": "Audi",
            "model": "A4",
            "pilot": {
                "name": "Sophia White",
                "age": 28
            },
            "year": "2022",
            "position": 3
        },
        {
            "id": 12,
            "brand": "Mazda",
            "model": "CX-5",
            "pilot": {
                "name": "Ava Hall",
                "age": 25
            },
            "year": "2022",
            "position": 2
        },
        {
            "id": 1,
            "brand": "Toyota",
            "model": "Corolla",
            "pilot": {
                "name": "John Doe",
                "age": 35
            },
            "year": "2022",
            "position": 1
        }
    ]
}
```

- Histórico de Corridas Finalizadas: Para acessar o histórico de corridas finalizadas, envie uma solicitação POST para /v1/history/races. A resposta será uma lista das corridas finalizadas.

```json
[
  {
        "id": "6501c2c4b7a23d779397fffd",
        "createdAt": "2023-09-13",
        "raceResult": {
            "name": "teste",
            "cars": [
                {
                    "id": 8,
                    "brand": "Audi",
                    "model": "A4",
                    "pilot": {
                        "name": "Sophia White",
                        "age": 28
                    },
                    "year": "2022",
                    "position": 3
                },
                {
                    "id": 5,
                    "brand": "Volkswagen",
                    "model": "Golf",
                    "pilot": {
                        "name": "Michael Wilson",
                        "age": 26
                    },
                    "year": "2022",
                    "position": 2
                },
                {
                     "id": 1,
                     "brand": "Toyota",
                     "model": "Corolla",
                     "pilot": {
                          "name": "John Doe",
                          "age": 35
                     },
                     "year": "2022",
                    "position": 1
                }
            ],
            "dateRace": "2024-08-12",
            "track": {
                "id": "6501b64b1471b22b16eefa4b",
                "name": "teste",
                "country": "Brasil"
            },
            "status": "FINISHED"
        }
]
```

Nota: Quando uma corrida é realizada, o microservice de ms-races registra as posições dos pilotos em cada volta e suas trocas de posições. Por padrão, o algoritmo realiza 78 voltas, mas isso pode ser configurado no AppConfig.

Exemplo:

![image](https://github.com/GabrielMorais2/challenge-III-compass/assets/68476116/02e2039b-c6c2-45af-a60e-be3d9bd39a7d)

## Documentação

A documentação da API está disponível por meio do Swagger. Para acessar a documentação, visite [Swagger UI](http://localhost:9090/webjars/swagger-ui/index.html) em sua máquina local após realizar o docker-compose do projeto. A documentação inclui detalhes de todos os microservices integrados ao Api Gateway.

Nota: Para acessar os microservices de ms-cars, ms-history, ms-races, você precisa fornecer um token JWT válido, que pode ser obtido registrando um usuário e fazendo o login. O token é disponibilizado no header Authorization da resposta após o login.

Exemplo:

![image](https://github.com/GabrielMorais2/challenge-III-compass/assets/68476116/f9203124-9a04-4b1e-b73d-462a903f2a5e)

## Endpoint da API

### URLs:

- Eureka Server - http://localhost:8761/
- Zipkin - http://localhost:9411/zipkin/
- API GATEWAY - http://localhost:9090/ 

### ms-cars

- GET /v1/cars/{id} - Recupere informações sobre um carro específico pelo seu ID.
- GET /v1/cars - Obtenha uma lista de todos os carros disponíveis.
- GET /v1/cars/limit - Recuperar uma lista de carros selecionados aleatoriamente, especificando opcionalmente um limite. (O valor default é 10)
- POST /v1/cars - Crie um novo carro com base nos dados fornecidos no corpo da solicitação.
- PUT /v1/cars/{id} - Atualize os detalhes de um carro específico pelo seu ID.
- DELETE /v1/cars/{id} - Exclua um carro específico pelo seu ID.
  
### ms-races

Races

- POST /v1/races/run - Inicie uma nova corrida passando o ID da corrida na URL. A corrida será realizada automaticamente e seu resultado será mostrado na resposta da requisição. (Após realizado a corrida, o resultado é enviado para uma fila Rabbit MQ para o MS-history consumir.)
- POST /v1/races/create - Crie uma nova corrida com os dados fornecidos na solicitação de corrida.
- GET /v1/races/ -  Obtenha uma lista de todos as corridas realizadas (Seja finalizar ou criada)
- GET /v1/races/{id} - Recupere informações sobre uma corrida específica pelo seu ID.

Track

- POST /v1/tracks - Crie uma nova pista com base nos dados fornecidos no corpo da solicitação.
- GET /v1/tracks{id} - Recupere informações sobre uma pista específica pelo seu ID.
- GET /v1/tracks - Obtenha uma lista de todos as pistas cadastradas.

### ms-history

- GET /v1/history/races/{id} - Recupere dados de uma corrida ja realizada especificando o seu ID.
- GET /v1/history/races - Obtenha uma lista de todos os dados históricos de corrida realizadas.

### ms-users

- POST /v1/users/register - Registre um usuario passando as informações solicitadas.
- POST /v1/users/login - Realize o login de um usuário.
  

## Segurança e Qualidade

### Testes contam com cobertura de 63%

![image](https://github.com/GabrielMorais2/challenge-III-compass/assets/68476116/8139e9ff-42b4-4d1b-80e0-e07b0f5d6bfc)

### Segurança

- Na segurança, foi utilizado o JWT token configurado no API Gateway. O Usuario realiza o registro do cadastro e login, e após ter o token, pode enviar as requições para o restante dos microservices.

### Estrutura de codigo

#### estrutura da branch 

- No início do projeto foram utilizadas as branches fixas "main" e "dev", seguindo o padrão para novas features [microservice]/[feature]-[o que será feito]

- Exemplo: ms-cars/feature-

#### estrutura do commit

- A estrutura de commits, segue o seguinte padrão: prefixo(microsserviço): O que foi feito no commit.

- Exemplo: feat(ms-cars)- criado o end point POST

## Funcionalidades que não foram criadas, mas que seria bom implementar posteriormente:

- Implementar Circuit Breaker utilizando o Resilience4j.
- Implementar Paginação e Cache
- Implementação de HATEOAS.
