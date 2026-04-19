# 🎓 Mentee Connect

A desktop-based **Mentor–Mentee Interaction System** built using **Java Swing and MySQL**. This application helps mentees find mentors, send requests, and manage interactions efficiently through a structured platform.

---

##  Features

*  User Authentication (Login/Signup)
*  Mentor Profile Management
*  Mentee Profile Management
*  Search Mentors
*  Send Mentorship Requests
*  Accept/Reject Requests
*  Full CRUD Operations (Create, Read, Update, Delete)

---

##  Tech Stack

| Layer     | Technology Used    |
| --------- | ------------------ |
| Frontend  | Java Swing (GUI)   |
| Backend   | Java (JDBC)        |
| Database  | MySQL              |
| IDE       | Eclipse / NetBeans |
| Connector | MySQL Connector/J  |

---

##  Project Structure

```
MenteeConnect/
│
├── src/
│   ├── ui/            # All UI Screens (Swing)
│   ├── dao/           # Database Operations (CRUD)
│   ├── db/            # Database Connection
│   └── model/         # Entity Classes
│
├── lib/               # MySQL Connector JAR
├── database/          # SQL Scripts
└── README.md
```

---

##  Database Schema

### Tables:

* **users**
* **mentors**
* **mentees**
* **requests**

### Relationships:

* One user → One mentor/mentee
* Mentee → sends → Requests
* Mentor → receives → Requests

---

##  Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/ishita526/Mentee_connect
cd mentee-connect
```

---

### 2. Setup MySQL Database

```sql
CREATE DATABASE mentee_connect;
USE mentee_connect;
```

Create tables:

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(10)
);

CREATE TABLE mentors (
    mentor_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100),
    expertise VARCHAR(100),
    email VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE mentees (
    mentee_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100),
    interest VARCHAR(100),
    email VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE requests (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    mentee_id INT,
    mentor_id INT,
    status VARCHAR(20),
    FOREIGN KEY (mentee_id) REFERENCES mentees(mentee_id),
    FOREIGN KEY (mentor_id) REFERENCES mentors(mentor_id)
);
```

---

### 3. Add MySQL Connector

* Download MySQL Connector/J
* Add `.jar` file to your project libraries

---

### 4. Configure Database Connection

Update your DB connection file:

```java
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/mentee_connect",
    "root",
    "your_password"
);
```

---

### 5. Run the Project

* Open in Eclipse / NetBeans
* Run main class (Login UI / Main UI)

---



## Testing

Basic test cases include:

* User login validation
* Mentor search
* Request sending
* CRUD operations

---

##  Future Enhancements

* Web-based version (Spring Boot)
* Chat system between mentor & mentee
* Notifications
* Profile ratings & reviews

---

##  License

This project is for academic use.

---

##  Author

**Ishita Zope**


---


