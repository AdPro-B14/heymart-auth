package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.UserRegisterRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.heymartauth.service.AuthService;
import id.ac.ui.cs.advprog.heymartauth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestHeader("Authorization") String id, @RequestBody UserRegisterRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (request.getRole().equalsIgnoreCase("MANAGER")) {
            if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
                throw new IllegalAccessException("You have no access.");
            }
        }

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
