# Smart Attendance System

A full-stack smart attendance management application built with Java, Spring Boot, React, and MySQL.

## Features

- Secure role-aware login for students and faculty.
- RESTful Spring Boot APIs for students, faculty, courses, attendance sessions, and records.
- Faculty dashboard to create attendance sessions and mark student attendance.
- Student dashboard to review personal attendance percentages and history.
- Responsive React interface for desktop and mobile screens.

## Project Structure

```text
backend/   Spring Boot REST API with JPA entities and repositories
frontend/  React dashboard powered by Vite
```

## Backend Setup

1. Create a MySQL database named `smart_attendance`.
2. Update `backend/src/main/resources/application.properties` with your database credentials.
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

The React application starts on `http://localhost:5173`.
