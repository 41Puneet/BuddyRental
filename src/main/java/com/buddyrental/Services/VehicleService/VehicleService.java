package com.buddyrental.Services.VehicleService;
import com.buddyrental.DTO.VehicleDTO;
import java.util.UUID;
import org.springframework.data.domain.Page;
import java.util.List; 
import com.buddyrental.enums.VehicleType;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;

public interface VehicleService {
   VehicleDTO addVehicle(VehicleDTO vehicleDTO);
   VehicleDTO getVehicleById(UUID vehicleId);
   VehicleDTO updateVehicle(UUID vehicleId,VehicleDTO vehicleDTO);
   void deleteVehicle(UUID vehicleId);
   Page<VehicleDTO> getVehicleByBrand(String brand,int page,int size);
   List<VehicleDTO> getVehiclesByOwnerId(UUID ownerId);
    Page<VehicleDTO>getVehicleByCityAndAvailability(String city,boolean isAvailable,int page,int size);
    Page<VehicleDTO>getVehicleByType(VehicleType vehicleType,int page,int size);
    Page<VehicleDTO>getVehicleByFuelType(Fueltype fuelType,int page,int size);
    Page<VehicleDTO>getVehicleByTransmissionType(TransmissionType transmissionType,int page,int size);
    Page<VehicleDTO>getVehicleByFilters(VehicleType type,boolean isAvailable,int minPrice,int maxPrice,TransmissionType transmissionType,Fueltype fueltype,int page,int size);
}
