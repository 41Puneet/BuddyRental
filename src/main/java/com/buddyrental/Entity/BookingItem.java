package com.buddyrental.Entity;
import java.time.LocalDateTime;
import com.buddyrental.enums.BookingStatus;
import com.buddyrental.enums.TravelMode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import java.util.UUID;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

    @Entity
    @Table(name="booking_items")
public class BookingItem {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="booking_id")
    private Booking booking;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(nullable=false)
    private String pickupLocation;
    private String dropLocation;
    @Enumerated(EnumType.STRING)
    private TravelMode travelMode;
    private int pricePerDay;
    private int totalPrice;
    private int trainNumber;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

    public BookingItem(){

    }
    public BookingItem(UUID id,Booking booking,Vehicle vehicle,LocalDateTime startDate,LocalDateTime endDate,String pickupLocation,String dropLocation,TravelMode travelMode,int pricePerDay,int totalPrice,int trainNumber,BookingStatus bookingStatus,LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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