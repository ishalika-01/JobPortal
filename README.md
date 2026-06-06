# Spring Job Portal Revision JWT

A Spring Boot Job Portal REST API with JWT authentication, Spring Security, BCrypt password hashing, PostgreSQL persistence, centralized exception handling, structured logging, service/repository tests, JaCoCo coverage checks, and GitHub Actions CI.

## Features

- User registration with BCrypt password hashing
- User login with JWT token generation
- Stateless authentication using Spring Security and Bearer tokens
- Protected job CRUD APIs
- Job keyword search by profile or description
- PostgreSQL persistence with Spring Data JPA and Hibernate
- Centralized API error handling with `@RestControllerAdvice`
- Structured-style service logging with Spring AOP
- JUnit 5 and Mockito service-layer tests
- Repository slice tests with `@DataJpaTest` and H2
- JaCoCo service-layer coverage enforcement
- GitHub Actions CI pipeline

## Tech Stack

- Java 21
- Spring Boot 3.3.1
- Spring Web
- Spring Security
- Spring Data JPA / Hibernate
- PostgreSQL
- JWT using `jjwt`
- BCrypt password encoder
- JUnit 5
- Mockito
- H2 for repository tests
- JaCoCo
- GitHub Actions
- Maven

## Project Structure

```text
src/main/java/com/ishalika/SpringJobPortalRevisionJWT
├── config          # Spring Security and JWT filter configuration
├── controllers     # REST controllers
├── exception       # Centralized exception handling
├── model           # JPA entities and UserDetails implementation
├── repo            # Spring Data JPA repositories
├── service         # Business logic and JWT service
└── aop             # Logging, validation, and performance aspects
```

## Configuration

The real `application.properties` file is ignored by Git for security.

Use the example file as a template:

```bash
cp src/main/resources/application.example.properties src/main/resources/application.properties
```

Then update these values locally:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/JobPortal
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
jwt.secret=${JWT_SECRET:REPLACE_WITH_BASE64_SECRET}
jwt.expiration=3600000
```

Generate a strong JWT secret:

```bash
openssl rand -base64 32
```

## Running Locally

Make sure PostgreSQL is running and the `JobPortal` database exists.

Run the application:

```bash
mvn spring-boot:run
```

The API runs on:

```text
http://localhost:8081
```

## API Endpoints

| Method | Endpoint | Auth Required | Description |
| --- | --- | --- | --- |
| POST | `/register` | No | Register a user |
| POST | `/login` | No | Login and receive JWT |
| GET | `/hello` | Yes | Test protected endpoint |
| GET | `/load` | Yes | Load sample jobs |
| GET | `/jobPosts` | Yes | Get all jobs |
| GET | `/jobPost/{postId}` | Yes | Get job by ID |
| GET | `/jobPosts/keyword/{keyword}` | Yes | Search jobs |
| POST | `/jobPost` | Yes | Add job |
| PUT | `/jobPost` | Yes | Update job |
| DELETE | `/jobPost/{postId}` | Yes | Delete job |

## Authentication Flow

1. Register a user through `/register`.
2. Login through `/login` with username and password.
3. Copy the returned JWT token.
4. Send the token with protected API requests:

```text
Authorization: Bearer <token>
```

## Sample Requests

Register:

```json
{
  "id": 1,
  "username": "ishalika",
  "password": "password123"
}
```

Login:

```json
{
  "username": "ishalika",
  "password": "password123"
}
```

Create job:

```json
{
  "postId": 1,
  "postProfile": "Java Developer",
  "postDesc": "Build Spring Boot REST APIs",
  "reqExperience": 2,
  "postTechStack": ["Java", "Spring Boot", "PostgreSQL"]
}
```

## Security

- Passwords are hashed using BCrypt before persistence.
- JWT secret is configurable through `JWT_SECRET`.
- Protected endpoints require a valid Bearer token.
- Sessions are stateless.
- Registration does not return the stored password hash.
- API errors are returned through a centralized error response.

## Testing and Coverage

Run tests:

```bash
mvn clean test
```

The test suite includes:

- Service-layer unit tests using JUnit 5 and Mockito
- Repository slice tests using `@DataJpaTest` and H2
- JaCoCo coverage enforcement for service classes

Coverage report is generated locally at:

```text
target/site/jacoco/index.html
```

## GitHub Actions CI

The workflow is defined in:

```text
.github/workflows/ci.yml
```

It runs automatically on push and pull requests to `main` or `master`.

The CI pipeline:

- checks out the repository
- sets up Java 21
- caches Maven dependencies
- runs `mvn clean test`
- injects `JWT_SECRET` from GitHub repository secrets

Required GitHub secret:

```text
JWT_SECRET
```

Add it in:

```text
Repository Settings > Secrets and variables > Actions > New repository secret
```

## Notes

- `application.properties` is ignored and should not be committed.
- Use `application.example.properties` as the public configuration template.
- OAuth properties exist in the example config, but OAuth login is not enabled in the current Spring Security configuration.
- Docker is not currently included. Add a `Dockerfile` and `docker-compose.yml` if containerized deployment is needed.
