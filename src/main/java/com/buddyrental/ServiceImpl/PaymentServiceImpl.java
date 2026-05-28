package com.buddyrental.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.buddyrental.enums.PaymentStatus;
import com.buddyrental.DTO.PaymentDTO;
import com.buddyrental.Entity.Booking;
import com.buddyrental.Entity.Payment;
import com.buddyrental.Repository.Booking.BookingHistoryRepository;
import com.buddyrental.Repository.Payment.PaymentRepository;
import com.buddyrental.Services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingHistoryRepository bookingHistoryRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingHistoryRepository bookingHistoryRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingHistoryRepository = bookingHistoryRepository;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
       Booking booking=bookingHistoryRepository.findById(paymentDTO.getBookingId()).orElseThrow(()->new IllegalArgumentException("Booking not found"));
       Payment payment=new Payment();
       payment.setBooking(booking);
       payment.setTransactionId(String.valueOf(paymentDTO.getTransactionId()));
         payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
            payment.setGatewayName(paymentDTO.getGatewayName());
            Payment savedPayment=paymentRepository.save(payment);
            return mapToDTO(savedPayment);
    }
    private PaymentDTO mapToDTO(Payment payment){
        if(payment==null) return null;
        PaymentDTO dto=new PaymentDTO();
        dto.setId(payment.getId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setTransactionId(payment.getTransactionId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setGatewayName(payment.getGatewayName());
        return dto;
    }

    

    @Override
    public PaymentDTO cancelPayment(UUID paymentId) {
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()->new IllegalArgumentException("Payment not found with this paymentId"+paymentId));
        payment.setPaymentStatus(PaymentStatus.Cancelled);
        Payment cancelledPayment=paymentRepository.save(payment);
        return mapToDTO(cancelledPayment);
    }

    @Override
    public PaymentDTO refundPayment(UUID paymentId) {
        Optional<Payment>payment=paymentRepository.findById(paymentId);
        if(payment.isPresent()){
            Payment existingPayment=payment.get();
            existingPayment.setPaymentStatus(PaymentStatus.Refund);
        }
        throw new IllegalArgumentException("Payment not found");
    }

    @Override
    public Optional<PaymentDTO> getPaymentById(UUID id) {
       Optional<Payment>optPayment=paymentRepository.getPaymentById(id);
     return optPayment.map(this::mapToDTO);
    }

    @Override
    public List<PaymentDTO> getPaymentsByBookingId(UUID bookingId) {
        List<Payment>payment=paymentRepository.findByBookingBookingId(bookingId);
        return payment.stream().map(this::mapToDTO).toList();
    }

    @Override
    public Optional<PaymentDTO> getPaymentsByTransactionId(String transactionId) {
    Optional<Payment>payment=paymentRepository.findByTransactionId(transactionId);
return payment.map(this::mapToDTO);
    }

    @Override
    public Page<PaymentDTO> getPaymentsByUserId(UUID userId, Pageable pageable) {
        Page<Payment>payment=paymentRepository.findByBookingUserId(userId,pageable);
        return payment.map(this::mapToDTO);
    }

    @Override
    public PaymentDTO updatePayment(UUID paymentId, PaymentDTO paymentDTO) {
    Optional<Payment>payment=paymentRepository.findById(paymentId);
    if(payment.isPresent()){
        Payment existingPayment=payment.get();
        existingPayment.setTransactionId(paymentDTO.getTransactionId());
        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        existingPayment.setPaymentStatus(paymentDTO.getPaymentStatus());
        existingPayment.setGatewayName(paymentDTO.getGatewayName());
        Payment updatedPayment=paymentRepository.save(existingPayment);
        return mapToDTO(updatedPayment);
    }
        throw new IllegalArgumentException("Payment not found");
    }
    
}
