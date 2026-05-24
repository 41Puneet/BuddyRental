package com.buddyrental.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buddyrental.DTO.UserDTO;
import com.buddyrental.Services.UserService.UserService;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.Entity.User;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDTO createUser(UserDTO userDTO) {
       Optional <User>UserInDB=userRepository.findByEmail(userDTO.getEmail());
      if(UserInDB.isPresent()){
        throw new IllegalArgumentException("Email already exists");
      }
      Optional<User>PhoneInDB=userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if(PhoneInDB.isPresent()){
            throw new IllegalArgumentException("Phone number already exists");
        }
        User user=new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
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
        return userInDB.map(this::mapToUserDTO);
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteUser(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}