# 02267 SDWS - Group 11 - dtu-pay

To fetch the OpenAPI specification for a micro service running at port 8082:
```
curl -o openapi-spec.yml g-11.compute.dtu.dk:8082/openapi
```
## Docker


## Microservices

The microservices are running in the VM hosted at DTU Compute. The base URL
for the VM is g-11.compute.dtu.dk.

### Payment service

The payment service is listening on port 8080.

### Token service

The token service is listening on port 8081.

### Account service

The account service is listening on port 8082.

The account service keeps track of the user accounts that exist in DTUPay.
It interacts with the 3rd party FastMoney BankService who controls the bank
accounts. This service has a RabbitMQ adapter used for internal communication
with other microservices within the system, and a REST adapter used for 
external communication with various mobile applications.

### Report service

The report service is listening on port 8083.


## Contributors

- Tobias ..., sXXXXXX
- Sebastian Lindhard Budsted, s13xxxx
- Daniel Larsen, s151641
- Emil Kosiara, s174265
- Troels Lund, s161791
- Kasper L. Stilling, s141250
