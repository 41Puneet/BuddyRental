package com.buddyrental.Services;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.buddyrental.DTO.PaymentDTO;
import java.util.Optional;

public interface PaymentService {
    List<PaymentDTO> getPaymentsByBookingId(UUID bookingId);
    Page<PaymentDTO> getPaymentsByUserId(UUID userId, Pageable pageable);
    Optional   <PaymentDTO> getPaymentsByTransactionId(String transactionId);
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    Optional<PaymentDTO> getPaymentById(UUID paymentId);
    PaymentDTO updatePayment(UUID paymentId, PaymentDTO paymentDTO);
    void deletePayment(UUID paymentId);
}
