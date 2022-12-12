public class UserData {
    // private instance variables
    private String username;
    private String password;

    public UserData() {
        username = null;
        password = null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
