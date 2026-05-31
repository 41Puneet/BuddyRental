package com.buddyrental.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.buddyrental.DTO.PaymentDTO;
import com.buddyrental.Services.PaymentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createPayment(paymentDTO);
    }

    @PutMapping("/cancel/{paymentId}")
    public PaymentDTO cancelPayment(@PathVariable UUID paymentId) {
        return paymentService.cancelPayment(paymentId);
    }

    @PutMapping("/refund/{paymentId}")
    public PaymentDTO refundPayment(@PathVariable UUID paymentId) {
        return paymentService.refundPayment(paymentId);
    }

    @GetMapping("/{id}")
    public Optional<PaymentDTO> getPaymentById(@PathVariable UUID id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/booking/{bookingId}")
    public List<PaymentDTO> getPaymentsByBookingId(@PathVariable UUID bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }

    @GetMapping("/transaction/{transactionId}")
    public Optional<PaymentDTO> getPaymentsByTransactionId(@PathVariable String transactionId) {
        return paymentService.getPaymentsByTransactionId(transactionId);
    }

    @GetMapping("/user/{userId}")
    public Page<PaymentDTO> getPaymentsByUserId(
            @PathVariable UUID userId,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentService.getPaymentsByUserId(userId, pageable);
    }

    @PutMapping("/update/{paymentId}")
    public PaymentDTO updatePayment(@PathVariable UUID paymentId, @RequestBody PaymentDTO paymentDTO) {
        return paymentService.updatePayment(paymentId, paymentDTO);
    }

}
