package id.ac.ui.cs.advprog.heymartauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private Long managerSupermarketId;
}
