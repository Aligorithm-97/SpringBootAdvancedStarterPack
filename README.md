# Spring Boot Advanced Starter Pack

This repository serves as an advanced Spring Boot starter pack, designed to streamline development with pre-configured
integrations for database connections, Spring Security with JWT, email services, and Aspect-Oriented Programming (AOP)
for logging. Developers can quickly adapt this project with minimal configuration adjustments to meet their needs.

---

## Key Features

1. **Database Integration**
    - **PostgreSQL**: Pre-configured in `application-dev.properties`. Simply install PostgreSQL, create the database
      using pgAdmin, and update properties such as `port`, `database name`, and `credentials` as needed.
    - **SQL Server**: Configuration is available for SQL Server. Update the necessary details and include the required
      dependencies in `build.gradle` to switch to SQL Server.

2. **Spring Security with JWT**
    - Includes JSON Web Token (JWT) authentication for securing APIs.
    - **CORS Configuration**:
        - Development: Allow origins like `localhost` and specific ports.
        - Production: Set allowed origins to your domain or IP in the `BeansConfig` class.

3. **Email Integration**
    - Pre-configured for email sending using Gmail.
    - **Setup Instructions**:
        1. Generate an app-specific password in your Gmail account.
        2. Update `application-dev.properties` with your Gmail username and app password.

4. **Aspect-Oriented Programming (AOP)**
    - Includes logging functionality for cross-cutting concerns.

5. **Global Exception Handling**
    - Pre-configured global exception handler for managing API errors consistently.

6. **Environment Support**
    - Built with **Java 17**.
    - Uses **Gradle** for dependency management and build.

---

## Getting Started

### Prerequisites

- **Java 17** installed.
- PostgreSQL or SQL Server installed for database support.
- Gmail account for email integration.

### Installation Steps

1. Clone this repository:
   ```bash
   git clone https://github.com/Aligorithm-97/SpringBootAdvancedStarterPack.git
   cd spring-boot-advanced-starter-pack
