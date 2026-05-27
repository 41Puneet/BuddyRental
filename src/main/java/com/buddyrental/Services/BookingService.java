package com.buddyrental.Services;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import com.buddyrental.DTO.BookingDTO;
import com.buddyrental.enums.BookingStatus;


public interface BookingService {
   Page<BookingDTO>getBookingByUserId(UUID userId, Pageable pageable);
   Page<BookingDTO>findByBookingStatus(BookingStatus bookingStatus,Pageable pageable);
   Page<BookingDTO>findByUserIdAndBookingStatus(UUID userId, BookingStatus bookingStatus, Pageable pageable);
   Page<BookingDTO>getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
   BookingDTO createBooking(BookingDTO bookingDTO);
   Optional<BookingDTO> getBookingById(UUID bookingId);
   BookingDTO updateBooking(UUID bookingId, BookingDTO bookingDTO);
   void deleteBooking(UUID bookingId);
}
