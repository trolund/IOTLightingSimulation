# 02267 SDWS - Group 11 - dtu-pay

1. [Microservices](#Microservices)
2. [Payment-service](#Payment-service)
3. [Token-service](#Payment-service)
4. [Account-service](#account-service)
5. [Report-service](#report-service)
6. [OpenAPI](#OpenAPI)  
7. [Docker](#Docker)
8. [Contributors](#Contributors)


## Microservices

The microservices are running in the virtual-machine given by DTU Compute. The base URL for the virtual-machine is g-11.compute.dtu.dk. The services
are listening on different ports, as described below. You can access the swagger-ui of each respective service by appending /swagger-ui at the end of the URI.


### Payment-service

The payment service is accessible at g-11.compute.dtu.dk:8080.


### Token-service

The token service is listening on port 8081.


### Account-service

The account service is listening on port 8082.

The account service keeps track of the user accounts that exist in DTUPay.
It interacts with the 3rd party FastMoney BankService who controls the bank
accounts. This service has a RabbitMQ adapter used for internal communication
with other microservices within the system, and a REST adapter used for 
external communication with various mobile applications.


### Report-service

The report service is listening on port 8083.



## OpenAPI

To fetch the OpenAPI specification for the account microservice, you can execute the following CURL script. Change the port to get the specification for the respective service.

```
curl -o openapi-spec.yml g-11.compute.dtu.dk:8082/openapi
```



## Docker





## Contributors

- Tobias Rydberg (s173899)
- Sebastian Lindhard Budsted (s135243)
- Daniel Larsen (s151641)
- Emil Kosiara (s174265)
- Troels Lund (s161791)
- Kasper L. Stilling (s141250)
