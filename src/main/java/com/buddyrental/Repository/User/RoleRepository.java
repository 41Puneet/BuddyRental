package com.buddyrental.Repository.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.buddyrental.enums.Role;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name); 
}
