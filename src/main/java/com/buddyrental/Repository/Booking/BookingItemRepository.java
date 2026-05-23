package com.buddyrental.Repository.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import com.buddyrental.Entity.BookingItem;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingItemRepository extends JpaRepository<BookingItem, UUID> {
    
}
