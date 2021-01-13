# author Troels (s161791)
# author Daniel (s151641)

mvn -f payment-service/pom.xml -D maven.test.skip=true package
mvn -f token-service/pom.xml -D maven.test.skip=true package
mvn -f account-service/pom.xml -D maven.test.skip=true package
mvn -f report-service/pom.xml -D maven.test.skip=true package
mvn -f client/pom.xml -D maven.test.skip=true package