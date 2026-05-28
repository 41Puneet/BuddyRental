package com.buddyrental.ServiceImpl;
import com.buddyrental.DTO.PaymentDTO;
import com.buddyrental.Services.PaymentService;
import com.buddyrental.Repository.Booking.BookingHistoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.buddyrental.Repository.Payment.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.buddyrental.Entity.Booking;
import com.buddyrental.Entity.Payment;

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
       payment.setTransactionId(paymentDTO.getTransactionId());
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
    public void deletePayment(UUID paymentId) {
        
        
    }

    @Override
    public Optional<PaymentDTO> getPaymentById(UUID paymentId) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<PaymentDTO> getPaymentsByBookingId(UUID bookingId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<PaymentDTO> getPaymentsByTransactionId(String transactionId) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Page<PaymentDTO> getPaymentsByUserId(UUID userId, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentDTO updatePayment(UUID paymentId, PaymentDTO paymentDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
