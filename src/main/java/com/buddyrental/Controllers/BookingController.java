package com.buddyrental.Controllers;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buddyrental.DTO.BookingDTO;
import com.buddyrental.Services.BookingService;
import com.buddyrental.enums.BookingStatus;





@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    


    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }

@PostMapping("/createBooking")
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO){
      return bookingService.createBooking(bookingDTO);
    }
@DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable UUID bookingId){
        bookingService.deleteBooking(bookingId);
    }
@GetMapping("/status/{bookingStatus}")
public Page<BookingDTO> findByBookingStatus(@PathVariable BookingStatus bookingStatus, @RequestParam int page, @RequestParam int size){
    Pageable pageable = PageRequest.of(page, size);
    return bookingService.findByBookingStatus(bookingStatus, pageable);
}
@GetMapping("/{bookingId}")
public Optional<BookingDTO> getBookingById(@PathVariable UUID bookingId){
    return bookingService.getBookingById(bookingId);
}
@GetMapping("/userId/{userId}")
public Page<BookingDTO>getBookingByUserId(@PathVariable UUID userId,@RequestParam int page,@RequestParam int size){
    Pageable pageable=PageRequest.of(page, size);
    return bookingService.getBookingByUserId(userId, pageable);
}
@GetMapping("/range/{startDate}/{endDate}")
public Page<BookingDTO> getBookingsByDateRange(@PathVariable String startDate, @PathVariable String endDate, @RequestParam int page, @RequestParam int size){
    LocalDateTime startDateTime = LocalDateTime.parse(startDate);
    LocalDateTime endDateTime = LocalDateTime.parse(endDate);
    Pageable pageable = PageRequest.of(page, size);
    return bookingService.getBookingsByDateRange(startDateTime, endDateTime, pageable);
}       
}

