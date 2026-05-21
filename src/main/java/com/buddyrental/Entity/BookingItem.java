package com.buddyrental.Entity;
import java.time.LocalDateTime;
import com.buddyrental.enums.BookingStatus;
import com.buddyrental.enums.TravelMode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="booking_items")
public class BookingItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pickupLocation;
    private String dropLocation;
    private TravelMode travelMode;
    private int pricePerDay;
    private int totalPrice;
    private int trainNumber;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;


    public BookingItem(){

    }
    public BookingItem(int id,Booking booking,Vehicle vehicle,LocalDateTime startDate,LocalDateTime endDate,String pickupLocation,String dropLocation,TravelMode travelMode,int pricePerDay,int totalPrice,int trainNumber,BookingStatus bookingStatus) {
        this.id = id;
        this.booking = booking;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.travelMode = travelMode;
        this.pricePerDay = pricePerDay;
        this.totalPrice = totalPrice;
        this.trainNumber = trainNumber;
        this.bookingStatus = bookingStatus;
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
    public Vehicle getVehicle(){
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle){
        this.vehicle=vehicle;
    }
    public LocalDateTime getStartDate(){
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate){
        this.startDate=startDate;
    }       
    public LocalDateTime getEndDate(){
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate){
        this.endDate=endDate;   
    }
    public String getPickupLocation(){
        return pickupLocation;
    }
    public void setPickupLocation(String pickupLocation){
        this.pickupLocation=pickupLocation; 
    }
    public String getDropLocation(){
        return dropLocation;
    }
    public void setDropLocation(String dropLocation){
        this.dropLocation=dropLocation;
    }
    public TravelMode getTravelMode(){
        return travelMode;
    }
    public void setTravelMode(TravelMode travelMode){
        this.travelMode=travelMode;
    }
    public int getPricePerDay(){
        return pricePerDay;
    }
    public void setPricePerDay(int pricePerDay){
        this.pricePerDay=pricePerDay;   
    }
    public int getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice){
        this.totalPrice=totalPrice; 
    }
    public int getTrainNumber(){
        return trainNumber;
    }
    public void setTrainNumber(int trainNumber){
        this.trainNumber=trainNumber;
    }
    public BookingStatus getBookingStatus(){
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus){
        this.bookingStatus=bookingStatus;
    }
}
