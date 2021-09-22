# Transactions Storage

[![Java CI with Maven](https://github.com/solomkinmv/transactions-storage/actions/workflows/maven.yml/badge.svg)](https://github.com/solomkinmv/transactions-storage/actions/workflows/maven.yml)

## Requirements to run locally

* JDK 16
* Download `jar`
  release [here](https://github.com/solomkinmv/transactions-storage/releases/download/v0.0.1/transactions-storage-0.0.1.jar)

## Local run

`java -jar transactions-storage-{version}.jar` - run with in-memory storage

`java -jar transactions-storage-{version}.jar --audit.file=./audit.txt` - run with in-memory storage and external audit
file for all submitted transactions

## API

### Submit transaction

`curl --location --request POST 'localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data-raw '{
"invoice": 1234567,
"amount": 1299,
"currency": "EUR",
"cardholder": {
"name": "First Last",
"email": "email@domain.com"
},
"card": {
"pan": "4200000000000001",
"expiry": "0624",
"cvv": "789"
} }'`

### Get transaction

`curl --location --request GET 'localhost:8080/transactions/123'` where `123` is invoice id

## TODO

1. Handle repeating invoice ids
2. Cover everything with unit tests
3. Test conditional beans
4. Pack with docker
5. Automate CI to attach releases and push to docker hub