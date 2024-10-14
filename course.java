import java.util.ArrayList;
import java.util.List;

public class Course {
    String code;
    String title;
    String professor;
    int credits;
    List<String> prerequisites;
    String schedule;
    List<String> registeredStudents;

    public Course(String code, String title, String professor, int credits, List<String> prerequisites, String schedule) {
        this.code = code;
        this.title = title;
        this.professor = professor;
        this.credits = credits;
        this.prerequisites = prerequisites;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Course Code: " + code + ", Title: " + title + ", Professor: " + professor +
                ", Credits: " + credits + ", Schedule: " + schedule + ", Prerequisites: " + prerequisites;
    }
}
