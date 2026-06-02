package com.buddyrental.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
        logger.warn("This mail is already registered:{}",userRegisterDTO.getEmail());
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
        user.setRole(Role.CUSTOMER);
        User savedUser=userRepository.save(user);
        logger.info("User created successfully with email:{}",userRegisterDTO.getEmail());
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
        dto.setRole(user.getRole());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setVerified(user.isVerified());
        dto.setRating(user.getRating());
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
            logger.warn("User not found with id:{}",id);
            throw new IllegalArgumentException("User not found with id: " + id);
        }
       logger.info("User deleted successfully with id:{}",id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUser(String email, UserDTO userDTO) {
        
        if (userRepository.findByEmail(email)==null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        User user = userRepository.findByEmail(email).get();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        User savedUser = userRepository.save(user);
        logger.info("User updated successfully with email:{}",email);
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
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> {
                logger.warn("User is not present with email:{}", loginRequest.getEmail());
                return new IllegalArgumentException("User not found");
            });

        String token = jwtService.generateToken(user.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRole(user.getRole());
        logger.info("User logged in successfully with email:{}", loginRequest.getEmail());
        return loginResponse;
}
}
