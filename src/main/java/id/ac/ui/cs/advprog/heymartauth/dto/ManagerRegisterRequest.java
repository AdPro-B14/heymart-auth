package id.ac.ui.cs.advprog.heymartauth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRegisterRequest {
    private String name;
    private String email;
    private String password;
    private Long supermarketId;
}
