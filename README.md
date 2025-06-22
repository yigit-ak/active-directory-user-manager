# 📘 Active Directory User Manager

**Active Directory User Manager** is a Spring Boot / ReactJS project for managing users in Microsoft Active Directory via LDAP. It enables admins to securely authenticate via Azure SSO, create users, reset passwords, manage account status, and log actions — all with email notifications and Swagger-documented APIs.

## 🔧 Key Features

* 🔐 **Azure SSO Integration** – OAuth2 login with Microsoft EntraID
* 👤 **User Management** – Create, lock/unlock, and reset AD users
* 🧾 **AD Queries** – Fetch user and vendor (OU) details via LDAP
* 🔒 **Secure Password Handling** – Strong password generation + AD-compatible encoding
* 📩 **Email Notifications** – SendGrid-powered emails for account creation & resets
* 📝 **Action Logging** – Tracks who did what and when (PostgreSQL)
* 📖 **Swagger UI** – Auto-generated API docs for quick testing
* 🌐 **CORS Configured** – Easy frontend integration

## 🚀 Setup & Environment Configuration

### 📦 Prerequisites

* Java 21+
* Maven 3.8+
* PostgreSQL database
* Access to an Active Directory server with LDAPS enabled
* A verified SendGrid API key
* An Azure App Registration for SSO (Microsoft Entra ID)

### 🛠️ Setup Steps

1. **Clone the repository**

```bash
git clone https://github.com/yigit-ak/ad-user-manager.git
cd ad-user-manager
```

2. **Configure the environment**

Create a file named `application.properties` in `src/main/resources/`, or use the existing `example-application.properties` as a template.

```bash
cp src/main/resources/example-application.properties src/main/resources/application.properties
```

3. **Fill in your configuration**

Update `application.properties` with:

* **LDAP/Active Directory**

  ```properties
  ldap.url=ldaps://your-ad-server:636
  ldap.userDn=CN=ServiceUser,OU=Users,DC=example,DC=com
  ldap.password=your-password
  ldap.base=DC=example,DC=com
  parent-organizational-unit=Users
  ```

* **Truststore (for LDAPS)**
  Provide the `.jks` file if you're using a custom certificate.

  ```properties
  trust-store.path=classpath:truststore.jks
  trust-store.password=your-truststore-password
  ```

* **SendGrid Email Settings**

  ```properties
  spring.sendgrid.api-key=your-sendgrid-api-key
  twilio.sendgrid.from-email=admin@example.com
  ```

* **Azure AD SSO Settings**

  ```properties
  spring.cloud.azure.active-directory.enabled=true
  spring.cloud.azure.active-directory.profile.tenant-id=your-tenant-id
  spring.cloud.azure.active-directory.credential.client-id=your-client-id
  spring.cloud.azure.active-directory.credential.client-secret=your-client-secret
  ```

* **Security & Frontend**

  ```properties
  security.required-role=APPROLE_Admin
  frontend.origin=http://localhost:5173
  ```

4. **Run the application**

```bash
./mvnw spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).
