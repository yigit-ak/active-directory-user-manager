# 🧑‍💻 Active Directory User Manager

This is a full-stack web application built with **Spring Boot** and **React** that allows authenticated users to create, search, and manage users in **Active Directory (AD)** using LDAP. It also supports SSO via OAuth2 and sends credentials via email using SendGrid.

---

## 🚀 Features

- 🔒 OAuth2 SSO Authentication with Azure Entra ID
- 👤 Create, lock/unlock, and reset passwords of AD users
- 🔍 Search users via common name (`cn`)
- 📧 Send credentials to users via email (SendGrid) when an account is created or password changed
- 🌐 CORS-configured for secure frontend-backend integration
- 📦 Centralized error handling with custom exceptions
- 🧪 Input validation on both frontend and backend

---

## 🛠️ Tech Stack

**Backend:** Spring Boot 3, Spring Security, OAuth2 Client, Java  
**Frontend:** React, React Router, Axios

---

## 📦 Required Packages

### Backend (Maven)
- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-oauth2-client`
- `spring-ldap-core`
- `javax.validation`
- `sendgrid-java`

### Frontend (Node.js)
- `react`
- `react-router-dom`
- `axios`
- `vite` (or `create-react-app` depending on setup)

---

## 🔧 Installation & Running

### 📍 Backend

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### 🖥️ Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## 🖼️ UI Preview

_Add screenshots or GIFs here (e.g., `assets/preview.gif`, `images/ui.png`)_

---

## 📄 License

MIT License – Feel free to use, modify, and share.

---

## 👨‍💼 Author

**Yigit Ak** – Software Engineer  
[GitHub](https://github.com/yigitak) | [LinkedIn](#)
