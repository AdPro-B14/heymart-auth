package id.ac.ui.cs.advprog.heymartauth.dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
}
