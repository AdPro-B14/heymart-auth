package id.ac.ui.cs.advprog.heymartauth.service;

import id.ac.ui.cs.advprog.heymartauth.model.User;
import id.ac.ui.cs.advprog.heymartauth.repository.UserRepository;
import id.ac.ui.cs.advprog.heymartauth.request.AuthenticationRequest;
import id.ac.ui.cs.advprog.heymartauth.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        String name = "arvin-123";
        String email = "arvin@gmail.com";
        String password = "adpro12345";
        String token = "jwt-token-123";

        doReturn("213921839afwqfn").when(passwordEncoder).encode(password);

        User user = User.getBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setRole("admin")
                .build();

        AuthenticationRequest registerRequest = new AuthenticationRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        doReturn(token).when(jwtService).generateToken(new HashMap<>(), user);
        doReturn(Optional.ofNullable(user)).when(userRepository).findByEmail(email);
    }

    @Test
    void testRegisterValid() {
        String name = "dummy-123";
        String email = "abcdefg@gmail.com";
        String password = "abcdefg123";
        String token = "20e909102-fwaofkwaofk";

        doReturn("213921839affn").when(passwordEncoder).encode(password);

        User user = User.getBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setRole("customer")
                .build();

        AuthenticationRequest registerRequest = new AuthenticationRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        AuthenticationResponse registerResponse = new AuthenticationResponse();
        registerResponse.setToken(token);

        doReturn(token).when(jwtService).generateToken(new HashMap<>(), user);

        assertEquals(registerResponse, authService.register(registerRequest));
    }

    @Test
    void testRegisterNotValid() {
        String email = "abcdefg@gmail.com";
        String password = "abcdefg123";

        AuthenticationRequest registerRequest1 = new AuthenticationRequest();
        registerRequest1.setEmail(email);

        assertThrows(IllegalAccessError.class, () -> authService.register(registerRequest1));

        AuthenticationRequest registerRequest2 = new AuthenticationRequest();
        registerRequest2.setPassword(password);

        assertThrows(IllegalAccessError.class, () -> authService.register(registerRequest2));

        AuthenticationRequest registerRequest3 = new AuthenticationRequest();

        assertThrows(IllegalAccessError.class, () -> authService.register(registerRequest3));
    }

    @Test
    void testAuthenticateValid() {
        AuthenticationRequest authenticateRequest = new AuthenticationRequest();
        authenticateRequest.setEmail("arvin@gmail.com");
        authenticateRequest.setPassword("adpro12345");

        AuthenticationResponse authenticateResponse = new AuthenticationResponse();
        authenticateResponse.setToken("jwt-token-123");

        assertEquals(authenticateResponse, authService.authenticate(authenticateRequest));
    }

    @Test
    void testAuthenticateNotValid() {
        AuthenticationRequest authenticateRequest1 = new AuthenticationRequest();
        authenticateRequest1.setEmail("arvin@gmail.com");
        authenticateRequest1.setPassword("adpro123");

        assertThrows(NoSuchElementException.class, () -> authService.authenticate(authenticateRequest1));

        AuthenticationRequest authenticateRequest2 = new AuthenticationRequest();
        authenticateRequest2.setEmail("arvin@gmail.com");

        assertThrows(NoSuchElementException.class, () -> authService.authenticate(authenticateRequest2));

        AuthenticationRequest authenticateRequest3 = new AuthenticationRequest();
        authenticateRequest3.setPassword("adpro123");

        assertThrows(NoSuchElementException.class, () -> authService.authenticate(authenticateRequest3));
    }
}
