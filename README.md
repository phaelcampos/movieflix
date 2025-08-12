# MovieFlix API

A comprehensive REST API for movie catalog management built with Spring Boot 3.x. This application provides a complete solution for managing movies, categories, streaming services, and user authentication with JWT security.

## 📋 Overview

MovieFlix is a robust movie catalog management system that allows users to:

- **Manage Movies**: Create, read, update, and delete movie records
- **Categorize Content**: Organize movies by categories (Action, Comedy, Drama, etc.)
- **Track Streaming Services**: Associate movies with streaming platforms
- **User Authentication**: Secure JWT-based authentication and authorization
- **Interactive Documentation**: Built-in Swagger UI for API testing and documentation

## 🏗️ Architecture

The application follows a clean, layered architecture with separation of concerns:

```
├── config/          # Configuration classes (Security, JWT, Swagger)
├── controller/      # REST API endpoints
├── entity/          # JPA entities and database models
├── exception/       # Custom exception handling
├── mapper/          # DTO mapping utilities
├── repository/      # Data access layer (JPA repositories)
├── request/         # Request DTOs
├── response/        # Response DTOs
└── service/         # Business logic layer
```

## 🛠️ Technology Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.5.4** - Main framework
- **Spring Security 6** - Authentication and authorization
- **Spring Data JPA** - Data access layer
- **PostgreSQL** - Database
- **Maven** - Build tool

### Key Dependencies
- **JWT (Auth0)** - JSON Web Token implementation
- **Flyway** - Database migration management
- **Lombok** - Boilerplate code reduction
- **Bean Validation** - Request validation
- **SpringDoc OpenAPI** - API documentation (Swagger)

## 📊 Data Model

### Core Entities

**Movie**
- `id` (Long) - Primary key
- `title` (String) - Movie title
- `description` (String) - Movie description
- `releaseDate` (LocalDate) - Release date
- `rating` (Double) - Movie rating
- `createdAt` (LocalDate) - Creation timestamp
- `updatedAt` (LocalDate) - Last update timestamp
- `categories` (List<Category>) - Many-to-many relationship
- `streamings` (List<Streaming>) - Many-to-many relationship

**User**
- `id` (Long) - Primary key
- `name` (String) - User full name
- `email` (String) - User email (unique)
- `password` (String) - Encrypted password

**Category**
- `id` (Long) - Primary key
- `name` (String) - Category name

**Streaming**
- `id` (Long) - Primary key
- `name` (String) - Streaming service name

### Database Relationships
- **Movie ↔ Category**: Many-to-Many (movie_category junction table)
- **Movie ↔ Streaming**: Many-to-Many (movie_streaming junction table)

## 🚀 Getting Started

### Prerequisites

1. **Java 17** or higher
2. **PostgreSQL** database server
3. **Maven 3.6+** (or use included wrapper)

### Database Setup

1. Install PostgreSQL and create a database:
   ```sql
   CREATE DATABASE movieflix;
   ```

2. Update database credentials in `src/main/resources/application.yaml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/movieflix
       username: your_username
       password: your_password
   ```

### Installation & Running

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd movieflix
   ```

2. **Build the application**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Database Migration

Flyway will automatically run database migrations on startup. The migration scripts are located in `src/main/resources/db/migration/`:

- `V1__create_table_category.sql`
- `V2__create_table_streaming.sql` 
- `V3__create_table_movie.sql`
- `V4__create_table_movie_category.sql`
- `V5__create_table_movie_streaming.sql`
- `V6__create_table_user.sql`

## 🔐 Authentication

The API uses JWT (JSON Web Tokens) for authentication:

### Registration
```bash
POST /movieflix/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com", 
  "password": "password123"
}
```

### Login
```bash
POST /movieflix/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Using the Token
Include the JWT token in the Authorization header for protected endpoints:
```bash
Authorization: Bearer <your-jwt-token>
```

## 📚 API Endpoints

### Public Endpoints
- `POST /movieflix/auth/register` - User registration
- `POST /movieflix/auth/login` - User login

### Protected Endpoints (Require JWT)

#### Movies
- `GET /movieflix/movie` - List all movies
- `GET /movieflix/movie/{id}` - Get movie by ID
- `GET /movieflix/movie/search?categoryId={id}` - Search movies by category
- `POST /movieflix/movie` - Create new movie
- `PUT /movieflix/movie/{id}` - Update movie
- `DELETE /movieflix/movie/{id}` - Delete movie

#### Categories
- `GET /movieflix/category` - List all categories
- `GET /movieflix/category/{id}` - Get category by ID
- `POST /movieflix/category` - Create new category
- `DELETE /movieflix/category/{id}` - Delete category

#### Streaming Services
- `GET /movieflix/streaming` - List all streaming services
- `GET /movieflix/streaming/{id}` - Get streaming service by ID
- `POST /movieflix/streaming` - Create new streaming service
- `DELETE /movieflix/streaming/{id}` - Delete streaming service

## 📖 API Documentation

The API includes interactive Swagger documentation:

- **Swagger UI**: `http://localhost:8080/swagger/index.html`
- **OpenAPI Spec**: `http://localhost:8080/api/api-docs`

The Swagger UI provides:
- Complete API documentation
- Interactive endpoint testing
- Request/response examples
- Built-in JWT authentication support

## 🔧 Configuration

### Environment Variables
For production, consider using environment variables for sensitive data:

```bash
export MOVIEFLIX_DB_URL=jdbc:postgresql://localhost:5432/movieflix
export MOVIEFLIX_DB_USERNAME=your_username
export MOVIEFLIX_DB_PASSWORD=your_password
export MOVIEFLIX_JWT_SECRET=your-super-secret-key
```

### Application Properties
Key configuration options in `application.yaml`:

```yaml
spring:
  datasource:
    url: ${MOVIEFLIX_DB_URL:jdbc:postgresql://localhost:5432/movieflix}
    username: ${MOVIEFLIX_DB_USERNAME:postgres}
    password: ${MOVIEFLIX_DB_PASSWORD:postgres}
    
movieFlix:
  security:
    secret: ${MOVIEFLIX_JWT_SECRET:your-secret-key}
    
springdoc:
  api-docs:
    path: /api/api-docs
  swagger-ui:
    path: /swagger/index.html
```

## 🏷️ Request/Response Examples

### Create Movie
```bash
POST /movieflix/movie
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "The Matrix",
  "description": "A computer programmer discovers reality is a simulation",
  "releaseDate": "31/03/1999",
  "rating": 8.7,
  "categories": [1, 2],
  "streamings": [1, 3]
}
```

### Create Category
```bash
POST /movieflix/category
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Science Fiction"
}
```

## 🚦 Error Handling

The API provides comprehensive error handling with:

- **Validation Errors**: Field-level validation with detailed messages
- **Authentication Errors**: Clear JWT-related error responses
- **Business Logic Errors**: Custom exception handling
- **HTTP Status Codes**: Proper status codes for different scenarios

Example error response:
```json
{
  "title": "Titulo do filme é obrigatório"
}
```

## 📝 License

This project is licensed under the MIT License.

## 👥 Author

**Raphael**
- GitHub: [@phaelcampos](https://github.com/phaelcampos)

For support or questions, please open an issue in the GitHub repository.
