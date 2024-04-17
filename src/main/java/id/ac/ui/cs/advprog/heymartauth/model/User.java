package id.ac.ui.cs.advprog.heymartauth.model;

import lombok.Getter;

public class User {
    @Getter
    private Integer id;

    @Getter
    private String name;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private String role;

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }
}
