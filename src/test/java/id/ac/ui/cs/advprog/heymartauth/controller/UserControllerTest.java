package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.GetProfileResponse;
import id.ac.ui.cs.advprog.heymartauth.model.User;
import id.ac.ui.cs.advprog.heymartauth.model.UserRole;
import id.ac.ui.cs.advprog.heymartauth.service.JwtService;
import id.ac.ui.cs.advprog.heymartauth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetProfileSuccess() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setRole(UserRole.CUSTOMER);

        when(jwtService.extractEmail(anyString())).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(get("/api/user/profile")
                        .header("Authorization", "Bearer dummyToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.name", is("Test User")))
                .andExpect(jsonPath("$.role", is("CUSTOMER")));
    }
}
