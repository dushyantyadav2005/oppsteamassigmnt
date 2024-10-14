public class Complaint {
    private int complaintId;      // Unique identifier for the complaint
    private int studentId;        // Identifier for the student who made the complaint
    private String complaintText;  // Text of the complaint

    // Constructor to initialize the Complaint object
    public Complaint(int complaintId, int studentId, String complaintText) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.complaintText = complaintText;
    }

    // Getter for complaintId
    public int getComplaintId() {
        return complaintId;
    }

    // Setter for complaintId
    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    // Getter for studentId
    public int getStudentId() {
        return studentId;
    }

    // Setter for studentId
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Getter for complaintText
    public String getComplaintText() {
        return complaintText;
    }

    // Setter for complaintText
    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    // Override toString() for easy representation of the complaint
    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", studentId=" + studentId +
                ", complaintText='" + complaintText + '\'' +
                '}';
    }
}
