package id.ac.ui.cs.advprog.heymartauth.controller;

import id.ac.ui.cs.advprog.heymartauth.dto.*;
import id.ac.ui.cs.advprog.heymartauth.service.AuthService;
import id.ac.ui.cs.advprog.heymartauth.service.JwtService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegisterCustomerSuccess() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest();
        AuthenticationResponse response = new AuthenticationResponse("dummyToken");

        Mockito.when(authService.registerCustomer(any(UserRegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("dummyToken")));
    }

    @Test
    public void testRegisterManagerSuccess() throws Exception {
        ManagerRegisterRequest request = new ManagerRegisterRequest();
        AuthenticationResponse response = new AuthenticationResponse("dummyToken");

        Mockito.when(jwtService.extractRole(any(String.class))).thenReturn("admin");
        Mockito.when(authService.registerManager(any(ManagerRegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/register-manager")
                        .header("Authorization", "Bearer dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"manageruser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("dummyToken")));
    }

    @Test
    public void testRegisterManagerAccessDenied() throws Exception {
        Mockito.when(jwtService.extractRole(any(String.class))).thenReturn("user");

        mockMvc.perform(post("/api/auth/register-manager")
                        .header("Authorization", "Bearer dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"manageruser\",\"password\":\"password\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRemoveManagerSuccess() throws Exception {
        ManagerRemovalRequest request = new ManagerRemovalRequest();

        Mockito.when(jwtService.extractRole(any(String.class))).thenReturn("admin");

        mockMvc.perform(post("/api/auth/remove-manager")
                        .header("Authorization", "Bearer dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"manageruser\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveManagerAccessDenied() throws Exception {
        Mockito.when(jwtService.extractRole(any(String.class))).thenReturn("user");

        mockMvc.perform(post("/api/auth/remove-manager")
                        .header("Authorization", "Bearer dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"manageruser\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        AuthenticationResponse response = new AuthenticationResponse("dummyToken");

        Mockito.when(authService.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("dummyToken")));
    }
}

