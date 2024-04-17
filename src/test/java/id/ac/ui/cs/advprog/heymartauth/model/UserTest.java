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
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setRole("subscriber"));
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setRole(null));
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setRole("1"));
    }

    @Test
    void testRoleExists() {
        assertDoesNotThrow(() -> User.getBuilder().setRole("admin"));
        assertDoesNotThrow(() -> User.getBuilder().setRole("customer"));
        assertDoesNotThrow(() -> User.getBuilder().setRole("manager"));
    }

    @Test
    void testEmailNotValid() {
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setEmail("arvint"));
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setEmail(null));
    }

    @Test
    void testEmailValid() {
        assertDoesNotThrow(() -> User.getBuilder().setEmail("abcde@gmail.com"));
        assertDoesNotThrow(() -> User.getBuilder().setEmail("arwrwrwrw@gmail.com"));
    }

    @Test
    void testNameNotValid() {
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setName("abc"));
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setName(null));
    }

    @Test
    void testNameValid() {
        assertDoesNotThrow(() -> User.getBuilder().setName("arvin ciu"));
        assertDoesNotThrow(() -> User.getBuilder().setName("abcdwdwrwrwrwr"));
    }

    @Test
    void testPasswordNotValid() {
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setPassword("abcd"));
        assertThrows(IllegalArgumentException.class, () -> User.getBuilder().setPassword(null));
    }

    @Test
    void testPasswordValid() {
        assertDoesNotThrow(() -> User.getBuilder().setPassword("arvinciu123"));
        assertDoesNotThrow(() -> User.getBuilder().setPassword("abcdwdwrwrwrwr423"));
    }
}
