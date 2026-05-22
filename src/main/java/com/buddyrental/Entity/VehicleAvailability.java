package com.buddyrental.Entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.buddyrental.enums.AvailabilityStatus;

@Entity
@Table(name = "vehicle_availability")

public class VehicleAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime availableFrom;

    private LocalDateTime availableTill;

    private Boolean available;

    public VehicleAvailability() {

    }
    public VehicleAvailability(Long id, LocalDateTime availableFrom, LocalDateTime availableTill, Boolean available,
            AvailabilityStatus status, Vehicle vehicle) {
        this.id = id;
        this.availableFrom = availableFrom;
        this.availableTill = availableTill;
        this.available = available;
        this.status = status;
        this.vehicle = vehicle;
    }

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}