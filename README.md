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
User roles available are ROLE_ADMIN and ROLE_USER.
Returns access Token.

# User Login

**Endpoint:** `/api/v1/users/sign-in`
**Method:** `POST`

Authenticates a user. Requires providing a `username` and `password` as request parameters. Returns a JWT token upon successful authentication.

# Purchase Airtime

**Endpoint:** `/api/v1/airtime-products/purchase`
**Method:** `POST`
**Authorization:** Bearer Token (JWT)

Allows authenticated users to purchase airtime. Requires providing the `phoneNumber`, `amount` and `network provider` in the request body. The request must be authenticated with a valid JWT token.

#JWT Authentication

The API uses JWT for user authentication. To access the authenticated endpoints (`/api/v1/airtime-products/purchase`), clients must include the JWT token in the `Authorization` header of the request. The header should look like: `Authorization: Bearer <JWT_TOKEN>`. The JWT token is obtained by authenticating the user via the `/api/v1/users/sign-in` endpoint.

#External Airtime Purchase API

The API communicates with an external airtime purchase service to process airtime purchases. The external service URL and credentials are configured in the application properties (`application.properties`).




