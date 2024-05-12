package id.ac.ui.cs.advprog.heymartauth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testRoleNotExists() {
        assertThrows(IllegalArgumentException.class, () -> User.builder().role("subscriber"));
        assertThrows(IllegalArgumentException.class, () -> User.builder().role(null));
        assertThrows(IllegalArgumentException.class, () -> User.builder().role("1"));
    }

    @Test
    void testRoleExists() {
        assertDoesNotThrow(() -> User.builder().role("admin"));
        assertDoesNotThrow(() -> User.builder().role("customer"));
        assertDoesNotThrow(() -> User.builder().role("manager"));
    }

    @Test
    void testEmailNotValid() {
        assertThrows(IllegalArgumentException.class, () -> User.builder().email("arvint"));
        assertThrows(IllegalArgumentException.class, () -> User.builder().email(null));
    }

    @Test
    void testEmailValid() {
        assertDoesNotThrow(() -> User.builder().email("abcde@gmail.com"));
        assertDoesNotThrow(() -> User.builder().email("arwrwrwrw@gmail.com"));
    }

    @Test
    void testNameNotValid() {
        assertThrows(IllegalArgumentException.class, () -> User.builder().name("abc"));
        assertThrows(IllegalArgumentException.class, () -> User.builder().name(null));
    }

    @Test
    void testNameValid() {
        assertDoesNotThrow(() -> User.builder().name("arvin ciu"));
        assertDoesNotThrow(() -> User.builder().name("abcdwdwrwrwrwr"));
    }

    @Test
    void testPasswordNotValid() {
        assertThrows(IllegalArgumentException.class, () -> User.builder().password("abcd"));
        assertThrows(IllegalArgumentException.class, () -> User.builder().password(null));
    }

    @Test
    void testPasswordValid() {
        assertDoesNotThrow(() -> User.builder().password("arvinciu123"));
        assertDoesNotThrow(() -> User.builder().password("abcdwdwrwrwrwr423"));
    }

    @Test
    void testUserBuild() {
        User user1 = User.builder()
                .name("Arvin")
                .email("arvinciu86@gmail.com")
                .password("adprolancar123")
                .role("aDmiN")
                .build();


        assertEquals("Arvin", user1.getName());
        assertEquals("arvinciu86@gmail.com", user1.getEmail());
        assertEquals("adprolancar123", user1.getPassword());
        assertEquals(UserRole.ADMIN, user1.getRole());
    }
}
