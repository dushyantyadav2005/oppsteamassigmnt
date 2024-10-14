import java.io.Serializable;

abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String email;
    String password;

    User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    abstract boolean login(String email, String password);
}
