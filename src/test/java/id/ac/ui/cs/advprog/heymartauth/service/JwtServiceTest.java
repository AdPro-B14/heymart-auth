package id.ac.ui.cs.advprog.heymartauth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Date date;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", "d50d67cb94cc26a89470f44bc5733583352b3daab178251c7d3e88af33c7afa39525f8d317500951ed6eb0fdfd1bd6b6a71c95f4b9437aaefe70d72c95f1a57dd5e50b92ca05318c682ead6ffa6ebc5f9bf15bc4902c6d4c9074222b972911814729ba4486adb8fde27c489b89e29d577cf10c013689874156396fbe9970735d8d7ec73abc7c3bc54f76c59ff0ffae77d66b0414d0ab8ac73ac9cfc0c8e14c24916d533553423f298104ad55ca893be30949ee14d2da86189e5d64d2cd900e8b7788f615ce9e588aee0d2721325733a58318c2c36980e0f30cd5faf29e042075326913615d0ab8ba42138a2316ee16251a3fe51a11ccee0d84ca8bfe8b12bb67");
        ReflectionTestUtils.setField(jwtService, "EXPIRE_DURATION", 1000L * 60L * 60L);
    }

    @Test
    public void testExtractUserId() {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(Map.of("userId", 123L), userDetails);
        Long userId = jwtService.extractUserId(token);
        assertEquals(123L, userId);
    }

    @Test
    public void testExtractEmail() {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(userDetails);
        String email = jwtService.extractEmail(token);
        assertEquals("test@example.com", email);
    }

    @Test
    public void testExtractRole() {
        String token = createToken(Map.of("role", "CUSTOMER"));
        String role = jwtService.extractRole(token);
        assertEquals("CUSTOMER", role);
    }

    @Test
    public void testGenerateToken() {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testIsTokenValid() {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

//    @Test
//    public void testIsTokenExpired() {
//        when(userDetails.getUsername()).thenReturn("test@example.com");
//        when(new Date(any())).thenReturn(new Date(""))
//        String token = jwtService.generateToken(userDetails);
//
//        boolean isExpired = jwtService.isTokenExpired(token);
//        assertTrue(isExpired);
//    }

    @Test
    public void testExtractClaim() {
        String token = createToken(new HashMap<>());
        Date extractedExpiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertNotNull(extractedExpiration);
    }

    private String createToken(Map<String, Object> claims) {
        return jwtService.generateToken(claims, userDetails);
    }
}

