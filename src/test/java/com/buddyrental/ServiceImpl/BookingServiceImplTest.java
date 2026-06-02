package com.buddyrental.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.buddyrental.DTO.BookingDTO;
import com.buddyrental.Entity.Booking;
import com.buddyrental.Entity.User;
import com.buddyrental.Repository.Booking.BookingHistoryRepository;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.enums.BookingStatus;
import com.buddyrental.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingHistoryRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void createBookingLoadsUserAndPersistsBookingDetails() {
        UUID userId = UUID.randomUUID();
        User user = user(userId);
        BookingDTO request = bookingDto(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setBookingId(UUID.randomUUID());
            return booking;
        });

        BookingDTO createdBooking = bookingService.createBooking(request);

        assertEquals(1200, createdBooking.getTotalPrice());
        assertEquals(300, createdBooking.getadvancePayment());
        assertEquals(BookingStatus.Pending, createdBooking.getBookingStatus());

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        assertEquals(user, bookingCaptor.getValue().getUser());
        assertEquals(PaymentStatus.Pending, bookingCaptor.getValue().getPaymentStatus());
    }

    @Test
    void createBookingRejectsMissingUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.createBooking(bookingDto(userId)));

        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    void updateBookingChangesEditableFields() {
        UUID bookingId = UUID.randomUUID();
        Booking existingBooking = booking(user(UUID.randomUUID()));
        BookingDTO update = bookingDto(UUID.randomUUID());
        update.setTotalPrice(2000);
        update.setadvancePayment(500);
        update.setBookingStatus(BookingStatus.Confirmed);
        update.setPaymentStatus(PaymentStatus.Partial);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingDTO updatedBooking = bookingService.updateBooking(bookingId, update);

        assertEquals(2000, updatedBooking.getTotalPrice());
        assertEquals(500, updatedBooking.getadvancePayment());
        assertEquals(BookingStatus.Confirmed, updatedBooking.getBookingStatus());
        assertEquals(PaymentStatus.Partial, updatedBooking.getPaymentStatus());
    }

    @Test
    void deleteBookingDeletesWhenPresent() {
        UUID bookingId = UUID.randomUUID();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(new Booking()));

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository).deleteById(bookingId);
    }

    @Test
    void getBookingsByDateRangeMapsPagedResults() {
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now();
        PageRequest pageable = PageRequest.of(0, 10);
        when(bookingRepository.findByCreatedAtBetween(start, end, pageable))
                .thenReturn(new PageImpl<>(List.of(booking(user(UUID.randomUUID())))));

        Page<BookingDTO> bookings = bookingService.getBookingsByDateRange(start, end, pageable);

        assertEquals(1, bookings.getTotalElements());
        assertEquals(1200, bookings.getContent().get(0).getTotalPrice());
    }

    private BookingDTO bookingDto(UUID userId) {
        BookingDTO dto = new BookingDTO();
        dto.setUserId(userId);
        dto.setTotalPrice(1200);
        dto.setadvancePayment(300);
        dto.setBookingStatus(BookingStatus.Pending);
        dto.setPaymentStatus(PaymentStatus.Pending);
        return dto;
    }

    private Booking booking(User user) {
        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID());
        booking.setUser(user);
        booking.setTotalPrice(1200);
        booking.setadvancePayment(300);
        booking.setBookingStatus(BookingStatus.Pending);
        booking.setPaymentStatus(PaymentStatus.Pending);
        return booking;
    }

    private User user(UUID userId) {
        User user = new User();
        user.setId(userId);
        user.setName("Customer");
        return user;
    }
}
