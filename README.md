# Helpdesk - Ticketing Management System

A full-featured helpdesk/ticketing system built with **Spring Boot** (backend) and **React** (frontend). This application allows organizations to manage support tickets, assign tasks to technicians, track progress, and provide analytics through a dashboard.

---

## Features

### Ticket Management
- Create, view, update, and delete support tickets
- Assign tickets to technicians
- Track ticket status (OPEN, IN_PROGRESS, RESOLVED, CLOSED, PENDING)
- Filter tickets by status, priority, and user
- Search functionality
- Automatic ticket number generation (format: `TICK + timestamp`)
- Due date tracking and overdue detection

### User Management
- Three user roles: **ADMIN**, **TECHNICIAN**, **USER**
- Full CRUD operations for users
- Role-based access control
- User filtering by role and active status
- Profile management (name, email, phone, department)

### Dashboard & Analytics
- Real-time statistics including:
  - Total tickets count
  - Open tickets
  - In-progress tickets
  - Resolved tickets
  - High-priority tickets
  - Tickets assigned to current user
  - Overdue tickets count

### Notifications
- Real-time notifications for:
  - Ticket creation
  - Ticket updates
  - Ticket assignments
  - Comments added
  - Status changes (resolved, closed, reopened)
- Mark notifications as read/unread
- Unread count tracking
- Bulk mark-all-as-read functionality

### Additional Features
- Ticket comments/internal notes system
- File attachments for tickets
- JWT-based authentication and authorization
- Email notifications (via Spring Mail)
- OpenAPI/Swagger documentation

---

## Tech Stack

### Backend
| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.2.3 |
| Spring Data JPA | ORM |
| Spring Security + JWT | Authentication |
| MySQL | Database |
| Spring Mail | Email |
| Maven | Build Tool |
| Lombok | Boilerplate reduction |
| SpringDoc OpenAPI | API documentation |

### Frontend
| Technology | Version |
|------------|---------|
| React | 19.2.1 |
| TypeScript | - |
| Material-UI (MUI) | 7.x |
| React Router DOM | 7.x |
| Axios | HTTP client |
| dayjs | Date handling |
| react-hot-toast | Notifications |

---

## Project Structure

```
Helpdesk/
├── backend/
│   └── helpdesk-backend/
│       ├── src/main/java/com/helpdesk/helpdesk_backend/
│       │   ├── controller/       # REST API endpoints
│       │   │   ├── TicketController.java
│       │   │   ├── UserController.java
│       │   │   ├── NotificationController.java
│       │   │   └── DashboardController.java
│       │   ├── dto/              # Data Transfer Objects
│       │   │   ├── TicketDTO.java
│       │   │   ├── CreateTicketDTO.java
│       │   │   ├── UpdateTicketDTO.java
│       │   │   ├── UserDTO.java
│       │   │   ├── NotificationDTO.java
│       │   │   └── DashboardStatsDTO.java
│       │   ├── entity/           # JPA Entities
│       │   │   ├── Ticket.java
│       │   │   ├── User.java
│       │   │   ├── Notification.java
│       │   │   ├── TicketComment.java
│       │   │   └── TicketAttachment.java
│       │   ├── repository/       # Spring Data JPA repositories
│       │   ├── service/          # Business logic layer
│       │   │   ├── TicketService.java
│       │   │   ├── UserService.java
│       │   │   └── NotificationService.java
│       │   ├── config/           # Configuration classes
│       │   └── exception/        # Global exception handling
│       ├── src/main/resources/
│       │   └── application.properties  # DB and app config
│       └── pom.xml
├── frontend/
│   └── src/
│       ├── components/           # React components
│       │   ├── Dashboard/        # Dashboard view
│       │   ├── Tickets/          # Ticket management
│       │   │   ├── TicketList.jsx
│       │   │   ├── TicketForm.jsx
│       │   │   └── TicketDetail.jsx
│       │   ├── Users/            # User management
│       │   │   ├── UserList.jsx
│       │   │   └── UserForm.jsx
│       │   └── Layout/           # Layout components
│       ├── services/             # API service layer
│       │   ├── api.js            # Axios instance
│       │   ├── ticketService.js
│       │   ├── userService.js
│       │   ├── notificationService.js
│       │   └── dashboardService.js
│       ├── App.tsx               # Main app component
│       └── index.tsx
└── README.md
```

---

## API Endpoints

### Tickets (`/api/tickets`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create new ticket |
| GET | `/` | Get all tickets |
| GET | `/{id}` | Get ticket by ID |
| GET | `/number/{ticketNumber}` | Get ticket by number |
| GET | `/status/{status}` | Filter by status |
| GET | `/priority/{priority}` | Filter by priority |
| GET | `/user/{userId}` | Get tickets created by user |
| GET | `/assigned/{userId}` | Get tickets assigned to user |
| PATCH | `/{id}/status` | Update ticket status |
| PATCH | `/{id}/assign/{userId}` | Assign ticket to user |
| DELETE | `/{id}` | Delete ticket |

### Users (`/api/users`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create user |
| GET | `/` | Get all users |
| GET | `/{id}` | Get user by ID |
| GET | `/role/{role}` | Filter by role (ADMIN, TECHNICIAN, USER) |
| GET | `/active` | Get active users |
| DELETE | `/{id}` | Delete user |

### Notifications (`/api/notifications`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/{userId}` | Get all notifications for user |
| GET | `/user/{userId}/unread` | Get unread notifications |
| GET | `/user/{userId}/unread-count` | Get unread count |
| PATCH | `/{id}/read` | Mark notification as read |
| PATCH | `/user/{userId}/read-all` | Mark all as read |
| DELETE | `/{id}` | Delete notification |
| DELETE | `/user/{userId}` | Delete all notifications |

### Dashboard (`/api/dashboard`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/stats` | Get dashboard statistics |

---

## Getting Started

### Prerequisites
- **Java 21** or higher
- **Node.js** 18+ and npm
- **MySQL** 8.0+ (or use Docker)
- **Maven** (included with Spring Boot)

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend/helpdesk-backend
   ```

2. Configure the database in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the backend:
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`

4. Access Swagger UI: `http://localhost:8080/swagger-ui.html`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```
   The app will open at `http://localhost:3000`

---

## Database Schema

### Users
- `id` (Primary Key)
- `fullName`, `email`, `password`, `phone`, `department`
- `role` (ADMIN, TECHNICIAN, USER)
- `isActive` (boolean)
- Timestamps: `createdAt`, `updatedAt`

### Tickets
- `id` (Primary Key)
- `ticketNumber` (unique identifier, e.g., TICK1714665550000)
- `title`, `description`
- `priority` (LOW, MEDIUM, HIGH, CRITICAL)
- `status` (OPEN, IN_PROGRESS, RESOLVED, CLOSED, PENDING)
- `category`
- `dueDate`
- Foreign keys: `createdBy`, `assignedTo` (User)
- Timestamps: `createdAt`, `updatedAt`

### Notifications
- `id` (Primary Key)
- `userId` (recipient)
- `type` (TICKET_CREATED, TICKET_ASSIGNED, etc.)
- `title`, `message`
- `isRead` (boolean)
- `relatedEntityId`, `relatedEntityType`
- Timestamps: `createdAt`

### TicketComments
- `id` (Primary Key)
- `ticketId` (foreign key)
- `userId` (commenter)
- `content`
- `isInternal` (private notes)
- Timestamps: `createdAt`

### TicketAttachments
- `id` (Primary Key)
- `ticketId` (foreign key)
- `fileName`, `filePath`, `fileType`, `fileSize`
- `uploadedBy` (userId)
- Timestamps: `uploadedAt`

---

## Architecture Highlights

- **Layered Architecture**: Clear separation between controllers, services, repositories, and entities
- **DTO Pattern**: Decouples internal entities from API contracts
- **Repository Pattern**: Spring Data JPA for database abstraction
- **RESTful Design**: Standard HTTP methods and status codes
- **CORS Enabled**: Backend configured for frontend communication
- **JWT Security**: Token-based authentication
- **Material-UI**: Consistent, responsive UI components

---

## Future Enhancements

- [ ] Email notification integration (SMTP configured but not fully implemented)
- [ ] Ticket attachment upload/download
- [ ] Ticket comments and internal notes
- [ ] Real-time updates via WebSocket
- [ ] Advanced reporting and analytics
- [ ] Ticket escalation rules
- [ ] Knowledge base/article system
- [ ] Multi-tenant support

---

## License

This project is open source and available under the MIT License.
