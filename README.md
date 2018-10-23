# odometerrollback-detector
API endpoint for detecting odometer tampering for a given VIN.

## Technologies/Libraries used
1. Java 8
2. Spring Boot
3. Junit & Mockito
4. Maven
5. Lombok
6. Swagger
7. Docker


## Running the application

You need to have Maven and Docker installed on your machine. 
Execute `./start.sh` from the command line.
This will create the jar file, build and run the docker image.

### Testing the API

The API can either be tested in two ways once the server is up and running:
1. Using swagger UI available under
`localhost:8080/swagger-ui.html`

2. Using a curl command
`curl http://localhost:8080/analyze/odometer-rollback?vin=<vin>`

### Result
Vehicle records matching the VIN with `hasOdometerRollback` property set to `true` in case a rollback event is detected. The property is set on the first record on which the rollback event gets detected.
