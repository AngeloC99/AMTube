package it.uniroma1.userManagement.models.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Registration {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Email  cannot be empty")
    @Email(message = "Should be an email")
    private String email;
    @NotBlank(message = "Password  cannot be empty")
    private String password;

    public Registration() { }

    public Registration(@NotBlank(message = "Username cannot be empty") String username, @NotBlank(message = "Email  cannot be empty") String email, @NotBlank(message = "Password  cannot be empty") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
