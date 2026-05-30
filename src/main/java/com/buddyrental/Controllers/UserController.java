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
import java.util.List;
import com.buddyrental.DTO.UserDTO;
import com.buddyrental.ServiceImpl.UserServiceImpl;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
private final UserServiceImpl userServiceImpl;

public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
}

@PostMapping("/sign-up")
public UserDTO createUser(@RequestBody UserDTO userDTO){
return userServiceImpl.createUser(userDTO);
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
public void deleteUserById(@PathVariable UUID id){
    userServiceImpl.deleteUser(id);
}
@PutMapping("/update/{id}")
public UserDTO updateUser(@PathVariable UUID id,@RequestBody UserDTO userDTO){
    return userServiceImpl.updateUser(id, userDTO);
}
@GetMapping("/users/allUser")
public List<UserDTO> getAllUsers(){
    return userServiceImpl.getAllUsers();
}
}
