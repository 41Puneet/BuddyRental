package com.buddyrental.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buddyrental.DTO.VehicleDTO;
import com.buddyrental.Entity.User;
import com.buddyrental.Entity.Vehicle;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.Repository.Vehicle.VehicleRepository;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void addVehicleAssignsOwnerAndDefaultsAvailability() {
        UUID ownerId = UUID.randomUUID();
        User owner = owner(ownerId);
        VehicleDTO request = vehicleDto(ownerId);
        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle vehicle = invocation.getArgument(0);
            vehicle.setVehicleId(UUID.randomUUID());
            return vehicle;
        });

        VehicleDTO addedVehicle = vehicleService.addVehicle(request);

        assertEquals(ownerId, addedVehicle.getownerId());
        assertEquals("Honda", addedVehicle.getBrand());
        assertTrue(addedVehicle.isAvailable());

        ArgumentCaptor<Vehicle> vehicleCaptor = ArgumentCaptor.forClass(Vehicle.class);
        verify(vehicleRepository).save(vehicleCaptor.capture());
        assertEquals(owner, vehicleCaptor.getValue().getOwner());
        assertTrue(vehicleCaptor.getValue().isAvailable());
    }

    @Test
    void addVehicleRejectsMissingOwner() {
        UUID ownerId = UUID.randomUUID();
        when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.addVehicle(vehicleDto(ownerId)));

        assertEquals("Owner not found", exception.getMessage());
    }

    @Test
    void updateVehicleUpdatesExistingVehicle() {
        UUID vehicleId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        Vehicle existingVehicle = vehicle(owner(ownerId));
        existingVehicle.setVehicleId(vehicleId);
        VehicleDTO update = vehicleDto(ownerId);
        update.setBrand("Yamaha");
        update.setAvailable(false);
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VehicleDTO updatedVehicle = vehicleService.updateVehicle(vehicleId, update);

        assertEquals("Yamaha", updatedVehicle.getBrand());
        assertEquals(ownerId, updatedVehicle.getownerId());
        assertEquals(false, updatedVehicle.isAvailable());
    }

    @Test
    void getVehiclesByOwnerIdMapsResults() {
        UUID ownerId = UUID.randomUUID();
        when(vehicleRepository.findByOwnerId(ownerId)).thenReturn(List.of(vehicle(owner(ownerId))));

        List<VehicleDTO> vehicles = vehicleService.getVehiclesByOwnerId(ownerId);

        assertEquals(1, vehicles.size());
        assertEquals(ownerId, vehicles.get(0).getownerId());
    }

    private VehicleDTO vehicleDto(UUID ownerId) {
        VehicleDTO dto = new VehicleDTO();
        dto.setownerId(ownerId);
        dto.setType(VehicleType.BIKE);
        dto.setBrand("Honda");
        dto.setVehicleNumber("BR-01-AB-1234");
        dto.setDescription("City bike");
        dto.setPricePerDay(500);
        dto.setSecurityDeposit(2000);
        dto.setFueltype(Fueltype.PETROL);
        dto.setTransmissionType(TransmissionType.MANUAL);
        dto.setCity("Patna");
        dto.setState("Bihar");
        return dto;
    }

    private Vehicle vehicle(User owner) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(UUID.randomUUID());
        vehicle.setOwner(owner);
        vehicle.setType(VehicleType.BIKE);
        vehicle.setBrand("Honda");
        vehicle.setVehicleNumber("BR-01-AB-1234");
        vehicle.setDescription("City bike");
        vehicle.setPricePerDay(500);
        vehicle.setSecurityDeposit(2000);
        vehicle.setFueltype(Fueltype.PETROL);
        vehicle.setTransmissionType(TransmissionType.MANUAL);
        vehicle.setCity("Patna");
        vehicle.setState("Bihar");
        vehicle.setAvailable(true);
        return vehicle;
    }

    private User owner(UUID ownerId) {
        User owner = new User();
        owner.setId(ownerId);
        owner.setName("Owner");
        return owner;
    }
}
