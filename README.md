# Active Directory User Manager

A demo application for managing Active Directory (AD) users. It exposes a REST API implemented with **Spring Boot** and provides a small **React** front‑end for administrators.

## Features

- Create new users under vendor specific organizational units
- Reset passwords and send temporary credentials via email
- Lock and unlock user accounts
- Fetch vendor list from AD
- Log user related actions to a relational database
- OAuth2 login support (tested with Microsoft Entra ID)
- OpenAPI documentation available at `/swagger-ui.html`

## Project Structure

```
server/   # Spring Boot backend
frontend/ # React application served separately
```

## Getting Started

### Backend

1. Copy `server/src/main/resources/example-application.properties` to `application.properties` and update the values for your environment.
2. From the `server` directory run:

```bash
./mvnw spring-boot:run
```

### Frontend

The front‑end uses Vite. To start it in development mode run:

```bash
cd frontend
npm install
npm run dev
```

The app assumes the backend is reachable at `http://localhost:8080` by default.

### Running Tests

Backend tests can be executed with:

```bash
cd server
./mvnw test
```

## License

This project is released under the MIT License.
