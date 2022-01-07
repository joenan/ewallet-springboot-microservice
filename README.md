## e-wallet micro service with JWT Oauth2 Authentication and Authorization with Kafka stream-processing between the micro services and Email Notifications with Swagger2 Documentation

## Overview

### The architecture is composed by five services: 

   * [`micro-gateway-service`] **Spring Cloud Gateway** aims to provide a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.

   * [`micro-eureka-service`] This is the Eureka Server which holds the information about all client-service applications. Eureka server knows all the client applications running on each port and IP address. It is also known as Discovery Server. 

   * [`micro-auth-service`] Is an **Authorisation server** which provides REST service for creating user credentials and access_token created with `Spring Boot, Spring Cloud JWT Oauth2, Spring Data JPA, MySQL

   * [`micro-account-service`] A REST service created with `Spring Boot, Spring Data JPA, MySQL, Kafka Producer and swagger for creating and updating user Accounts and Wallet` which is used as a **resource service**

   * [`micro-transaction-service`] A REST service created with `Spring Boot, Spring Data JPA, MySQL, Kafka Producer and swagger for transactions such as Deposit, Withdrawal and Transfer` which is used as a  **resource service**
  
   * [`micro-notification-service`] A REST service created with `Spring Boot, Spring Data JPA, MySQL, Kafka Consumer and swagger for email notifications on Account Opening, as Deposit, Withdrawal and Transfer` which is used as a  **resource service**

### Tools you will need

* Maven 3.x as your build tool
* Your favorite IDE but I will recommend `IntelliJ 2020`.
* MySQL server or MariaDB Server
* JDK 11

### How to run payment microservice?

**Application Running Process**:
First, Download Apache Kafka from [https://kafka.apache.org/downloads]

Secondly, we need to change the default log directory for zookeeper and kafka server Then start Zookeeper Server and then start up Kafka server. Follow this link for installation instruction [https://www.goavega.com/install-apache-kafka-on-windows/]

After Zookeper server and Kafka server is running,

- STEP 1: run `eureka service`
- STEP 2: run `micro-gateway-service`
- STEP 3: run `micro-auth-service`
- Lastly, we need to run `micro-account-service`, `micro-transaction-service` and `micro-notification-service`

**NOTE** we run each of the micro service by navigating to the directory of the each micro service on the terminal/command line using the command `mvn spring-boot:run` or `mvn org.springframework.boot:spring-boot-maven-plugin:run` or run each of micro services directory from your IDE

**AFTER RUNNING EACH MICRO SERVICE**

The micro services can be seen on Eureka Discovery Dashboard using the url below
Eureka Discovery-Service URL: `http://localhost:8761`

![Screenshot from 2020-12-09 10-22-05](https://github.com/joenan/ewallet-springboot-microservice/blob/main/EurekaDiscoveryDashboard.PNG)


## Overview **For micro-auth-service Swagger Documentation**
![Screenshot from 2020-12-09 10-22-05](https://github.com/joenan/ewallet-springboot-microservice/blob/main/AuthSwaggerDoc.PNG)

## Overview **For micro-account-service Swagger Documentation**
![Screenshot from 2020-12-09 10-22-05](https://github.com/joenan/ewallet-springboot-microservice/blob/main/AccountSwaggerDoc.PNG)

## Overview **For micro-transaction-service Swagger Documentation**
![Screenshot from 2020-12-09 10-22-05](https://github.com/joenan/ewallet-springboot-microservice/blob/main/TransactionSwaggerDoc.PNG)

## Overview **For micro-notification-service Swagger Documentation**
![Screenshot from 2020-12-09 10-22-05](https://github.com/joenan/ewallet-springboot-microservice/blob/main/NotificationSwaggerDoc.PNG)

##To create User Roles in  the application,
**Run this query in MYSQL or MariaDB**
```
INSERT INTO roles(NAME) VALUES('ROLE_USER');
INSERT INTO roles(NAME) VALUES('ROLE_ADMIN');
```

##To Create Auth User,
**Send a post request to containing the request body below**
```
http://localhost:8180/auth-api/v1/auth/signup
```

##To obtain access_token,
**Make a HTTP POST Request to**
```
http://localhost:8180/auth-api/v1/auth/signin
```
```
{
  "username": "jadon",
  "password": "jadon"
}
```
```
**The HTTP POST Response will be**
```
```
{
    "id": 2,
    "username": "jadon",
    "email": "jadonphilips@gmail.com",
    "roles": [
        "ROLE_USER"
    ],
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWRvbiIsImlhdCI6MTY0MTI3MzA0OSwiZXhwIjoxNjQxMzU5NDQ5fQ.aFUJxg9qRAcKcjRNBaalNSpGwCQ8XUqpxatoNWaO4gGPMRVY8PyGbHbgZ_TZO28psko0pHZ3ohqYUXW5cgzB7A",
    "tokenType": "Bearer"
}
```
#####The accessToken from the http post response in the payload will be used to access transactions such as fund wallet, withdraw from wallet and transfer from wallet to wallet 


##To create an account on the application,

**Make a HTTP POST Request to**
```
http://localhost:8180/account-api/v1/account/create
```
```
{
    "surname":"Shurle",
    "firstName":"Princess",
    "lastName":"Angelina",
    "email":"angelinashurle@gmail.com",
    "phone":"2348052649901"
}
```

**The HTTP POST Response will be**

```
{
    "responseCode": 201,
    "responseMessage": "Account creation was successful",
    "data": {
        "walletId": 3,
        "walletAddress": "3f56d52f-8b7f-4e27-947c-79f35c1114e6",
        "walletBalance": 0.0,
        "accountId": 3,
        "surname": "Shurle",
        "firstName": "Princess",
        "lastName": "Angelina",
        "email": "angelinashurle@gmail.com",
        "phone": "2348052649901",
        "accountNo": "6315847",
        "date": null
    }
}
```

##TO FUND WALLET ACCOUNT

```
Post the below request body to http://localhost:8180/transaction-api/v1/wallet/fund
```
```
{
    "walletAddress": "b6451434-b25b-4a78-907c-43322f792cf6",
    "amount": 20000
}
```
**And the response will be**

```
{
    "responseCode": 200,
    "responseMessage": "Funding was successful",
    "data": {
        "walletId": 3,
        "walletAddress": "3f56d52f-8b7f-4e27-947c-79f35c1114e6",
        "walletBalance": 20000.0,
        "dateCreated": "2022-01-03T18:08:03",
        "accountId": {
            "accountId": 3,
            "surname": "Shurle",
            "firstName": "Princess",
            "lastName": "Angelina",
            "email": "angelinashurle@gmail.com",
            "phone": "2348052649901",
            "accountNo": "6315847"
        }
    }
}
```

##TO WITHDRAW FROM WALLET ACCOUNT

```
Post the below request body to http://localhost:8180/transaction-api/v1/wallet/withdraw
```
```
{
    "walletAddress": "3f56d52f-8b7f-4e27-947c-79f35c1114e6",
    "amount": 6000
}
```
**And the response will be**

```
{
    "responseCode": 200,
    "responseMessage": "Withdrawal was successful",
    "data": {
        "walletId": 3,
        "walletAddress": "3f56d52f-8b7f-4e27-947c-79f35c1114e6",
        "walletBalance": 14000.0,
        "dateCreated": "2022-01-03T18:08:03",
        "accountId": {
            "accountId": 3,
            "surname": "Shurle",
            "firstName": "Princess",
            "lastName": "Angelina",
            "email": "angelinashurle@gmail.com",
            "phone": "2348052649901",
            "accountNo": "6315847"
        }
    }
}
```

##TO TRANSFER FUNDS FROM ONE WALLET TO WALLET

```
Post the below request body to http://localhost:8180/transaction-api/v1/wallet/transfer
```
```
{
    "sourceWalletAddress": "b6451434-b25b-4a78-907c-43322f792cf6",
    "amount": 15000,
    "destinationWalletAddress":"4b0a0cf7-b605-421e-8a21-fcec645926b0"
}
```
**And the response will be**

```
{
    "responseCode": 200,
    "responseMessage": "Fund transfer was successful"
}
```




