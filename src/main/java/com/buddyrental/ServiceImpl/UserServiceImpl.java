package com.buddyrental.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.buddyrental.DTO.UserRegisterDTO;
import com.buddyrental.DTO.UserDTO;
import com.buddyrental.Entity.User;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.Services.UserService.UserService;
@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
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
        user.setName(userRegisterDTO.name());
        user.setEmail(userRegisterDTO.email());
        user.setPhoneNumber(userRegisterDTO.phoneNumber());
        User savedUser=userRepository.save(user);
        return mapToUserDTO(savedUser);
    }

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

}