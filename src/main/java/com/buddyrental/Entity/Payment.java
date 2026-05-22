package com.buddyrental.Entity;
import com.buddyrental.enums.PaymentMethod;
import com.buddyrental.enums.PaymentStatus;
import com.buddyrental.enums.GatewayName;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.util.UUID;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Entity
@Table(name="payment")
public class Payment {
   @Id
   @GeneratedValue(strategy=GenerationType.UUID)
   private UUID id;
   @ManyToOne
   @JoinColumn(name="booking_id")
   private Booking booking;
   private String transaction_id;
   private int amount;
   @Enumerated(EnumType.STRING)
   private PaymentMethod paymentMethod;
   @Enumerated(EnumType.STRING)
   private PaymentStatus paymentStatus;
   @Enumerated(EnumType.STRING)
   private GatewayName gatewayName;

   public Payment(){

   }
   public Payment(UUID id,Booking booking,String transaction_id,int amount,PaymentMethod paymentMethod,PaymentStatus paymentStatus,GatewayName gatewayName){
    this.id=id;
    this.booking=booking;
    this.transaction_id=transaction_id;
    this.amount=amount;
    this.paymentMethod=paymentMethod;
    this.paymentStatus=paymentStatus;
    this.gatewayName=gatewayName;
   }
   public UUID getId(){
    return id;
   }
   public void setId(UUID id){
    this.id=id;
   }
   public Booking getBooking(){
    return booking;
   }
   public void setBooking(Booking booking){
    this.booking=booking;
   }
   public String getTransactionId(){
    return transaction_id;
   }
   public void setTransactionId(String transaction_id){
    this.transaction_id=transaction_id;
   }
   public int getAmount(){
    return amount;
   }
   public void setAmount(int amount){
    this.amount=amount;
   }
   public PaymentMethod getPaymentMethod(){
    return paymentMethod;
   }
   public void setPaymentMethod(PaymentMethod paymentMethod){
    this.paymentMethod=paymentMethod;
   }
   public PaymentStatus getPaymentStatus(){
    return paymentStatus;
   }
   public void setPaymentStatus(PaymentStatus paymentStatus){
    this.paymentStatus=paymentStatus;
   }
   public GatewayName getGatewayName(){
    return gatewayName;
   }
   public void setGatewayName(GatewayName gatewayName){
    this.gatewayName=gatewayName;
   }
 
}
