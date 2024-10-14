import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    String email;
    String password;
    int year;
    List<Course> registeredCourses;
    List<String> complaints;

    public Student(String email, String password, int year) {
        this.email = email;
        this.password = password;
        this.year = year;
        this.registeredCourses = new ArrayList<>();
        this.complaints = new ArrayList<>();
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void viewAvailableCourses(List<Course> courses) {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public boolean checkPrerequisites(List<Course> courses, List<String> prerequisites) {
        for (String prerequisite : prerequisites) {
            boolean found = false;
            for (Course course : courses) {
                if (course.title.equals(prerequisite)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public void registerCourse(List<Course> courses) {
        System.out.println("Select a course to register:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).title);
        }
        int choice = new Scanner(System.in).nextInt() - 1;

        if (choice < 0 || choice >= courses.size()) {
            System.out.println("Invalid choice. Registration failed.");
            return;
        }

        Course selectedCourse = courses.get(choice);
        if (checkPrerequisites(registeredCourses, selectedCourse.prerequisites)) {
            registeredCourses.add(selectedCourse);
            selectedCourse.registeredStudents.add(this.email);
            System.out.println("Successfully registered for " + selectedCourse.title);
        } else {
            System.out.println("Prerequisites not met for " + selectedCourse.title);
        }
    }

    public void viewSchedule() {
        System.out.println("Your Schedule:");
        for (Course course : registeredCourses) {
            System.out.println(course.title + " on " + course.schedule);
        }
    }

    public void trackAcademicProgress() {
        System.out.println("Your Academic Progress:");
        // Placeholder for actual academic progress logic
        // You may implement grade tracking here
    }

    public void dropCourse() {
        System.out.println("Select a course to drop:");
        for (int i = 0; i < registeredCourses.size(); i++) {
            System.out.println((i + 1) + ". " + registeredCourses.get(i).title);
        }
        int choice = new Scanner(System.in).nextInt() - 1;

        if (choice < 0 || choice >= registeredCourses.size()) {
            System.out.println("Invalid choice. Drop failed.");
            return;
        }

        Course courseToDrop = registeredCourses.get(choice);
        registeredCourses.remove(courseToDrop);
        courseToDrop.registeredStudents.remove(this.email);
        System.out.println("Successfully dropped " + courseToDrop.title);
    }

    public void submitComplaint(String complaint) {
        complaints.add(complaint);
        System.out.println("Complaint submitted successfully.");
    }

    public void viewComplaints() {
        System.out.println("Your Complaints:");
        if (complaints.isEmpty()) {
            System.out.println("No complaints submitted.");
        } else {
            for (String complaint : complaints) {
                System.out.println(complaint);
            }
        }
    }
}
