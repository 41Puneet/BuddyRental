package com.buddyrental.Repository.Vehicle;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.buddyrental.Entity.VehicleAvailability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;



public interface VehicleAvailabilityRepository extends JpaRepository<VehicleAvailability, Long> {
    List<VehicleAvailability> findByVehicleId(UUID vehicleId);
    Page<VehicleAvailability> findByVehicleIdAndDateRange(UUID vehicleId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    List<VehicleAvailability> findByVehicleIdAndStatus(UUID vehicleId, String status);
    List<VehicleAvailability> findByAvailableFromBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    @Query("""
           SELECT va
           FROM VehicleAvailability va
           WHERE va.vehicle.id = :vehicleId
           AND va.status = 'BOOKED'
           AND (
                :startDate < va.availableTill
                AND :endDate > va.availableFrom
           )
           """)
    List<VehicleAvailability> findOverlappingBookings(
            Long vehicleId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
