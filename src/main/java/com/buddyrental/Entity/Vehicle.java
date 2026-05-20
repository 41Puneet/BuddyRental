package com.buddyrental.Entity;
import java.util.UUID;
import java.time.LocalDateTime;

import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;

import java.util.ArrayList;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
   private UUID id;
   @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private VehicleType type;
    private String brand;
    private String vehicleNumber;
    private String description;
    private int pricePerDay;
    private int securityDeposit;
    private Fueltype fueltype;
    private TransmissionType transmissionType;
    private String city;
    private String state;
    private Double longitude;
    private Double latitude;
    private boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

    public Vehicle(){

    }
    public Vehicle(UUID id,User owner, VehicleType type, String brand, String vehicleNumber, String description ,int pricePerDay, int secuityDeposit, Fueltype fueltype, TransmissionType transmissionType, String City, String state, Double longitude, Double latitude, boolean isAvailable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.owner = owner;
        this.type = type;
        this.brand = brand;
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.securityDeposit = secuityDeposit;
        this.fueltype = fueltype;
        this.transmissionType = transmissionType;   
        this.city = City;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
        public UUID getId() {
        return id;
        }
        public void setId(UUID id) {
        this.id = id;
        }
        public User getOwner() {
        return owner;
        }
        public void setOwner(User owner) {
        this.owner = owner;
        }
        public VehicleType getType() {
        return type;
        }
        public void setType(VehicleType type) {
        this.type = type;
        }
        public String getBrand() {
        return brand;   
   
        }
        public void setBrand(String brand) {
        this.brand = brand;
        }
        public String getVehicleNumber() {
        return vehicleNumber;   
        }
        public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        }
        public String getDescription() {
        return description;
        }
        public void setDescription(String description) {
        this.description = description;
        }
        public int getPricePerDay() {   
        return pricePerDay;
        }
        public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
        }
        public int getSecurityDeposit() {
        return securityDeposit;
        }
        public void setSecurityDeposit(int securityDeposit) {
        this.securityDeposit = securityDeposit;
        }
        public Fueltype getFueltype() {
        return fueltype;    
        }
        public void setFueltype(Fueltype fueltype) {
        this.fueltype = fueltype;
        }
        public TransmissionType getTransmissionType() {
        return transmissionType;
        }
        public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
        }
        public String getCity() {
        return city;
        }
        public void setCity(String city) {
        this.city = city;
        }
        public String getState() {
        return state;
        }
        public void setState(String state) {
        this.state = state;
        }
        public Double getLongitude() {
        return longitude;
        }
        public void setLongitude(Double longitude) {
        this.longitude = longitude;
        }
        public Double getLatitude() {
        return latitude;
        }
        public void setLatitude(Double latitude) {
        this.latitude = latitude;
        }
        public boolean isAvailable() {
        return isAvailable;
        }
        public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        }
        public LocalDateTime getCreatedAt() {
        return createdAt;
        }
        public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;}
        public LocalDateTime getUpdatedAt() {
        return updatedAt;
        }
        public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        }   
        @PrePersist
public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
}

@PreUpdate
public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
}
      @OneToMany(mappedBy="vehicle" ,fetch=FetchType.LAZY)
        private List<BookingItem>bookingItems=new ArrayList<>();
        @OneToMany(mappedBy="vehicle" ,fetch=FetchType.LAZY)
        private List<VehicleImage>VehicleImage=new ArrayList<>();
}
