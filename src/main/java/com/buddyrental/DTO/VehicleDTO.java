package com.buddyrental.DTO;
import java.util.UUID;

import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;

public class VehicleDTO {
    public UUID getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }
    private UUID ownerId;
    private UUID vehicleId;
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
    private boolean isAvailable;
    public VehicleDTO(){

    }
    public VehicleDTO(UUID ownerId,UUID VehicleId, VehicleType type, String brand, String vehicleNumber, String description, int pricePerDay, int securityDeposit, Fueltype fueltype, TransmissionType transmissionType, String city, String state, boolean isAvailable) {
        this.ownerId = ownerId;
        this.vehicleId = VehicleId;
        this.type = type;
        this.brand = brand; 
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.securityDeposit = securityDeposit;
        this.fueltype = fueltype;
        this.transmissionType = transmissionType;
        this.city = city;
        this.state = state;
        this.isAvailable = isAvailable;
    }
    public UUID getownerId() {
        return ownerId;
    }
    public void setownerId(UUID ownerId) {
        this.ownerId = ownerId;
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
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }


}
