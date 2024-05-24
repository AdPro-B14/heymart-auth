package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.*;
import id.ac.ui.cs.advprog.heymartauth.service.AuthService;
import id.ac.ui.cs.advprog.heymartauth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(authService.registerManager(request));
    }

    @PostMapping("/remove-manager")
    public ResponseEntity<Void> removeManager(@RequestHeader("Authorization") String id, @RequestBody ManagerRemovalRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        authService.removeManager(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
