package com.buddyrental.DTO;
import java.util.UUID;
import com.buddyrental.enums.BookingStatus;
import com.buddyrental.enums.PaymentStatus;



public class BookingDTO {
    private UUID userId;
    private int totalPrice;
    private int advancePayment;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    public BookingDTO(){

    }
    public BookingDTO(UUID userId, int totalPrice, int advancePayment, BookingStatus bookingStatus, PaymentStatus paymentStatus) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.advancePayment = advancePayment;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getadvancePayment() {
        return advancePayment;
    }
    public void setadvancePayment(int advancePayment) {
        advancePayment = advancePayment;
    }
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
