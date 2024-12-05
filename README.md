# Spring Boot Advanced Starter Pack

This repository serves as an advanced Spring Boot starter pack, designed to streamline development with pre-configured
integrations for database connections, Spring Security with JWT  (including refresh token mechanism), email services, and Aspect-Oriented Programming (AOP)
for logging. Developers can quickly adapt this project with minimal configuration adjustments to meet their needs.

---

## Getting Started

### Prerequisites
- **Java 17** & **Gradle** (recommended version: **8.10.2**).
- **PostgreSQL** or **SQL Server** installed for database support.
- **Gmail** account for email integration.

---

## Step-by-Step Setup Guide

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Aligorithm-97/SpringBootAdvancedStarterPack.git
   cd SpringBootAdvancedStarterPack
   
2. **Install Java 17 and Gradle**
   - Ensure **Java 17** is installed on your system.
   - Install **Gradle** (recommended version: **8.10.2**) if not already installed.

3. **Database Setup**
   - **SQL Server**:
      - Update `application-dev.properties` with your SQL Server details:
        ```properties
        spring.datasource.url=jdbc:sqlserver://<your-sql-server-url>:<port>;databaseName=<database>
        spring.datasource.username=<your-username>
        spring.datasource.password=<your-password>
        ```
   - **PostgreSQL**:
      - Install **PostgreSQL** if it is not already installed.
      - Use **pgAdmin** to create a new database.
      - Update `application-dev.properties` with your PostgreSQL details:
        ```properties
        spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
        spring.datasource.username=<your-username>
        spring.datasource.password=<your-password>
        ```

4. **Email Integration**
   - Generate an app-specific password in your Gmail account.
   - Update `application-dev.properties` with your Gmail credentials:
     ```properties
     spring.mail.mailUsername=<your-email>
     spring.mail.mailPassword=<app-password>
     ```

5. **CORS Configuration**
   - By default, the **CORS** configuration allows all ports for development purposes.
   - Update the allowed origins in `BeansConfig` for production as needed:
     ```java
     configuration.setAllowedOrigins(Arrays.asList(<your-domain-or-ip>));
     ```

6. **Activate User Accounts**
   - Add the URL of your UI for account activation in `application-dev.properties`:
     ```properties
     application.mailing.frontend.activation-url=http://<your-ui-url>/activate
     ```

7. **Troubleshooting**
   - If all the above steps have been completed and you still encounter errors, it could be due to the **Lombok annotation processor** not functioning correctly.
   - To resolve this:
      - Ensure Lombok is installed and enabled in your IDE.
      - For IntelliJ IDEA:
         1. Go to **File > Settings > Plugins**, search for `Lombok`, and install it if it’s not already installed.
         2. Enable annotation processing:  
            **File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors**, and check **Enable annotation processing**.

---

## Key Features

1. **Database Integration**
   - **PostgreSQL**: Pre-configured in `application-dev.properties`. Simply install PostgreSQL, create the database using **pgAdmin**, and update properties such as `port`, `database name`, and `credentials` as needed.
   - **SQL Server**: Configuration is available for SQL Server. Update the necessary details and include the required dependencies in `build.gradle` to switch to SQL Server.

2. **Spring Security with JWT**
   - Includes **JSON Web Token (JWT)** authentication for securing APIs.
   - **Supports Refresh Token Mechanism**:
     - Enables seamless token renewal without requiring users to re-authenticate.
     - Designed for use with front-end interceptors to manage token refresh automatically.
     - Pre-configured endpoints for obtaining access and refresh tokens.
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

*Thank you for using this advanced starter pack. If you have any feedback or suggestions, feel free to contribute to the repository. Wishing you success in your development journey. Happy coding! 🚀*

---
