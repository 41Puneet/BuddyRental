package com.buddyrental.Services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID; 
import com.buddyrental.Auth.LoginRequest;
import com.buddyrental.Auth.LoginResponse;
import com.buddyrental.DTO.UserRegisterDTO;

import com.buddyrental.DTO.UserDTO;

public interface UserService {
    UserDTO createUser(UserRegisterDTO userDTO);
    Optional<UserDTO>getUserByEmail(String email);
    Optional<UserDTO>getUserById(UUID id);
    void deleteUser(UUID id);
    UserDTO updateUser(UUID id,UserDTO userDTO);
    List<UserDTO>getAllUsers();
    LoginResponse login(LoginRequest loginRequest);
}
