import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator {
    String email;
    String password;
    List<String> complaints;

    public Administrator(String email) {
        this.email = email;
        this.password = "admin123"; // Fixed password
        this.complaints = new ArrayList<>();
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void manageCourseCatalog(List<Course> courses) {
        System.out.println("Course Catalog:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void manageStudentRecords(List<Student> students) {
        System.out.println("Student Records:");
        for (Student student : students) {
            System.out.println(student.email);
        }
    }

    public void assignProfessorsToCourses(List<Professor> professors, List<Course> courses) {
        System.out.println("Assigning professors to courses.");
        for (Course course : courses) {
            for (Professor professor : professors) {
                if (course.professor.equals(professor.email)) {
                    professor.coursesTaught.add(course);
                }
            }
        }
        System.out.println("Professors assigned to courses successfully.");
    }

    public void handleComplaints(List<Student> students) {
        System.out.println("Handling complaints:");
        for (Student student : students) {
            for (String complaint : student.complaints) {
                System.out.println("Complaint from " + student.email + ": " + complaint);
                System.out.print("Resolve this complaint? (yes/no): ");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("Complaint resolved.");
                } else {
                    System.out.println("Complaint not resolved.");
                }
            }
        }
    }
}
