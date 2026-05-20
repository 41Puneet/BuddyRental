package com.buddyrental.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import com.buddyrental.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity

@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
     @Column(nullable=false)
    private String fullname;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    private String password;
     @Column(nullable=false)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profilePicture;
    private boolean isVerified;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(UUID id, String fullname, String email, String password, String phoneNumber,
                Role role, String profilePicture, boolean isVerified,
                Double rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.profilePicture = profilePicture;
        this.isVerified = isVerified;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return fullname;
    }

    public void setName(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @OneToMany(mappedBy="owner" , cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Vehicle>vehicle=new ArrayList<>();
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Booking>bookings=new ArrayList<>();
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<UserDocument>documents=new ArrayList<>();
    @PrePersist
public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
}

@PreUpdate
public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
}
}

