package com.buddyrental.Repository.Payment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddyrental.Entity.Payment;



@Repository
public interface PaymentRepository extends JpaRepository<Payment,UUID> {

   List<Payment>findByBookingBookingId(UUID bookingId);
   Optional<Payment>findByPaymentId(UUID paymentId);
   List<Payment>findByUserId(UUID userId);
   Page<Payment>findByBookingUserId(UUID userId, Pageable pageable);
   List<Payment>findByTransactionId(String transactionId);
   


}
