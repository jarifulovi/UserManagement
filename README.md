# User Management System

A robust Spring Boot application for managing users and roles with a clean architecture approach.

## Features

- User Management (CRUD operations)
- Role-based Access Control
- RESTful API endpoints
- Data validation
- Transaction management
- Clean Architecture implementation
- H2 in-memory database

## Tech Stack

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Jakarta Validation
- H2 Database
- Lombok
- Maven 3.x
- JUnit 5 (for testing)

## Prerequisites

Before you begin, ensure you have the following installed:
- JDK 21 or later
- Maven 3.x or later
- Git (for version control)

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd UserManagement
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   
   The application will start on `http://localhost:8080`

4. **Access H2 Console** (Development only)
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:userdb`
   - Username: `sa`
   - Password: [empty]

## Database Configuration

The application uses H2 in-memory database with the following configuration:
- Database URL: `jdbc:h2:mem:userdb`
- Username: sa
- Password: [empty]

You can modify these settings in `application.properties`.

## Testing

The project includes unit tests and integration tests. To run the tests: