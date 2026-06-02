package com.buddyrental.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buddyrental.DTO.PaymentDTO;
import com.buddyrental.Entity.Booking;
import com.buddyrental.Entity.Payment;
import com.buddyrental.Entity.User;
import com.buddyrental.Repository.Booking.BookingHistoryRepository;
import com.buddyrental.Repository.Payment.PaymentRepository;
import com.buddyrental.enums.GatewayName;
import com.buddyrental.enums.PaymentMethod;
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

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingHistoryRepository bookingHistoryRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void createPaymentLoadsBookingAndPersistsDetails() {
        Booking booking = booking(UUID.randomUUID());
        PaymentDTO request = paymentDto(booking.getBookingId());
        when(bookingHistoryRepository.findById(booking.getBookingId())).thenReturn(Optional.of(booking));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            payment.setId(UUID.randomUUID());
            return payment;
        });

        PaymentDTO createdPayment = paymentService.createPayment(request);

        assertEquals(booking.getBookingId(), createdPayment.getBookingId());
        assertEquals("txn-123", createdPayment.getTransactionId());
        assertEquals(PaymentStatus.Paid, createdPayment.getPaymentStatus());

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        assertEquals(booking, paymentCaptor.getValue().getBooking());
        assertEquals(GatewayName.RAZORPAY, paymentCaptor.getValue().getGatewayName());
    }

    @Test
    void createPaymentRejectsMissingBooking() {
        UUID bookingId = UUID.randomUUID();
        when(bookingHistoryRepository.findById(bookingId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.createPayment(paymentDto(bookingId)));

        assertEquals("Booking not found", exception.getMessage());
    }

    @Test
    void cancelPaymentMarksPaymentFailed() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = payment(booking(UUID.randomUUID()), PaymentStatus.Paid);
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentDTO cancelledPayment = paymentService.cancelPayment(paymentId);

        assertEquals(PaymentStatus.Failed, cancelledPayment.getPaymentStatus());
    }

    @Test
    void refundPaymentMarksPaymentRefunded() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = payment(booking(UUID.randomUUID()), PaymentStatus.Paid);
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentDTO refundedPayment = paymentService.refundPayment(paymentId);

        assertEquals(PaymentStatus.Refund, refundedPayment.getPaymentStatus());
    }

    @Test
    void updatePaymentUpdatesEditableFields() {
        UUID paymentId = UUID.randomUUID();
        Booking booking = booking(UUID.randomUUID());
        Payment existingPayment = payment(booking, PaymentStatus.Pending);
        PaymentDTO update = paymentDto(booking.getBookingId());
        update.setTransactionId("txn-456");
        update.setAmount(900);
        update.setPaymentStatus(PaymentStatus.Partial);
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentDTO updatedPayment = paymentService.updatePayment(paymentId, update);

        assertEquals("txn-456", updatedPayment.getTransactionId());
        assertEquals(900, updatedPayment.getAmount());
        assertEquals(PaymentStatus.Partial, updatedPayment.getPaymentStatus());
    }

    @Test
    void getPaymentsByUserIdMapsPagedResults() {
        UUID userId = UUID.randomUUID();
        PageRequest pageable = PageRequest.of(0, 10);
        when(paymentRepository.findByBookingUserId(userId, pageable))
                .thenReturn(new PageImpl<>(List.of(payment(booking(UUID.randomUUID()), PaymentStatus.Paid))));

        Page<PaymentDTO> payments = paymentService.getPaymentsByUserId(userId, pageable);

        assertEquals(1, payments.getTotalElements());
        assertEquals(PaymentStatus.Paid, payments.getContent().get(0).getPaymentStatus());
    }

    private PaymentDTO paymentDto(UUID bookingId) {
        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(bookingId);
        dto.setTransactionId("txn-123");
        dto.setAmount(700);
        dto.setPaymentMethod(PaymentMethod.UPI);
        dto.setPaymentStatus(PaymentStatus.Paid);
        dto.setGatewayName(GatewayName.RAZORPAY);
        return dto;
    }

    private Payment payment(Booking booking, PaymentStatus status) {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setBooking(booking);
        payment.setTransactionId("txn-123");
        payment.setAmount(700);
        payment.setPaymentMethod(PaymentMethod.UPI);
        payment.setPaymentStatus(status);
        payment.setGatewayName(GatewayName.RAZORPAY);
        return payment;
    }

    private Booking booking(UUID bookingId) {
        User user = new User();
        user.setId(UUID.randomUUID());
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setUser(user);
        return booking;
    }
}
