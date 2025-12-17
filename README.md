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
| **Resilience & Performance** | Application of distributed caching (Redis), **HTTP Caching**, and defensive mechanisms (AOP Rate Limiting, **Circuit Breaker**). |
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
| **`presentation`** | **External Interface & Routing (BFF).** Handles HTTP concerns, versioning, and security filtering. **Designed as a Backend for Frontend**, simplifying interactions for UI clients (e.g., abstracting away complex database IDs). | REST Controllers, API Versioning (`@ApiVersion`), Global Exception Handling, **OpenAPI/Swagger UI**. |

### 2. 🛡️ Advanced Security Engineering (JWT, Spring Security, RSA)

The authentication system is designed for high security and scalability:

* **Asymmetric Token Signing (RSA):** The implementation utilizes **RSA asymmetric key pairs** for signing JSON Web Tokens (JWT). This ensures only the server with the private key can issue valid tokens, while any external service can verify them using the public key.
* **RSA Key Requirement:** For the authentication system to function, a **`pkey.pem`** file (RSA private key in PKCS8 format) must be generated and correctly configured.
* **Role-Based Authorization Guards:** Authorization is handled declaratively through custom Spring Security annotations like `@RequireAdmin`.
* **Filter Chain Configuration:** `JwtAuthenticationFilter.java` manages authentication seamlessly, integrated with `SecurityFilterChainConfig.java` to enforce security headers and policies.

### 3. ⚡ Resilience, Observability & Dual-Strategy Caching

Mechanisms are implemented to ensure the application remains stable and performs optimally under high load.

| Feature | Design and Implementation |
| :--- | :--- |
| **AOP Logging for Observability** | Utilizes **Aspect-Oriented Programming (AOP)** via `@Loggable` to inject cross-cutting logging logic (timing, parameters) without code pollution. |
| **Distributed Rate Limiting** | Implemented using a high-throughput **Redis** adapter and a Presentation layer interceptor to prevent resource exhaustion. |
| **Asynchronous Processing** | Uses **Project Reactor (`Mono`)** and **`CompletableFuture`** to handle external Stripe API calls asynchronously, preventing main thread blocking. |
| **Fault Tolerance (Circuit Breaker)** | Implements **Resilience4j** (`@CircuitBreaker`, `@Retry`) on critical checkout use cases to prevent cascading failures. |
| **Dual-Strategy Caching** | **Public Routes:** Implements **HTTP Caching** (ETags/Cache-Control) to reduce bandwidth and server load for static-like data. <br> **Private Data:** Uses **Redis** for distributed caching of sensitive or frequently accessed session data. |



### 4. 🗄️ Persistence and External Integrations

* **Hybrid Persistence:** Decouples Domain Repositories from specific technologies (PostgreSQL for transactional data, MongoDB for flexible product schemas).
* **Data Optimization:** Prevents **N+1 Selects** using **`@EntityGraph`** and strategic database indexing for sub-millisecond read times.
* **Efficient Paginated Reads:** The `ProductDetailReadAdapter` implements complex **Hybrid Pagination**, combining and filtering data from both PostgreSQL and MongoDB in a single query path.
* **Cloud-Native Storage:** `S3StorageService.java` uses the **official AWS S3 SDK**, ensuring compatibility with AWS S3 and self-hosted solutions like **MinIO**.

---

## 🛠️ Technology Stack

| Category | Technology | Detail |
| :--- | :--- | :--- |
| **Language** | **Java** | JDK 17+ |
| **Framework** | **Spring Boot 3.x** | Core framework, IoC, Web, and Data. |
| **API Documentation** | **OpenAPI / Swagger UI** | Industry-standard interactive documentation. |
| **Security** | **Spring Security** | JWT, RSA Asymmetric Keys, Custom Filters. |
| **Resilience** | **Resilience4j** | Circuit Breaker and Retry patterns. |
| **Caching** | **Redis & HTTP Cache** | Distributed Caching and Browser-level caching. |
| **Database** | **PostgreSQL & MongoDB** | Hybrid Relational and NoSQL storage. |
| **Object Storage** | **MinIO & AWS S3 SDK** | Cloud-native storage implementation. |

---

## ⚙️ Installation and Running

### Prerequisites

1.  **Docker & Docker Compose**: Required for infrastructure and the application.
2.  **RSA Key Pair**: Generate a private key file named **`pkey.pem`** and place it in the project root. **Required for JWT.**
3.  (Optional) **JDK 17+ & Maven**: To run the application natively.

### Steps

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Steph7478/Lumiere-Backend.git](https://github.com/Steph7478/Lumiere-Backend.git)
    cd Lumiere-Backend
    ```
2.  **Environment Configuration:** Create a `.env` file in the project root with the necessary credentials.
3.  **Start All Services:**
    ```bash
    docker-compose --env-file .env up --build -d
    ```

**Status:** The application will be available at `http://localhost:8080`.

---

## 🧭 API Endpoints

All endpoints follow API versioning standards (`@ApiVersion`).

* **Documentation (Swagger UI):** Interactive docs at `http://localhost:8080/api/v1/swagger-ui`

| Module | Example Controller | Functionality |
| :--- | :--- | :--- |
| **Admin** | `AdminController.java` | Product/Stock management (Requires `@RequireAdmin`). |
| **Auth** | `AuthController.java` | RSA-signed JWT Login and Registration. |
| **Product** | `ProductController.java` | **Hybrid Pagination** & Search (Public/Cached). |
| **Payment** | `PaymentController.java` | **Asynchronous** Stripe Checkout session creation. |
| **Webhooks** | `StripeWebhookController.java` | Idempotent handling of payment status updates. |

---

## 🛑 Disclaimer

This project is developed solely for **learning and portfolio demonstration purposes**. It showcases proficiency in advanced software architecture and performance patterns. It is not a live commercial application.
