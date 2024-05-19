package id.ac.ui.cs.advprog.heymartauth.service;

import id.ac.ui.cs.advprog.heymartauth.model.User;
import id.ac.ui.cs.advprog.heymartauth.model.UserRole;
import id.ac.ui.cs.advprog.heymartauth.repository.UserRepository;
import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.UserRegisterRequest;
import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(@RequestHeader("Authorization") String id, UserRegisterRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .managerSupermarketId(request.getManagerSupermarketId())
                .build();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }
        if (user.getRole() == UserRole.ADMIN) {
            throw new IllegalArgumentException("User can not be an admin.");
        }
        if (user.getRole() == UserRole.MANAGER) {
            if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
                throw new IllegalAccessException("You have no access.");
            }
        }

        userRepository.save(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole());

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole());

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
