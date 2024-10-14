import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Professor {
    String email;
    String password;
    List<Course> coursesTaught;

    public Professor(String email, String password) {
        this.email = email;
        this.password = password;
        this.coursesTaught = new ArrayList<>();
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void manageCourses(List<Course> courses) {
        System.out.println("Courses taught by " + email + ":");
        for (Course course : courses) {
            if (course.professor.equals(this.email)) {
                System.out.println(course);
            }
        }
    }

    public void updateCourseDetails(Course course) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Updating course details for " + course.title);
        System.out.println("1. Update Schedule");
        System.out.println("2. Update Credits");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new schedule: ");
                String newSchedule = scanner.nextLine();
                course.schedule = newSchedule;
                System.out.println("Schedule updated.");
                break;
            case 2:
                System.out.print("Enter new credits (2 or 4): ");
                int newCredits = scanner.nextInt();
                if (newCredits == 2 || newCredits == 4) {
                    course.credits = newCredits;
                    System.out.println("Credits updated.");
                } else {
                    System.out.println("Invalid credit value.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void viewEnrolledStudents(List<Course> courses) {
        System.out.println("Enrolled students in your courses:");
        for (Course course : courses) {
            if (course.professor.equals(this.email)) {
                System.out.println(course.title + ": " + course.registeredStudents);
            }
        }
    }
}
