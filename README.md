# ✨ Lumière Backend: An E-commerce Core

**A High-Performance Core API engineered with Clean Architecture and Domain-Driven Design (DDD).**

[![Project Status](https://img.shields.io/badge/Status-In%20Progress-yellow)](https://github.com/Steph7478/Lumiere-Backend)
[![Design Pattern](https://img.shields.io/badge/Architecture-DDD%20%7C%20Clean%20Arch.-blue)](https://domaindriven.design/)

---

## 🎯 Strategic Focus & Demonstrated Capabilities

This project is a technical portfolio piece, demonstrating proficiency in designing and implementing systems that utilize **advanced architectural and performance patterns** to achieve high **Scalability and Maintainability**.

**Key Capabilities Demonstrated:**

| Capability | Advanced Implementation Detail |
| :--- | :--- |
| **Architectural Governance** | Strict, code-enforced separation (via packages) of the business domain from infrastructure concerns. |
| **Client-Oriented Design** | Implementation of **Backend for Frontend (BFF)** patterns, abstracting IDs and complex data models from the UI. |
| **Resilience & Performance** | Application of distributed caching (Redis) and defensive mechanisms (AOP Rate Limiting, **Circuit Breaker**). |
| **Security Engineering** | Implementation of asymmetric JWT signing (RSA) and sophisticated authorization guards (`@RequireAdmin`). |
| **Data Optimization** | **N+1 prevention** using **`@EntityGraph`** and **database indexing** for fast query execution. |
| **Decoupling** | Use of the Adapter pattern to switch between persistence models (JPA, NoSQL) and external services (Stripe, S3) without affecting the Domain. |
| **Observability** | Centralized, cross-cutting logging using Aspect-Oriented Programming (AOP). |

## 🚧 Project Status

This project is **actively in progress**. The architecture is stable, with current work focusing on performance tuning and advanced feature completion.

## ✅ Technical Deep Dive: Advanced Implementation

The Lumière Backend is built upon a foundation of best-in-class patterns and technologies:

### 1. 🏛️ Architectural Foundation (DDD & Clean Architecture)

The system is rigorously structured into independent layers, ensuring the core domain remains immutable and testable.

| Layer (Package) | Advanced Role | Key Design Patterns |
| :--- | :--- | :--- |
| **`domain`** | **Business Source of Truth.** Defines core entities (`Order`, `Cart`) and Value Objects (`Money`, `Stock`) with validation logic encapsulated inside. | Domain Layer, Value Objects, Repository Interfaces. |
| **`application`** | **Transaction & Workflow Manager.** Implements **Ports** (`I...UseCase`) and **Use Cases**, acting as the primary transaction boundary. | Command/Query Separation (CQRS DTOs), Ports & Adapters (Hexagonal). |
| **`infrastructure`** | **Implementation Adapters.** Contains all technical details. Decouples persistence (JPA, NoSQL) and external APIs (Stripe, S3) from the core logic. | Adapter Pattern, Service Implementation. |
| **`presentation`** | **External Interface & Routing (BFF).** Handles HTTP concerns, versioning, and security filtering. **Designed as a Backend for Frontend**, simplifying interactions for UI clients (e.g., abstracting away complex database IDs). | REST Controllers, API Versioning (`@ApiVersion`), Global Exception Handling. |

### 2. 🛡️ Advanced Security Engineering (JWT, Spring Security, RSA)

The authentication system is designed for high security and scalability:

* **Asymmetric Token Signing (RSA):** The implementation utilizes **RSA asymmetric key pairs** for signing JSON Web Tokens (JWT). This is superior to symmetric signing (HMAC) as it ensures only the server with the private key can issue valid tokens, while any external service (or client) can verify the token using the public key.
* **Role-Based Authorization Guards:** Authorization is handled declaratively through custom Spring Security annotations like `@RequireAdmin`, with `RequireAdminValidator.java` enforcing business rules at the **Use Case** boundary, not just the Controller layer.
* **Filter Chain Configuration:** `JwtAuthenticationFilter.java` manages authentication seamlessly, integrated with `SecurityFilterChainConfig.java` to enforce security headers and policies across all endpoints.

### 3. ⚡ Resilience and Observability

Mechanisms are implemented to ensure the application remains stable and auditable under high load, and to gracefully handle failures in external services.

| Feature | Design and Implementation |
| :--- | :--- |
| **AOP Logging for Observability** | Utilizes **Aspect-Oriented Programming (AOP)** with the custom `@Loggable` annotation to inject cross-cutting *logging* logic (timing, parameters) into both `Controller`s and critical `UseCases`, providing deep insights into runtime performance and flow without code pollution. |
| **Distributed Rate Limiting** | Implemented using a high-throughput **Redis** adapter (`RedisRateLimiterAdapter.java`) and a Presentation layer interceptor (`RateLimitInterceptor.java`) to defensively prevent resource exhaustion from abusive traffic. |
| **Fault Tolerance (Circuit Breaker)** | Implements the **Circuit Breaker** and **Retry** patterns via **Resilience4j** on the critical `CreateCheckoutSessionUseCase`. This prevents cascading failures and ensures stability when the **Stripe Payment Gateway** experiences latency or downtime, using a `fallbackMethod` for graceful error handling. |
| **Strategic Caching** | Dedicated `ProductCacheService.java` manages Redis distributed cache, implementing a read-through strategy for frequently accessed product data, significantly improving read latency. |

### 4. 🗄️ Persistence and External Integrations

The infrastructure is designed for maximum flexibility and compliance with cloud standards.

* **Hybrid Persistence:** Utilizes an Adapter pattern to decouple the Domain's Repository interfaces from the specific persistence technology:
    * **JPA Adapters:** For transactional, relational data (Orders, Users).
    * **Data Optimization:** Prevention of the **N+1 Selects problem** in JPA repositories using the **`@EntityGraph`** annotation, drastically reducing the number of database queries required for complex object graphs.
    * **SQL Indexing:** Proper database indexing is applied to frequently queried fields to ensure sub-millisecond read times.
    * **NoSQL Adapters:** For flexible schema data (e.g., product categories) via `infrastructure.persistence.nosql`.
* **Cloud-Native Storage:** `S3StorageService.java` is implemented against the **official AWS S3 SDK**. This choice is strategic, ensuring the system is instantly compatible with cloud services like AWS S3 and self-hosted solutions like **MinIO**, guaranteeing minimal refactoring during environment migration.
* **Resilient Payment Webhooks:** `StripeWebhookEventDispatcher.java` and specialized adapters manage the asynchronous and idempotent processing of payment events, ensuring system state consistency even in the face of network failures.

---

## 🛠️ Technology Stack

| Category | Technology | Detail |
| :--- | :--- | :--- |
| **Language** | **Java** | JDK 17+ |
| **Framework** | **Spring Boot 3.x** | Core framework, IoC, Web, and Data. |
| **Security** | **Spring Security** | JWT, RSA Asymmetric Keys, Custom Filters. |
| **Resilience** | **Resilience4j** | Circuit Breaker and Retry patterns. |
| **Caching/Resilience** | **Redis** | Distributed Caching and Rate Limiting. |
| **Database** | **PostgreSQL** | Primary relational store (via Spring Data JPA). |
| **Object Storage** | **MinIO & AWS S3 SDK** | Cloud-native storage implementation. |
| **Patterns** | **AOP, DDD, Ports & Adapters** | Aspect-Oriented Programming for cross-cutting concerns. |
| **Payment Gateway** | **Stripe** | Checkout and Webhook processing. |

## ⚙️ Installation and Running

### Prerequisites

1.  **Docker** and **Docker Compose** (Required to run the infrastructure and the application).
2.  (Optional) **JDK 17+** and **Maven** (Only if you wish to run the application natively outside Docker).

### Steps (Via Docker Compose)

This is the recommended way to run the application, as it automatically provisions the entire stack (PostgreSQL, Redis, MinIO) and the API.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Steph7478/Lumiere-Backend.git](https://github.com/Steph7478/Lumiere-Backend.git)
    cd Lumiere-Backend
    ```

2.  **Environment Configuration (`.env`):**
    Create a `.env` file in the project root and populate it with the necessary credentials. Docker Compose will automatically use this file.

3.  **Start All Services:**
    Build and launch the application backend along with the infrastructure services in a single command:
    ```bash
    docker-compose --env-file .env up --build -d
    ```

**Status:** The application will be available at `http://localhost:8080`.

---

## 🧭 API Endpoints

All endpoints follow API versioning standards (`@ApiVersion`). The documentation for all available endpoints (Swagger/OpenAPI) is pending development.

| Module | Example Controller | Functionality |
| :--- | :--- | :--- |
| **Admin** | `AdminController.java` | Product/Stock/Price/Coupon management (Requires `@RequireAdmin`). |
| **Auth** | `AuthController.java` | User Registration, Login, Token Management (`CreateUserUseCase`, `LoginUseCase`). |
| **Product** | `ProductController.java` | Product search, listing, and detail retrieval (`ProductReadUseCase`). |
| **Coupon** | `CouponController.java` | Listing and checking available coupons (`AvalibleCouponsUseCase`). |
| **Order/Cart**| `OrderController.java`, `CartController.java` | Cart manipulation, Order creation, Coupon application. |
| **Payment**| `PaymentController.java` | Initiating the payment process (e.g., Stripe Checkout session creation). |
| **Webhooks** | `StripeWebhookController.java` | Asynchronous handling of payment status updates from Stripe (`PaymentSucceededUseCase`). |

---

## 🛑 Disclaimer

This project is developed solely for **learning, practice, and portfolio demonstration purposes**. It is intended to showcase proficiency in advanced software architecture, security, and performance patterns (DDD, Clean Architecture, AOP, RSA, Caching, **Resilience4j**). It is not a live or production-ready commercial application.
