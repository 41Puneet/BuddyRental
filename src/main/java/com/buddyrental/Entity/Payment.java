package com.buddyrental.Entity;
import com.buddyrental.enums.PaymentMethod;
import com.buddyrental.enums.PaymentStatus;
import com.buddyrental.enums.GatewayName;

public class Payment {
   private int id;
   private Booking booking;
   private int transaction_id;
   private int amount;
   private PaymentMethod paymentMethod;
   private PaymentStatus paymentStatus;
private GatewayName gatewayName;

   public Payment(){

   }
   public Payment(int id,Booking booking,int transaction_id,int amount,PaymentMethod paymentMethod,PaymentStatus paymentStatus,GatewayName gatewayName){
    this.id=id;
    this.booking=booking;
    this.transaction_id=transaction_id;
    this.amount=amount;
    this.paymentMethod=paymentMethod;
    this.paymentStatus=paymentStatus;
    this.gatewayName=gatewayName;
   }
   public int getId(){
    return id;
   }
   public void setId(int id){
    this.id=id;
   }
   public Booking getBooking(){
    return booking;
   }
   public void setBooking(Booking booking){
    this.booking=booking;
   }
   public int getTransactionId(){
    return transaction_id;
   }
   public void setTransactionId(int transaction_id){
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
