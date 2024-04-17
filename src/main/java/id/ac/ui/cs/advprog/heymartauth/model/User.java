package id.ac.ui.cs.advprog.heymartauth.model;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private UserRole role;

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }
}
