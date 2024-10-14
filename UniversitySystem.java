import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniversitySystem {
    // Class-level variable for courses
    private static List<Course> courses;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        courses = createCourses();  // Initialize courses here
        List<Student> students = createStudents();
        List<Professor> professors = createProfessors();
        List<Administrator> admins = new ArrayList<>();
        admins.add(new Administrator("admin@university.com"));  // Example admin password

        // Assign professors to courses
        assignProfessorsToCourses(professors, courses);

        while (true) {
            System.out.println("Welcome to the University Course Registration System");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Professor");
            System.out.println("3. Login as Administrator");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleStudentLogin(students);
                    break;
                case 2:
                    handleProfessorLogin(professors);
                    break;
                case 3:
                    handleAdminLogin(admins, students);
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static List<Course> createCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS101", "Introduction to Computer Science", "prof1@university.com", 4, new ArrayList<>(), "Mon/Wed 10:00 AM - 11:30 AM"));
        courses.add(new Course("CS102", "Data Structures", "prof2@university.com", 4, List.of("Introduction to Computer Science"), "Tue/Thu 2:00 PM - 3:30 PM"));
        return courses;
    }

    private static List<Student> createStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("student1@university.com", "password1", 1));
        students.add(new Student("student2@university.com", "password2", 2));
        return students;
    }

    private static List<Professor> createProfessors() {
        List<Professor> professors = new ArrayList<>();
        professors.add(new Professor("prof1@university.com", "password1"));
        professors.add(new Professor("prof2@university.com", "password2"));
        return professors;
    }

    private static void assignProfessorsToCourses(List<Professor> professors, List<Course> courses) {
        for (Course course : courses) {
            for (Professor professor : professors) {
                if (course.professor.equals(professor.email)) {
                    professor.coursesTaught.add(course);
                }
            }
        }
    }

    private static void handleStudentLogin(List<Student> students) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Student student : students) {
            if (student.login(email, password)) {
                studentActions(student);
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }

    private static void studentActions(Student student) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. View Schedule");
            System.out.println("4. Track Academic Progress");
            System.out.println("5. Drop a Course");
            System.out.println("6. Submit a Complaint");
            System.out.println("7. View Complaints");
            System.out.println("8. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    student.viewAvailableCourses(courses);
                    break;
                case 2:
                    student.registerCourse(courses);
                    break;
                case 3:
                    student.viewSchedule();
                    break;
                case 4:
                    student.trackAcademicProgress();
                    break;
                case 5:
                    student.dropCourse();
                    break;
                case 6:
                    System.out.print("Enter your complaint: ");
                    String complaint = scanner.nextLine();
                    student.submitComplaint(complaint);
                    break;
                case 7:
                    student.viewComplaints();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleProfessorLogin(List<Professor> professors) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Professor professor : professors) {
            if (professor.login(email, password)) {
                professorActions(professor);
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }

    private static void professorActions(Professor professor) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Manage Courses");
            System.out.println("2. Update Course Details");
            System.out.println("3. View Enrolled Students");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    professor.manageCourses(courses);
                    break;
                case 2:
                    System.out.print("Enter course title to update: ");
                    String title = scanner.nextLine();
                    // Find the course
                    Course courseToUpdate = null;
                    for (Course course : courses) {
                        if (course.title.equals(title) && course.professor.equals(professor.email)) {
                            courseToUpdate = course;
                            break;
                        }
                    }
                    if (courseToUpdate != null) {
                        professor.updateCourseDetails(courseToUpdate);
                    } else {
                        System.out.println("Course not found or you are not assigned to it.");
                    }
                    break;
                case 3:
                    professor.viewEnrolledStudents(courses);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleAdminLogin(List<Administrator> admins, List<Student> students) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Administrator admin : admins) {
            if (admin.login(email, password)) {
                adminActions(admin, students);
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }

    private static void adminActions(Administrator admin, List<Student> students) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Handle Complaints");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    admin.manageCourseCatalog(courses);
                    break;
                case 2:
                    admin.manageStudentRecords(students);
                    break;
                case 3:
                    admin.handleComplaints(students);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
