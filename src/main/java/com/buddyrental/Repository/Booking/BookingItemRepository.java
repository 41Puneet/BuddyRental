package com.buddyrental.Repository.Booking;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddyrental.Entity.BookingItem;
import com.buddyrental.enums.BookingStatus;

@Repository
public interface BookingItemRepository extends JpaRepository<BookingItem, UUID> {
    

    Page<BookingItem> findByBookingBookingId(UUID bookingId,Pageable pageable);

    List<BookingItem>findByVehicleIdAndBookingStatus(UUID vehicleId, BookingStatus status);

    List<BookingItem>findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<BookingItem>findByVehicleAndStartDateBetween(UUID vehicleId, LocalDateTime startDate, LocalDateTime endDate);
}
