package com.buddyrental.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.buddyrental.enums.BookingStatus;
import com.buddyrental.enums.PaymentStatus;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;

@Entity
@Table(name="booking")
public class Booking {
    @Id
   @GeneratedValue(strategy=GenerationType.UUID)
    private UUID bookingId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    private int totalPrice;
    private int advancePayment;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Booking(){

    }
    public Booking(UUID bookingId, User user, int totalPrice, int advancePayment, BookingStatus bookingStatus, PaymentStatus paymentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookingId = bookingId;
        this.user = user;
        this.totalPrice = totalPrice;
        this.advancePayment = advancePayment;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public UUID getBookingId(){
        return bookingId;
    }
    public void setBookingId(UUID bookingId){
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
    public int getadvancePayment(){
        return advancePayment;
    }
    public void setadvancePayment(int advancePayment){
        this.advancePayment=advancePayment;
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
    @OneToMany(mappedBy="booking" , cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<BookingItem>bookingItems=new ArrayList<>();
    @OneToMany(mappedBy="booking" , cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Payment>payments=new ArrayList<>();
    @PrePersist
public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
}

@PreUpdate
public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
}

}