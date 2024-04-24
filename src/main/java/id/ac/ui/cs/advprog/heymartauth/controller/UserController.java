package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.*;
import id.ac.ui.cs.advprog.heymartauth.model.User;
import id.ac.ui.cs.advprog.heymartauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfile(@RequestBody GetProfileRequest request) {
        User user = userService.findByEmail(request.email);

        GetProfileResponse response = new GetProfileResponse();
        response.name = user.getName();
        response.email = user.getEmail();
        response.id = user.getId();

        return ResponseEntity.ok(response);
    }
}
