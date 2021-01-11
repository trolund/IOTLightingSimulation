# 02267 SDWS - Group 11 Microservice template

# NOT YET FINISHED :)

Dependency injection does not seem to work well with tests.
Should IExampleRepository be parametrized for the id or not?
etc.

## Pakke struktur

### domain
Data model.

### dto
Data Transfer Object (DTO).
DTO'er sendes frem og tilbage mellem hvert enkelt microservice og internt mellem hvert lag.

### exceptions
Alle custom exceptions.
Extend WebApplicationException for at ændre indholdet af den http response som bliver sendt. 
(fx. status kode) 

### infrastructure
Alle adapters som skal kommunikere med andre microservices/database/fil-system/osv.

### interfaces
Alle interfaces som skal modtage kommunikation kommende udefra.

#### REST
Alle resources som beskriver REST interfacet.

##### Resource Annotations 
Se evt. link med annotationer som kan bruges på rest resurser:
https://medium.com/@alextheedom/overview-of-jax-rs-part-1-da5289bdffe2

### infrastructure.repositories
Der skal være et repository per model.
Repositoriet står for al interaktion med data.