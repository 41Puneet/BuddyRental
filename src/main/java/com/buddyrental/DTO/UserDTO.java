package com.buddyrental.DTO;
import java.util.UUID;

import com.buddyrental.enums.Role;

public class UserDTO {
   private UUID id;
    private String fullname;
    private String email;
    private String phoneNumber;
    private Role role;
    private String profilePicture;
    private Boolean isVerified;
    private Double rating;

    public UserDTO() {
    }

    public UserDTO(UUID id, String fullname, String email, String phoneNumber,
                   Role role, String profilePicture, Boolean isVerified,
                   Double rating) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.profilePicture = profilePicture;
        this.isVerified = isVerified;
        this.rating = rating;
    } 

    public UUID getId() {
        return id;
    }
    public void setId(UUID id){
        this.id=id;
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
    public Boolean isVerified() {
        return isVerified;
    }
    public void setVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
}   
