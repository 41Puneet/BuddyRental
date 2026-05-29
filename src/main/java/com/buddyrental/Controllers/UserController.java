package com.buddyrental.Controllers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddyrental.ServiceImpl.UserServiceImpl;
import com.buddyrental.DTO.UserDTO;
import com.buddyrental.Entity.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
@PostMapping("/email/{email}")
public UserDTO getUserByEmail(@PathVariable String email){
return userServiceImpl.getUserByEmail(email).orElse(null);
}

}
