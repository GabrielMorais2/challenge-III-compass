# Race Management System

The Race Management System is a robust and scalable application based on microservices architecture, designed to simplify various aspects of race management. Leveraging microservices architecture, API Gateway, and Server Name Load Balancing, this system offers comprehensive functionalities for creating, monitoring, and maintaining race events.

## Features of Each Microservice:

### MS-Cars (Cars Microservice):

- Manages detailed information about vehicles, including make, model, and year.
- Allows the association of drivers with specific cars, ensuring there are no completely identical drivers or cars.
- Offers a single set of CRUD (Create, Read, Update, and Delete) operations for car management, along with pilots.

### MS-Races (Races Microservice):

- Manages the entire lifecycle of race events and tracks.
- For a race to take place, there must be a track.
- Consumes data via Open Feign from MS-Cars and retrieves up to 10 cars.
- Allows random selection of 3 to 10 cars to participate in a race.
- Implements race rules, where a car can only overtake the car in front of it, ensuring fair competition.
- Automatically sends the race result to a RabbitMQ queue, allowing for later analysis and processing.

### MS-History (History Microservice):

- Consumes data from the MS-Races queue, receives race results and saves them in the database.
- Records the date each entry is inserted into the database, ensuring a complete and traceable history of all races conducted.
- Provides access to detailed information about previous race events.

## Technologies Used::

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
  
## Requirements

To install and run the Race Management System, you will need the following requirements:

- Docker
- Docker Compose

## Installation

To install the Race Management System, follow these steps:

- Clone the GitHub repository:

```shel
  git clone https://github.com/GabrielMorais2/challenge-III-compass.git
```

- Navigate to the project directory:

```shel
  cd challenge-III-compass
```

- Execute the docker-compose command to start the system:

```shel
  docker-compose -d up
```

The system will be started at http://localhost:9090/.

## Usage

To use the Race Management System, you will need a valid JWT token. You can obtain a JWT token by registering a user and logging in.

 - To register a user, send a POST request to the /v1/users/register endpoint with the following data:

```json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "password"
}
```

 - To log in, send a POST request to the /v1/users/login endpoint with the following data:

```json
{
  "email": "johndoe@example.com",
  "password": "password"
}
```

The JWT token will be returned in the response header.

Here are some examples of how to use the Race Management System:

- Create a track: To create a race, you need to have a registered track. Register the track by sending a POST request to the /v1/tracks endpoint with the following data:
  
```json
{
    "name": "corrida x",
    "country": "Brasil"
}
```

- Create a Race: With the ID of the created track, you can create a race by sending a POST request to the /v1/races/create endpoint with the following data:

```json
{
    "name": "corrida x",
    "idTrack":"6501b64b1471b22b16eefa4b",
    "date": "2024-08-12"
}
```
Response:

```json
{
    "id": "6501b6541471b22b16eefa4c",
    "name": "teste",
    "status": "CREATED",
    "date": "2024-08-12",
    "track": {
        "id": "6501b64b1471b22b16eefa4b",
        "name": "teste",
        "country": "Brazil"
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
- Start a Race: With the race ID, you can start a race by sending a POST request to the /v1/races/run/{race ID} endpoint. The response will include the list of cars and their positions in the race, along with the race status set to "FINISHED".

```json
{
    "id": "6501c2af1471b22b16eefa4d",
    "name": "teste",
    "status": "FINISHED",
    "date": "2024-08-12",
    "track": {
        "id": "6501b64b1471b22b16eefa4b",
        "name": "teste",
        "country": "Brazil"
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

- History of Finished Races: To access the history of finished races, send a POST request to /v1/history/races. The response will be a list of finished races.

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
                "country": "Brazil"
            },
            "status": "FINISHED"
        }
]
```

Note: When a race is conducted, the MS-Races microservice records the positions of the pilots on each lap and their position changes. By default, the algorithm performs 78 laps, but this can be configured in AppConfig.

Example:

![image](https://github.com/GabrielMorais2/challenge-III-compass/assets/68476116/02e2039b-c6c2-45af-a60e-be3d9bd39a7d)

## Documentação

The API documentation is available through Swagger. To access the documentation, visit [Swagger UI](http://localhost:9090/webjars/swagger-ui/index.html) on your local machine after running the docker-compose for the project. The documentation includes details of all the microservices integrated with the API Gateway.

Exemplo:

![image](https://github.com/GabrielMorais2/challenge-III-compass/assets/68476116/f9203124-9a04-4b1e-b73d-462a903f2a5e)

Note: To access the microservices of MS-Cars, MS-History, and MS-Races, you need to provide a valid JWT token, which can be obtained by registering a user and logging in. The token is available in the Authorization header of the response after login.

## API Endpoint

### URLs:

- Eureka Server - http://localhost:8761/
- Zipkin - http://localhost:9411/zipkin/
- API GATEWAY - http://localhost:9090/ 

### ms-cars

- GET /v1/cars/{id} - Retrieve information about a specific car by its ID.
- GET /v1/cars - Get a list of all available cars.
- GET /v1/cars/limit - Retrieve a list of randomly selected cars, optionally specifying a limit. (Default value is 10)
- POST /v1/cars - Create a new car based on the data provided in the request body.
- PUT /v1/cars/{id} - Update the details of a specific car by its ID.
- DELETE /v1/cars/{id} - Delete a specific car by its ID.
  
### ms-races

Races

- POST /v1/races/run - Start a new race by passing the race ID in the URL. The race will be conducted automatically, and its result will be shown in the response to the request. (After the race is conducted, the result is sent to a Rabbit MQ queue for MS-History consumption.)
- POST /v1/races/create - Create a new race with the data provided in the race request.
- GET /v1/races/ - Get a list of all races conducted (whether finished or created).
- GET /v1/races/{id} - Retrieve information about a specific race by its ID.

Track

- POST /v1/tracks - Create a new track based on the data provided in the request body.
- GET /v1/tracks{id} - Retrieve information about a specific track by its ID.
- GET /v1/tracks - Get a list of all registered tracks.

### ms-history

- GET /v1/history/races/{id} - Retrieve data from a previously conducted race by specifying its ID.
- GET /v1/history/races - Get a list of all historical data of conducted races.

### ms-users

- POST /v1/users/register - Register a user by providing the requested information.
- POST /v1/users/login - Login to the API
  
## Security and Quality

### Tests have a coverage of 75%

![image](https://github.com/GabrielMorais2/challenge-III-compass/assets/68476116/8139e9ff-42b4-4d1b-80e0-e07b0f5d6bfc)

### Security

- In terms of security, the JWT token was used and configured in the API Gateway. The user registers for an account and logs in, and after obtaining the token, they can send requests to the rest of the microservices.

### Code Structure

#### Branch Structure

- At the beginning of the project, fixed branches "main" and "dev" were used, following the pattern for new features [microservice]/[feature]-[what will be done]
- Example: ms-cars/feature-create-car-endpoint

#### Commit Structure

- The commit structure follows the following pattern: prefix(microservice): What was done in the commit.
- Example: feat(ms-cars): Created the POST endpoint for creating cars

## Features not implemented but would be good to implement in the future:

- Implement Circuit Breaker using Resilience4j.
- Implement Pagination and Caching.
- Implement HATEOAS.
