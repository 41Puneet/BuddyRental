package com.buddyrental.ServiceImpl;
import com.buddyrental.DTO.VehicleDTO;
import com.buddyrental.Services.VehicleService.VehicleService;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;
import com.buddyrental.Entity.Vehicle;
import java.util.List;
import java.util.UUID;
import com.buddyrental.Repository.Vehicle.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.Entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{
    private VehicleRepository vehicleRepository;
    private  final UserRepository userRepository;
    public VehicleServiceImpl(UserRepository userRepository,VehicleRepository vehicleRepository){
        this.vehicleRepository=vehicleRepository;
        this.userRepository=userRepository;
    }
    public VehicleServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public VehicleDTO addVehicle(VehicleDTO vehicleDTO) {
       
       User owner = userRepository.findById(vehicleDTO.getownerId())
        .orElseThrow(() ->
                new IllegalArgumentException("Owner not found"));
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
        
        Vehicle savedVehicle=vehicleRepository.save(vehicle);
        return mapToVehicleDTO(savedVehicle);

    }
    private VehicleDTO mapToVehicleDTO(Vehicle vehicle){
        if(vehicle==null) return null;
        VehicleDTO dto=new VehicleDTO();
        dto.setownerId(vehicle.getOwner().getId());
        dto.setType(vehicle.getType());
        dto.setBrand(vehicle.getBrand());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setDescription(vehicle.getDescription());
        dto.setPricePerDay(vehicle.getPricePerDay());
        dto.setSecurityDeposit(vehicle.getSecurityDeposit());
        dto.setFueltype(vehicle.getFueltype());
        dto.setTransmissionType(vehicle.getTransmissionType());
        dto.setCity(vehicle.getCity());
        return dto;
    }

    @Override
    public void deleteVehicle(UUID vehicleId) {
        if(!vehicleRepository.existsById(vehicleId)){
            throw new IllegalArgumentException("Vehicle not found with"+vehicleId);
        }
        vehicleRepository.deleteById(vehicleId);
        
    }

    @Override
    public Page<VehicleDTO> getVehicleByBrand(String brand, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Vehicle>vehicle=vehicleRepository.findByBrandContainingIgnoreCase(brand, pageable);
        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleDTO> getVehicleByCityAndAvailability(String city, boolean isAvailable, int page, int size) {
        Pageable pageable=PageRequest.of(page, size);
        Page<Vehicle>vehicle=vehicleRepository.findByCityAndIsAvailable(city,isAvailable,pageable);
        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleDTO> getVehicleByFilters(VehicleType type, boolean isAvailable, int minPrice, int maxPrice,
            TransmissionType transmissionType, Fueltype fueltype, int page, int size) {
         Pageable pageable=PageRequest.of(page,size);
         Page<Vehicle>vehicle=vehicleRepository.findByTypeAndIsAvailableAndPricePerDayBetweenAndTransmissionTypeAndFueltype(type,isAvailable,minPrice,maxPrice,transmissionType,fueltype,pageable);
        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleDTO> getVehicleByFuelType(Fueltype fuelType, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Vehicle>vehicle=vehicleRepository.findByFueltype(fuelType,pageable);

        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public Optional<VehicleDTO> getVehicleById(UUID vehicleId) {
            Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
            return vehicleOpt.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleDTO> getVehicleByTransmissionType(TransmissionType transmissionType, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Vehicle>vehicle=vehicleRepository.findByTransmissionType(transmissionType,pageable);

        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleDTO> getVehicleByType(VehicleType vehicleType, int page, int size) {
       Pageable pageable=PageRequest.of(page,size);
       Page<Vehicle>vehicle=vehicleRepository.findByType(vehicleType,pageable);
       
        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public List<VehicleDTO> getVehiclesByOwnerId(UUID ownerId) {
            List<Vehicle> vehicles = vehicleRepository.findByOwnerId(ownerId);
            return vehicles.stream().map(this::mapToVehicleDTO).toList();
    }

    @Override
    public VehicleDTO updateVehicle(UUID vehicleId, VehicleDTO vehicleDTO) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        if(vehicleOpt.isPresent()){
            Vehicle vehicle = vehicleOpt.get();
            vehicle.setBrand(vehicleDTO.getBrand());
            vehicle.setType(vehicleDTO.getType());
            vehicle.setAvailable(vehicleDTO.isAvailable());
            vehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
            vehicle.setDescription(vehicleDTO.getDescription());
            vehicle.setPricePerDay(vehicleDTO.getPricePerDay());
            vehicle.setSecurityDeposit(vehicleDTO.getSecurityDeposit());
            vehicle.setFueltype(vehicleDTO.getFueltype());
            vehicle.setTransmissionType(vehicleDTO.getTransmissionType());
            vehicle.setCity(vehicleDTO.getCity());
            Vehicle saved = vehicleRepository.save(vehicle);
            return mapToVehicleDTO(saved);
        }
        throw new IllegalArgumentException("Vehicle not found with id: " + vehicleId);
    }
    
    
}
