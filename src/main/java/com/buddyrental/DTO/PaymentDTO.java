package com.buddyrental.DTO;

import java.util.UUID;
import com.buddyrental.enums.GatewayName;
import com.buddyrental.enums.PaymentMethod;
import com.buddyrental.enums.PaymentStatus;


public class PaymentDTO {
    private UUID id;
   private UUID bookingId;
   private String transactionId;
   private int amount;
   private PaymentMethod paymentMethod;
   private PaymentStatus paymentStatus;
   private GatewayName gatewayName;

   public PaymentDTO(){

   }
   public PaymentDTO(UUID id,UUID bookingId,String transactionId,int amount,PaymentMethod paymentMethod,PaymentStatus paymentStatus,GatewayName gatewayName){
    this.id=id;
    this.bookingId=bookingId;
    this.transactionId=transactionId;
    this.amount=amount; 
    this.paymentMethod=paymentMethod;
    this.paymentStatus=paymentStatus;
    this.gatewayName=gatewayName;

   }
   public UUID getId() {
    return id;
   }
   public void setId(UUID id) {
    this.id = id;
   }
   public UUID getBookingId() {
    return bookingId;
   }
   public void setBookingId(UUID bookingId) {
    this.bookingId = bookingId;
   }
   public String getTransactionId() {
    return transactionId;
   }
   public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
   }
   public int getAmount() {
    return amount;
   }
   public void setAmount(int amount) {
    this.amount = amount;
   }
   public PaymentMethod getPaymentMethod() {
    return paymentMethod;
   }
   public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
   }
   public PaymentStatus getPaymentStatus() {
    return paymentStatus;
   }
   public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
   }
   public GatewayName getGatewayName() {
    return gatewayName;
   }
   public void setGatewayName(GatewayName gatewayName) {
    this.gatewayName = gatewayName;
   }
   
}
