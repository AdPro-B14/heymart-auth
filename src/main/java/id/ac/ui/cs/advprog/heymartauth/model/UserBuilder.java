package id.ac.ui.cs.advprog.heymartauth.model;

import java.util.regex.Pattern;

public class UserBuilder {
    private String name;

    private String email;

    private String password;

    private UserRole role;

    private Long managerSupermarketId;

    public UserBuilder name(String name) {
        if (name == null || name.length() < 4) {
            throw new IllegalArgumentException("Name is not valid. Name length must be at least 4.");
        }

        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        if (email == null || !Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}\\b").matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid.");
        }

        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password is not valid. Password length must be at least 8.");
        }

        this.password = password;
        return this;
    }

    public UserBuilder role(String role) {
        if (role == null || !UserRole.contains(role)) {
            throw new IllegalArgumentException("Role does not exists.");
        }

        this.role = UserRole.valueOf(role.toUpperCase());
        return this;
    }

    public UserBuilder managerSupermarketId(Long supermarketId) {
        if (supermarketId == null) {
            throw new IllegalArgumentException("Supermarket id is not valid.");
        }

        this.managerSupermarketId = supermarketId;
        return this;
    }

    public User build() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRole(this.role);
        user.setManagerSupermarketId(this.managerSupermarketId);
        return user;
    }
}
