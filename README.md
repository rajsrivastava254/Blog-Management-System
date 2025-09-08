# Blog Management System

A **Spring Boot-based backend system** for managing blog posts with full CRUD operations. Provides REST APIs for creating, retrieving, updating, and deleting posts in a MySQL database.  
The project also has a **React-based frontend** for a complete blogging experience.

🚀 **Live Demo (Backend):** [https://blog-management-system-qk02.onrender.com](https://blog-management-system-qk02.onrender.com)  
🌐 **Live Demo (Frontend):** [https://blog-management-system-frontend-three.vercel.app/](https://blog-management-system-frontend-three.vercel.app/)

---

## Features
- ✍️ Create new blog posts  
- 📖 Read all posts with pagination support  
- ✏️ Update existing posts  
- 🗑️ Delete posts by ID  
- 🌐 RESTful API design  
- 🗄️ Integration with MySQL database using JPA/Hibernate  
- 🎨 React frontend for UI  
- ☁️ Deployed on **Render** (backend) & **Vercel** (frontend)  

---

## Technology Stack
### Backend
- **Java 21**  
- **Spring Boot**  
- **Spring Data JPA**  
- **MySQL**  
- **Maven**  
- **Docker** (optional containerization)  
- **Render** (cloud deployment)  

### Frontend
- **React.js**  
- **JavaScript (ES6+)**  
- **Vercel** (cloud deployment)  

---

## Installation

### 1. Clone the repository
```bash
git clone https://github.com/rajsrivastava254/Blog-Management-System.git
cd Blog-Management-System

## API Endpoints

| Method | Endpoint      | Description                         |
|--------|--------------|-------------------------------------|
| GET    | `/posts`     | Retrieve all posts (with pagination) |
| GET    | `/posts/{id}`| Retrieve a post by ID                |
| POST   | `/posts`     | Create a new post                    |
| PUT    | `/posts/{id}`| Update an existing post              |
| DELETE | `/posts/{id}`| Delete a post by ID                  |

