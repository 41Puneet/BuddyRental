package com.buddyrental.Repository.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import com.buddyrental.Entity.VehicleImage;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehicleImageRepository extends JpaRepository<VehicleImage,UUID>{
    

    List<VehicleImage> findByVehicleId(UUID vehicleId);
    VehicleImage findByVehicleIdAndPrimaryImageTrue(UUID vehicleId);

    void deleteByVehicleId(UUID vehicleId);

}
