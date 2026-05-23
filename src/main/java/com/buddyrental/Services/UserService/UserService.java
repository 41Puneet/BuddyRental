package com.buddyrental.Services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buddyrental.Entity.User;
import com.buddyrental.Repository.User.UserRepository;  


@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

public User createUser(User user){
    Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
    if(existingUserByEmail.isPresent()){
        throw new RuntimeException("Email already exists");
    }
    boolean existingUserByPhoneNumber = userRepository.existsByPhoneNumber(user.getPhoneNumber());
    if(existingUserByPhoneNumber){
        throw new RuntimeException("Phone number already exists");
    }
    return userRepository.save(user);
}

public Optional<User>getUserByEmail(String email){
    return userRepository.findByEmail(email);
}

public Optional<User>getUserByPhoneNumber(String phoneNumber){
    return userRepository.findByPhoneNumber(phoneNumber);
}

public void deleteUser(UUID userId){
    userRepository.deleteById(userId);
}

public User updateUser(User user){
    return userRepository.save(user);
}

public List<User>getAllUsers(){
    return userRepository.findAll();
}
}
