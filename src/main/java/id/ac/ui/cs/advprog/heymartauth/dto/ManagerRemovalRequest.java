package id.ac.ui.cs.advprog.heymartauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRemovalRequest {
    private String email;
    private Long supermarketId;
}
