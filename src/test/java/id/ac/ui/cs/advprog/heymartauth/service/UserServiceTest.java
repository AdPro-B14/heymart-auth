package id.ac.ui.cs.advprog.heymartauth.service;

import id.ac.ui.cs.advprog.heymartauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.heymartauth.model.User;
import id.ac.ui.cs.advprog.heymartauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindByEmailValid() {
        String name = "arvin_12345";
        String email = "arvin@gmail.com";
        String password = "this-should-be-token";

        var user = User.getBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setRole("customer")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User queriedUser = userService.findByEmail("arvin@gmail.com");
        assertEquals("arvin_12345", queriedUser.getName());
        assertEquals("arvin@gmail.com", queriedUser.getEmail());
    }

    @Test
    void testFindByEmailNotValid() {
        when(userRepository.findByEmail("abc12345@gmail.com")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.findByEmail("abc12345@gmail.com"));
    }
}
