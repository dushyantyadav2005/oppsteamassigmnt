import java.io.Serializable;

class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    String description;
    String status;

    Complaint(String description) {
        this.description = description;
        this.status = "Pending";
    }

    void resolve() {
        this.status = "Resolved";
    }

    @Override
    public String toString() {
        return "Complaint: " + description + " [Status: " + status + "]";
    }
}
