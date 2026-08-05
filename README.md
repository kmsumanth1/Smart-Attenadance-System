# Smart Attendance System

A production-oriented full-stack attendance management application built with Java, Spring Boot, React, Tailwind CSS, and MySQL.

## Features

- JWT-secured registration and login for admins, faculty, and students with BCrypt password hashing.
- Layered Spring Boot backend using Controller → Service → Repository.
- CRUD APIs for users, students, faculty, courses, subjects, attendance sessions, attendance records, and student reports.
- DTO-based request/response models with Bean Validation and global exception handling.
- Spring Data JPA entity relationships for users, faculty, students, courses, subjects, attendance sessions, and records.
- Pagination and search support on list endpoints through Spring `Pageable` and `q` query parameters.
- React Router pages for Login, Dashboard, Students, Faculty, Courses, Attendance, and Reports.
- Axios API client with JWT Authorization interceptor, localStorage token persistence, and automatic logout on token expiry.

## Project Structure

```text
backend/   Spring Boot REST API with JPA entities, DTOs, services, repositories, and controllers
frontend/  React + Vite app with Tailwind CSS, Axios, and React Router
```

## Backend Setup

1. Create a MySQL database named `smart_attendance`.
2. Update `backend/src/main/resources/application.properties` or set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
3. Run the API:

```bash
cd backend
mvn spring-boot:run
```

The API starts on `http://localhost:8080` and exposes endpoints under `/api`.

## Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

The React application starts on `http://localhost:5173`. Set `VITE_API_URL` to point to a different backend URL.

## API Overview

- `POST /api/auth/register`
- `POST /api/auth/login`
- `/api/users`
- `/api/students`
- `/api/faculty`
- `/api/courses`
- `/api/subjects`
- `/api/attendance/sessions`
- `/api/attendance/records`
- `/api/attendance/reports/students/{studentId}`

List endpoints accept `page`, `size`, `sort`, and optional `q` search parameters where applicable.

## Authentication

The backend issues signed JWT bearer tokens from `/api/auth/register` and `/api/auth/login`. The frontend stores the token in `localStorage`, sends it through the Axios `Authorization: Bearer <token>` interceptor, and automatically logs out when the token expires or protected requests return authorization errors.
