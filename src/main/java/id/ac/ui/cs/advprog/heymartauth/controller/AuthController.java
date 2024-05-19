package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.ManagerRegisterRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.UserRegisterRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.heymartauth.service.AuthService;
import id.ac.ui.cs.advprog.heymartauth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(authService.registerCustomer(request));
    }

    @PostMapping("/register-manager")
    public ResponseEntity<AuthenticationResponse> registerManager(@RequestHeader("Authorization") String id, @RequestBody ManagerRegisterRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        return ResponseEntity.ok(authService.registerManager(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
