package com.buddyrental.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.buddyrental.DTO.UserRegisterDTO;
import com.buddyrental.Auth.LoginRequest;
import com.buddyrental.Auth.LoginResponse;
import com.buddyrental.Auth.Security.JwtService;
import com.buddyrental.DTO.UserDTO;
import com.buddyrental.Entity.User;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.Services.UserService.UserService;
import com.buddyrental.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService{

   private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }


    //Create a User Method
    @Override
    public UserDTO createUser(UserRegisterDTO userRegisterDTO) {
       Optional <User>UserInDB=userRepository.findByEmail(userRegisterDTO.getEmail());
      if(UserInDB.isPresent()){
        throw new IllegalArgumentException("Email already exists");
      }
      Optional<User>PhoneInDB=userRepository.findByPhoneNumber(userRegisterDTO.getPhoneNumber());
        if(PhoneInDB.isPresent()){
            throw new IllegalArgumentException("Phone number already exists");
        }
        User user=new User();
        user.setName(userRegisterDTO.getFullname());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        User savedUser=userRepository.save(user);
        return mapToUserDTO(savedUser);
    }
  // Helper function of create Method
    private UserDTO mapToUserDTO(User user){
        if(user==null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> userInDB = userRepository.findByEmail(email);
        if (userInDB.isPresent()) {
            return Optional.of(mapToUserDTO(userInDB.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isPresent()) {
            return Optional.of(mapToUserDTO(userInDB.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        // delete the user if it exists
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        User user = userRepository.findById(id).get();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        User savedUser = userRepository.save(user);
        return mapToUserDTO(savedUser);
     
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDTO).toList();
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );

       return null;
    }
}