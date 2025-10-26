# University Management System (OOP Assignment)

Repository: https://github.com/dushyantyadav2005/oppsteamassigmnt  
Database Design PDF: (https://drive.google.com/file/d/1QP5pkkm4yuDKJYj7GnzL_i9Nkt7NGKHK/view?usp=drive_link)

This project is a Java-based University Management System demonstrating Object-Oriented Programming concepts, where Administrator, Professor, and Student roles interact with Courses and Enrollments. It includes modular design, layered architecture, and database connectivity with JDBC.

## Features
- Administrator can add/manage Students, Professors, Courses, assign professors to courses, and enroll students.
- Professors can view courses assigned and enrolled students.
- Students can view profile details and enrolled courses.
- Basic data flow for academic management.

## OOP Concepts Demonstrated
Encapsulation (private fields + getters/setters), Abstraction (role-based behaviors), Modularity (dedicated manager classes), Class-level organization for clarity and separation of responsibilities.

## Architecture Overview
<img width="1395" height="818" alt="Screenshot 2025-08-21 075353" src="https://github.com/user-attachments/assets/911a93ec-5d2f-418e-948e-d2d0909e2bb6" />
- Presentation Layer: `UniversitySystem.java` (menu-based user interaction)
- Business Logic Layer: `Administrator.java`, `Professor.java`, `Student.java`, `CourseManager.java`, `StudentManager.java`
- Persistence Layer: `DatabaseConnection.java` handles JDBC and database connectivity
This layered approach simplifies maintenance and scalability.

## Code Structure
/oppsteamassigmnt  
├─ UniversitySystem.java  
├─ Administrator.java  
├─ Professor.java  
├─ Student.java  
├─ CourseManager.java  
├─ StudentManager.java  
├─ DatabaseConnection.java  
└─ database.pdf (schema reference)

## Database (Referenced in PDF)
Core tables recommended:
Students(id, name, email, year), Professors(id, name, department), Courses(id, title, credits), Student_Course(student_id, course_id), Professor_Course(professor_id, course_id). Optional: grades table for evaluation tracking.
PDF link: https://drive.google.com/file/d/1QP5pkkm4yuDKJYj7GnzL_i9Nkt7NGKHK/view?usp=drive_link

## How to Run
1. Clone the project:
git clone https://github.com/dushyantyadav2005/oppsteamassigmnt
cd oppsteamassigmnt

2. Update database credentials inside `DatabaseConnection.java` (URL/user/password).

3. Compile:
javac *.java

4. Run:
java UniversitySystem

Use the interactive menu to choose role operations.

## Example Workflow
Administrator → add Professor → create Course → assign Professor to Course → add Student → enroll Student.  
Student logs in → views enrolled Courses.  
Professor logs in → views Student list in their Course.

## Technologies Used
Java Core, JDBC Database Connectivity, CLI menu interaction.

## Limitations and Improvements
Currently:
- Console-only interface
- Minimal validation
Future Improvements:
- GUI (Swing/JavaFX)
- REST API (Spring Boot)
- Data Access Object layer
- Logging (Log4j)


## Author
Dushyant Yadav  
GitHub: https://github.com/dushyantyadav2005
