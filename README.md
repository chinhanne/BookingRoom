# 🏨 BookingRoom – Hotel Room Booking Backend

A robust hotel room booking backend system built with **Spring Boot**, focused on clean architecture, security, and real-world integrations.  
This project includes features such as JWT authentication, OAuth2 login, Redis/token management, rate limiting, RabbitMQ-based email processing, and VNPAY payment integration.

---

## 🎯 Project Overview

BookingRoom is developed as a **backend service for hotel room booking**, providing RESTful APIs for user authentication, room search, booking management, payment, and notifications.  
It is designed to be secure, scalable, and maintainable.

---

## ✨ Key Features

### 🔐 Authentication & Authorization
- Secure **JWT Authentication** with access & refresh tokens
- **Redis** for token storage
- Login via **Google & GitHub (OAuth2)**
- Logout and token revocation

### 🏨 Room Booking & Search
- Search for available rooms by check-in / check-out date
- Room price, category, and amenity filtering
- Create and manage bookings
- Admin search bookings by status, date range, user, and room

### 💳 Payment Integration
- **VNPAY** payment gateway integration
- Payment status tracking (PAID / UNPAID)
- Cash payment support

### 📬 Email Notification
- Send booking confirmation and invoice via email
- **RabbitMQ** for asynchronous email delivery

### 🚦 Rate Limiting & Performance
- Distributed rate limiting using **Bucket4j + Redis**
- Limits on login, API requests, and OAuth endpoints

### 💬 Comment System
- Add and manage comments for rooms

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot, Spring Security |
| Authentication | JWT, OAuth2 (Google, GitHub) |
| Database | MySQL |
| Cache / Tokens | Redis |
| Messaging | RabbitMQ |
| Rate Limiting | Bucket4j + Redis |
| Payment | VNPAY |
| ORM | Spring Data JPA |
| Build Tool | Maven |

---

## 📂 Project Structure

```text
BookingRoom
│
├── configuration        # Redis, RabbitMQ, Rate Limit, JWT & OAuth2 configurations
├── constant             # Application constants (Roles, permissions, etc.)
├── controller           # REST Controllers (API endpoints)
├── dto                  # Request / Response Data Transfer Objects
├── entity               # Database Entities (JPA)
├── enums                # Application enums (BookingStatus, PaymentStatus, etc.)
├── exception            # Global exception handling & custom exceptions
├── mapper               # Entity ↔ DTO mappers
├── repository           # JPA repositories
├── service              # Business logic layer
├── validator            # Custom validators (e.g. DOB validation)
└── util                 # Utility & helper classes
```

## 🔗 Core APIs (Examples)

### 🔐 Authentication
- `POST /auth/token` – User login
- `POST /auth/refresh` – Refresh token
- `POST /auth/logout` – Logout
- `GET /auth/outbound/authentication` – Initiate Google/GitHub OAuth

### 🏨 Booking & Room
- `POST /bookings` – Create booking
- `GET /bookings/search` – Admin booking search
- `GET /rooms/search` – Search available rooms

### 💳 Payment
- `POST /payment/vnpay` – Create VNPAY payment
- `GET /payment/vnpay/callback` – VNPAY callback handler

---

## ⚙️ Getting Started

### 1️⃣ Clone the repository
```bash
git clone https://github.com/chinhanne/BookingRoom.git
cd BookingRoom
