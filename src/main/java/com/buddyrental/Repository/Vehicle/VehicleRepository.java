package com.buddyrental.Repository.Vehicle;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buddyrental.Entity.Vehicle;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    
    Page<Vehicle> findByBrandContainingIgnoreCase(String brand, Pageable pageable);
   List<Vehicle> findByOwnerId(UUID ownerId);
   Page<Vehicle>findByCityAndIsAvailable(String city, boolean isAvailable,Pageable pageable);
   Page<Vehicle>findByType(VehicleType vehicleType,Pageable pageable);
    Page<Vehicle>findByFueltype(Fueltype fuelType,Pageable pageable);
    Page<Vehicle>findByTransmissionType(TransmissionType transmissionType,Pageable pageable);
    Page<Vehicle>findByTypeAndIsAvailableAndPricePerDayBetweenAndTransmissionTypeAndFueltype(VehicleType type,boolean isAvailable,int minPrice,int maxPrice,TransmissionType transmissionType,Fueltype fueltype,Pageable pageable);

}
