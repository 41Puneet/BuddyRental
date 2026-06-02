package com.buddyrental.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buddyrental.Auth.LoginRequest;
import com.buddyrental.Auth.LoginResponse;
import com.buddyrental.Auth.Security.JwtService;
import com.buddyrental.DTO.UserDTO;
import com.buddyrental.DTO.UserRegisterDTO;
import com.buddyrental.Entity.User;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserEncodesPasswordAndAssignsCustomerRole() {
        UserRegisterDTO request = registerRequest();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber(request.getPhoneNumber())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            return user;
        });

        UserDTO createdUser = userService.createUser(request);

        assertEquals(request.getEmail(), createdUser.getEmail());
        assertEquals(request.getFullname(), createdUser.getName());
        assertEquals(Role.CUSTOMER, createdUser.getRole());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("encoded-password", userCaptor.getValue().getPassword());
        assertEquals(Role.CUSTOMER, userCaptor.getValue().getRole());
    }

    @Test
    void createUserRejectsDuplicateEmail() {
        UserRegisterDTO request = registerRequest();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(request));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void updateUserUpdatesExistingUserByEmail() {
        User user = user("old@example.com", Role.CUSTOMER);
        UserDTO update = new UserDTO();
        update.setName("Updated User");
        update.setEmail("updated@example.com");
        update.setPhoneNumber("7777777777");
        when(userRepository.findByEmail("old@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO updatedUser = userService.updateUser("old@example.com", update);

        assertEquals("Updated User", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals("7777777777", updatedUser.getPhoneNumber());
    }

    @Test
    void loginAuthenticatesAndReturnsJwtForUserRole() {
        LoginRequest request = new LoginRequest();
        request.setEmail("owner@example.com");
        request.setPassword("secret");
        User user = user("owner@example.com", Role.OWNER);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(request.getEmail())).thenReturn("jwt-token");

        LoginResponse response = userService.login(request);

        assertEquals("jwt-token", response.getToken());
        assertEquals("owner@example.com", response.getEmail());
        assertEquals(Role.OWNER, response.getRole());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void getAllUsersMapsUsersToDtos() {
        when(userRepository.findAll()).thenReturn(List.of(
                user("first@example.com", Role.CUSTOMER),
                user("admin@example.com", Role.ADMIN)));

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getRole() == Role.ADMIN));
    }

    private UserRegisterDTO registerRequest() {
        UserRegisterDTO request = new UserRegisterDTO();
        request.setFullname("Test User");
        request.setEmail("test@example.com");
        request.setPhoneNumber("9999999999");
        request.setPassword("secret");
        return request;
    }

    private User user(String email, Role role) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("User");
        user.setEmail(email);
        user.setPhoneNumber("9999999999");
        user.setPassword("encoded-password");
        user.setRole(role);
        return user;
    }
}
