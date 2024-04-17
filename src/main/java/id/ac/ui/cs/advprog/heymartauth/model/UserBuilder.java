package id.ac.ui.cs.advprog.heymartauth.model;

import lombok.Getter;

import java.util.regex.Pattern;

public class UserBuilder {
    private String name;

    private String email;

    private String password;

    private String role;

    UserBuilder setName(String name) {
        if (name == null || name.length() < 5) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        return this;
    }

    UserBuilder setEmail(String email) {
        if (email == null || !Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}\\b").matcher(email).matches()) {
            throw new IllegalArgumentException();
        }

        this.email = email;
        return this;
    }

    UserBuilder setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException();
        }

        this.password = password;
        return this;
    }

    UserBuilder setRole(String role) {
        if (role == null || !UserRole.contains(role)) {
            throw new IllegalArgumentException();
        }

        this.role = role;
        return this;
    }

    User build() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRole(this.role);
        return user;
    }
}
