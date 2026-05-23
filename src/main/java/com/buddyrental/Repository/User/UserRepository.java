package com.buddyrental.Repository.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.buddyrental.Entity.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User>findByEmail(String Email);
    Optional<User>findByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);    
}
