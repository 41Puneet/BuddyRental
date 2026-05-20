package com.buddyrental.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.buddyrental.enums.BookingStatus;
import com.buddyrental.enums.PaymentStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

@Table(name="Booking")
public class Booking {
    @Id
   @GeneratedValue(strategy=GenerationType.UUID)
    private int bookingId;
    @JoinColumn(name="user_id")
    private User user;
    private int totalPrice;
    private int AdvancePayment;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Booking(){

    }
    public Booking(int bookingId, User user, int totalPrice, int advancePayment, BookingStatus bookingStatus, PaymentStatus paymentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookingId = bookingId;
        this.user = user;
        this.totalPrice = totalPrice;
        this.AdvancePayment = advancePayment;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public int getBookingId(){
        return bookingId;
    }
    public void setBookingId(int bookingId){
        this.bookingId=bookingId;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }

    public int getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice){
        this.totalPrice=totalPrice; 
    }
    public int getAdvancePayment(){
        return AdvancePayment;
    }
    public void setAdvancePayment(int advancePayment){
        this.AdvancePayment=advancePayment;
    }
    public BookingStatus getBookingStatus(){
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus){
        this.bookingStatus=bookingStatus;
    }
    public PaymentStatus getPaymentStatus(){
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus){
        this.paymentStatus=paymentStatus;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt=updatedAt;
    }
    @OneToMany(mappedBy="booking")
    private List<BookingItem>bookingItems=new ArrayList<>();

}