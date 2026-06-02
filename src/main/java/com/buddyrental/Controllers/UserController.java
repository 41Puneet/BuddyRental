package com.buddyrental.Controllers;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;

import com.buddyrental.DTO.UserDTO;
import com.buddyrental.DTO.UserRegisterDTO;
import com.buddyrental.Services.UserService.UserService;
import com.buddyrental.Auth.LoginRequest;
import com.buddyrental.Auth.LoginResponse;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
private final UserService userServiceImpl;

public UserController(UserService userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
}

@PostMapping("/sign-up")
public UserDTO createUser(@RequestBody UserRegisterDTO userRegisterDTO){
return userServiceImpl.createUser(userRegisterDTO);
}
@PostMapping("/get/email/{email}")
public UserDTO getUserByEmail(@PathVariable String email){
return userServiceImpl.getUserByEmail(email).orElse(null);
}
@PostMapping("/get/Id/{id}")
public UserDTO getUserById(@PathVariable UUID id){
    return userServiceImpl.getUserById(id).orElse(null);
}
@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteUserById(@PathVariable UUID id){
    userServiceImpl.deleteUser(id);
    return ResponseEntity.ok("user deleted successfully");
}
@PutMapping("/update")
public ResponseEntity<UserDTO> updateUser(Authentication authentication, @RequestBody UserDTO userDTO){
    return new ResponseEntity<>(userServiceImpl.updateUser(authentication.getName(), userDTO), HttpStatus.OK);
}
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/users/allUser")
public List<UserDTO> getAllUsers(){
    return userServiceImpl.getAllUsers();
}
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
    return new ResponseEntity<>(userServiceImpl.login(loginRequest),HttpStatus.OK);
}

}
