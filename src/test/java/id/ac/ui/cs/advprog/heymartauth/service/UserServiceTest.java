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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        String name = "arvin_12345";
        String email = "arvin@gmail.com";
        String password = "this-should-be-token";

        var user = User.getBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setRole("customer")
                .build();

        userRepository.save(user);
    }

    @Test
    void testFindByEmailValid() {
        User user = userService.findByEmail("arvin@gmail.com");
        assertEquals("arvin_12345", user.getName());
        assertEquals("arvin@gmail.com", user.getEmail());
    }

    @Test
    void testFindByEmailNotValid() {
        assertThrows(NoSuchElementException.class, () -> userService.findByEmail("arvin@gmail.com"));
    }
}
