package com.buddyrental.Repository.Vehicle;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buddyrental.Entity.VehicleAvailability;
import com.buddyrental.enums.AvailabilityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleAvailabilityRepository extends JpaRepository<VehicleAvailability, Long> {
    List<VehicleAvailability> findByVehicleId(UUID vehicleId);

    @Query("""
           SELECT va
           FROM VehicleAvailability va
           WHERE va.vehicle.id = :vehicleId
           AND va.availableFrom <= :endDate
           AND va.availableTill >= :startDate
           """)
    Page<VehicleAvailability> findByVehicleIdAndDateRange(
            @Param("vehicleId") UUID vehicleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    List<VehicleAvailability> findByVehicleIdAndStatus(UUID vehicleId, AvailabilityStatus status);

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
            @Param("vehicleId") UUID vehicleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
