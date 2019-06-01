package ir.asta.wise.core.response;

public class UserResponseManager {
    private String name;
    private String email;
    private String role;
    private String id;

    public UserResponseManager(String name, String email, String role, String id) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public UserResponseManager(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
