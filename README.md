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


---

<details><summary>📄 <code>application.properties</code> Example (Click to Expand)</summary>
```properties
spring.application.name=ad-user-manager

# LDAP Configuration
ldap.url=ldap://your-domain-controller
ldap.userDn=CN=ServiceUser,OU=Users,DC=example,DC=com
ldap.password=your-password
ldap.base=DC=example,DC=com

# Truststore (if LDAPS is used)
trust-store.disable-endpoint-identification=true
trust-store.path=classpath:truststore.jks
trust-store.password=your-truststore-password

# Active Directory user creation configuration
parent-organizational-unit=OU=Users,DC=example,DC=com
user-creation-description=Created via LDAP UI

# SendGrid Email Configuration
spring.sendgrid.api-key=your-sendgrid-api-key
twilio.sendgrid.from-email=admin@example.com

# Microsoft Entra ID (Azure SSO)
spring.cloud.azure.active-directory.enabled=true
spring.cloud.azure.active-directory.profile.tenant-id=your-tenant-id
spring.cloud.azure.active-directory.credential.client-id=your-client-id
spring.cloud.azure.active-directory.credential.client-secret=your-client-secret

# Access Control
security.required-role=ROLE_Admin

# Frontend CORS origin
frontend.origin=http://localhost:5173

# Logging database (Optional)
spring.datasource.url=jdbc:postgresql://localhost:5432/logs
spring.datasource.username=db_user
spring.datasource.password=db_pass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Swagger / OpenAPI Docs
springdoc.api-docs.enabled=true
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.resolve-schema-properties=true
springdoc.api-docs.groups.enabled=true
```
</details>
