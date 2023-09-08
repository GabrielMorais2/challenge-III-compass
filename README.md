# Races Management System

The Race Management System is a robust and scalable microservices-based application designed to streamline various aspects of race management. Leveraging the power of microservices architecture, API Gateway, and Naming Server Load Balancing, this system offers comprehensive functionalities to create, monitor, and maintain racing events.

## Functionality of Each Microservice:

### MS-Cars (Car Microservice):

- Manages detailed information about vehicles, including brand, model, and year.
- Allows the association of drivers with specific cars, ensuring that there are no entirely identical drivers.
- Ensures that there are no entirely identical cars, avoiding unnecessary redundancies.
- Offers a single set of CRUD (Create, Read, Update, and Delete) operations for car management, preventing data duplication.

### MS-Races (Race Microservice):

- Manages the entire lifecycle of race events.
- For a race to happen, there must be a track.
- Consumes data via Open Feign from ms-Cars and retrieves a maximum of 10 cars.
- Allows the random selection of 3 to 10 cars to participate in a race (as specified in the user request when creating the race).
- Implements race rules, where one car can only overtake the car in front of it, ensuring fair competition.
- Automatically sends the race result to a RabbitMQ queue, enabling later analysis and processing.
  
### MS-History (History Microservice):

- Consumes data from the MS-Races queue, receives race results and saves them in the database.
- Records the date when each entry is inserted into the database, ensuring a complete and traceable history of all races conducted.
- Provides access to detailed information about previous race events.

## Documentation

API documentation is available through Swagger. To access the documentation, please visit [Swagger UI](http://localhost:9090/webjars/swagger-ui/index.html) on your local machine. The documentation includes details for all microservices integrated into the API Gateway.

## API Endpoint

### ms-cars

- GET /v1/cars/{id} - Retrieve information about a specific car by its ID.
- GET /v1/cars - Get a list of all available cars.
- POST /v1/cars - Create a new car based on the data provided in the request body.
- PUT /v1/cars/{id} - Update details of a specific car by its ID.
- DELETE /v1/cars/{id} - Delete a specific car by its ID.
  
### ms-races

- POST /v1/races/start - Start a new race based on the request parameters.
- POST /v1/tracks - Create a new track based on the data provided in the request body.

### ms-history

- GET /v1/history/races/{id} - Retrieve historical data for a specific race by its ID.
- GET /v1/history/races - Get a list of all historical race data.
