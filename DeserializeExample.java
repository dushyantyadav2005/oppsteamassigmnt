import java.io.*;
import java.util.ArrayList;

public class DeserializeExample {
    public static void main(String[] args) {
        String filename = "students.dat"; // Replace with your actual file name

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            // Read the ArrayList from the file
            ArrayList<Student> students = (ArrayList<Student>) ois.readObject();
            
            // Display the content of the ArrayList
            for (Student student : students) {
                System.out.println(student);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
