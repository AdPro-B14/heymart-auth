package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.*;
import id.ac.ui.cs.advprog.heymartauth.model.User;
import id.ac.ui.cs.advprog.heymartauth.service.JwtService;
import id.ac.ui.cs.advprog.heymartauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfile(@RequestHeader("Authorization") String id) {
        String token = id.replace("Bearer ", "");

        String email = jwtService.extractEmail(token);
        User user = userService.findByEmail(email);

        GetProfileResponse response = new GetProfileResponse();
        response.name = user.getName();
        response.email = user.getEmail();
        response.id = user.getId();
        response.role = user.getRole();
        response.manager_id = user.getManagerSupermarketId();

        return ResponseEntity.ok(response);
    }
}
