package com.buddyrental.ServiceImpl;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.buddyrental.DTO.VehicleDTO;
import com.buddyrental.Entity.Vehicle;
import com.buddyrental.Repository.Vehicle.VehicleRepository;
import com.buddyrental.Services.VehicleService.VehicleService;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleDTO addVehicle(VehicleDTO vehicleDTO) {
       Vehicle vehicle=new Vehicle();
       vehicle.setType(vehicleDTO.getType());
       vehicle.setBrand(vehicleDTO.getBrand());
        vehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        vehicle.setDescription(vehicleDTO.getDescription());
        vehicle.setPricePerDay(vehicleDTO.getPricePerDay());
        vehicle.setSecurityDeposit(vehicleDTO.getSecurityDeposit());
        vehicle.setFueltype(vehicleDTO.getFueltype());
        vehicle.setTransmissionType(vehicleDTO.getTransmissionType());
        vehicle.setCity(vehicleDTO.getCity());
        vehicle.setAvailable(true);
        
        return null;
    }

    @Override
    public void deleteVehicle(UUID vehicleId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Page<VehicleDTO> getVehicleByBrand(String brand, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleDTO> getVehicleByCityAndAvailability(String city, boolean isAvailable, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleDTO> getVehicleByFilters(VehicleType type, boolean isAvailable, int minPrice, int maxPrice,
            TransmissionType transmissionType, Fueltype fueltype, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleDTO> getVehicleByFuelType(Fueltype fuelType, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VehicleDTO getVehicleById(UUID vehicleId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleDTO> getVehicleByTransmissionType(TransmissionType transmissionType, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleDTO> getVehicleByType(VehicleType vehicleType, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VehicleDTO> getVehiclesByOwnerId(UUID ownerId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VehicleDTO updateVehicle(UUID vehicleId, VehicleDTO vehicleDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
