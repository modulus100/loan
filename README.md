# Loan Application

Java loan app is based on gradle version 6.6.1 and java version v11. This example
shows how to use the mediator pattern with https://github.com/sizovs/PipelinR library.
.Net version https://github.com/jbogard/MediatR This library is quite useful
when you want to apply CQRS pattern for your api or/and want to get of huge services.

## Run by Gradle ##

```
gradle build
```
```
./gradlew bootRun
```

## Run by Docker ##
```
gradle build
```
```
docker build --tag loan:v1 .
```
```
docker run loan:v1
```
## Check if the service is running ##
 Run by curl or simply by Postman
```
curl --location --request POST 'localhost:8080/approval-request' \
--header 'Content-Type: application/json' \
--data-raw '{ "customerId": "XX-XXXX-XXX", "loanAmount": "1", "approvers": ["test"] }'
```

## Test ##
```
gradle test