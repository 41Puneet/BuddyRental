package com.buddyrental.Repository.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buddyrental.Entity.Booking;
import com.buddyrental.enums.BookingStatus;

public interface BookingHistoryRepository
        extends JpaRepository<Booking, UUID> {
    Page<Booking> findByUserId(
            UUID userId,
            Pageable pageable
    );
    Page<Booking> findByBookingStatus(
            BookingStatus bookingStatus,
            Pageable pageable
    );
    Page<Booking> findByUserIdAndBookingStatus(
            UUID userId,
            BookingStatus bookingStatus,
            Pageable pageable
    );

    
    List<Booking> findTop10ByOrderByCreatedAtDesc();
    Page<Booking> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}