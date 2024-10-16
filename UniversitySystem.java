import java.util.Scanner;

public class UniversitySystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the University Course Registration System!");
        
        while (true) {
            System.out.println("\n1. Login as Student");
            System.out.println("2. Login as Professor");
            System.out.println("3. Login as Administrator");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 4) {
                System.out.println("Exiting the system. Goodbye!");
                break; // Exit the loop
            }

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            switch (choice) {
                case 1:
                    Student student = new Student(email, password);
                    studentMenu(student, scanner);
                    break;
                case 2:
                    Professor professor = new Professor(email, password);
                    professorMenu(professor, scanner);
                    break;
                case 3:
                    Administrator admin = new Administrator(email, password);
                    adminMenu(admin, scanner);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close(); // Close the scanner to prevent resource leaks
    }

    private static void studentMenu(Student student, Scanner scanner) {
        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. View Schedule");
            System.out.println("4. Track Grades");
            System.out.println("5. Submit Complaint");
            System.out.println("6. check status compalint Complaint");
            System.out.println("7. Drop courses: ");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    student.viewAvailableCourses();
                    break;
                case 2:
                    student.registerForCourse();
                    break;
                case 3:
                    student.viewSchedule();
                    break;
                case 4:
                    student.trackGrades();
                    break;
                case 5:
                    student.submitComplaint();
                    break;
                case 6:
                    student.statusCompalin();
                    break;
                case 7:
                    student.dropCourse();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return; // Exit the student menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void professorMenu(Professor professor, Scanner scanner) {
        while (true) {
            System.out.println("\nProfessor Menu:");
            System.out.println("1. View Assigned Courses");
            System.out.println("2. View Students in a Course");
            System.out.println("3. Change professor course detail : ");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    professor.viewAssignedCourses();
                    break;
                case 2:
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.nextLine();
                    professor.viewStudentsInCourse(courseCode);
                    break;
                case 3:
                    professor.Courseschange();
                case 4:
                    System.out.println("Logging out...");
                    return; // Exit the professor menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu(Administrator admin, Scanner scanner) {
        while (true) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Handle Complaints");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    admin.manageCourseCatalog();
                    break;
                case 2:
                    admin.manageStudentRecords();
                    break;
                case 3:
                    admin.handleComplaints();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return; // Exit the admin menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
