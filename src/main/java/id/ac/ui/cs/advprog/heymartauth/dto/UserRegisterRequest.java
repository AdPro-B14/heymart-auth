package id.ac.ui.cs.advprog.heymartauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
}
