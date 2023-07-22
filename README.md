# Spring Boot Airtime Purchase API with JWT Authentication
This is a Spring Boot application that serves as an API for purchasing airtime from an external service. The API uses JWT (JSON Web Tokens) for user authentication to secure the endpoints.

# Requirements
Java 11 or higher
Maven build tool

# Getting Started
git clone https://github.com/dideoluwaseun/airtime-payment.git

# Navigate to the project directory:
cd airtime-payment

# Build the project using Maven
mvn clean install

#Run the Application

The application will start on `http://localhost:8080`.

## API Endpoints
#User Registration

**Endpoint:** `/api/v1/users/sign-up`
**Method:** `POST`

Registers a new user. Requires providing a `username`, `password` and `userRole` in the request body.
User roles available are ROLE_ADMIN and ROLE_USER
Returns access Token




