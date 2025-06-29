# Dream Shops - E-commerce Backend API

A comprehensive e-commerce backend application built with Spring Boot, featuring user authentication, product management, shopping cart functionality, and order processing.

## 📋 Overview

This project is a RESTful API for an online shopping platform that provides complete backend functionality for managing products, categories, shopping carts, orders, and user authentication with role-based access control.

**Tutorial Source**: This project follows the YouTube tutorial: *"Spring Boot, Spring Security, JWT Course – Shopping Cart Backend Java Project"*

## 🚀 Technologies Used

- **Java 21** - Programming language
- **Spring Boot 3.x** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **JWT (JSON Web Tokens)** - Token-based authentication
- **MySQL** - Database
- **Lombok** - Reduce boilerplate code
- **ModelMapper** - Entity to DTO mapping
- **Maven** - Dependency management

## 🏗️ Architecture

The application follows a layered architecture:
- **Controller Layer** - REST API endpoints
- **Service Layer** - Business logic
- **Repository Layer** - Data access
- **Model Layer** - Entity classes
- **DTO Layer** - Data transfer objects
- **Security Layer** - Authentication & authorization


## 🔧 Key Features

### Authentication & Authorization
- JWT-based authentication
- Role-based access control (USER, ADMIN)
- User registration and login
- Protected endpoints with method-level security

### Product Management
- CRUD operations for products
- Category-based product organization
- Image upload and management
- Product search and filtering
- Admin-only product management

### Shopping Cart
- Add/remove items from cart
- Update item quantities
- Cart persistence per user
- Cart total calculation

### Order Management
- Place orders from cart
- Order history tracking
- Order status management
- User-specific order retrieval

### User Management
- User profile management
- User registration
- Role assignment

## 🛡️ Security Features

- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **Role-Based Access Control**: Different permissions for USER and ADMIN roles
- **Method-Level Security**: `@PreAuthorize` annotations for fine-grained access control
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Cross-origin resource sharing setup

## 🔐 Authentication Flow

1. User registers/logs in with credentials
2. Server validates credentials and returns JWT token
3. Client includes JWT token in Authorization header for protected requests
4. Server validates token and grants access based on user roles

## 🗄️ Database Schema

Key entities and relationships:
- **User** ↔ **Role** (Many-to-Many)
- **User** ↔ **Cart** (One-to-One)
- **User** ↔ **Order** (One-to-Many)
- **Cart** ↔ **CartItem** (One-to-Many)
- **Order** ↔ **OrderItem** (One-to-Many)
- **Product** ↔ **Category** (Many-to-One)
- **Product** ↔ **Image** (One-to-Many)
