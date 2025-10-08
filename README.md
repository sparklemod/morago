# Morago
A service for calling translators on various topics in real time

### Features

- Build profiles: local, prod
- File storage support: Local, S3
- Balance management services: Deposit and Withdrawal
- Role-based access control: Admin, Translator, User
- Dedicated endpoints and services per role
- Real-time communication via WebSocket API (calls & notifications)

## Prerequisites

Docker and Docker Compose

Maven
## Quick Start

- Start the Database

```
docker-compose up -d --build
```

- Add initial data with the SQL command in [data.sql](/init/data.sql)

- Start the Application

```
mvn clean install -DskipTests spring-boot:run
```

## API
http://localhost:8080/swagger-ui/index.html#/
