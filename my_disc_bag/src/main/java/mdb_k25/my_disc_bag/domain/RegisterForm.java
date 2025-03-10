package mdb_k25.my_disc_bag.domain;

import jakarta.validation.constraints.*;

public class RegisterForm {
    @NotEmpty
    @Size(min=3, max=30)
    private String username = "";

    @NotEmpty
    @Size(min=8, max=30)
    private String password = "";

    @NotEmpty
    @Size(min=8, max=30)
    private String passwordCheck = "";

    @NotEmpty
    private String role = "USER";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
