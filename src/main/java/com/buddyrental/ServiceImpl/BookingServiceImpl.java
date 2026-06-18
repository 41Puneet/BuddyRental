package com.buddyrental.ServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buddyrental.DTO.BookingDTO;
import com.buddyrental.Entity.Booking;
import com.buddyrental.Repository.Booking.BookingHistoryRepository;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.Services.BookingService;
import com.buddyrental.enums.BookingStatus;



@Service
public class BookingServiceImpl implements BookingService {

    private final BookingHistoryRepository bookingRepository;
    private final UserRepository userRepository;
private final Logger logger=LoggerFactory.getLogger(BookingServiceImpl.class);
    public BookingServiceImpl(BookingHistoryRepository bookingRepository,UserRepository userRepository){
        this.bookingRepository=bookingRepository;
        this.userRepository=userRepository;
    }
    private BookingDTO mapToBookingDTO(Booking booking){
            if(booking==null) return null;
            BookingDTO dto=new BookingDTO();
            dto.setBookingId(booking.getBookingId());
            if (booking.getUser() != null) {
                dto.setUserId(booking.getUser().getId());
            }
            dto.setTotalPrice(booking.getTotalPrice());
            dto.setadvancePayment(booking.getadvancePayment());
            dto.setBookingStatus(booking.getBookingStatus());
            dto.setPaymentStatus(booking.getPaymentStatus());
            return dto;
        }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking =new Booking();
        booking.setUser(userRepository.findById(bookingDTO.getUserId()).orElseThrow(() -> {
            logger.warn("User not found with id: " + bookingDTO.getUserId());
            return new IllegalArgumentException("User not found with id: " + bookingDTO.getUserId());
        }));
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setadvancePayment(bookingDTO.getadvancePayment());
        booking.setBookingStatus(
                bookingDTO.getBookingStatus() == null
                        ? BookingStatus.Pending
                        : bookingDTO.getBookingStatus());
        booking.setPaymentStatus(
                bookingDTO.getPaymentStatus() == null
                        ? com.buddyrental.enums.PaymentStatus.Pending
                        : bookingDTO.getPaymentStatus());
        Booking savedBooking=bookingRepository.save(booking);
        return mapToBookingDTO(savedBooking);
    }

    @Override
    public void deleteBooking(UUID bookingId) {
        Optional<Booking>bookingOpt=bookingRepository.findById(bookingId);
        if(bookingOpt.isPresent()){
            logger.info("Booking deleted successfully with id:{}",bookingId);
            bookingRepository.deleteById(bookingId);
        }else{
            logger.warn("Booking not found with id:{}",bookingId);
            throw new IllegalArgumentException("Booking not found with id: "+bookingId);
        }
        
    }

    @Override
    public Page<BookingDTO> findByBookingStatus(BookingStatus bookingStatus, Pageable pageable) {
        Page<Booking>booking=bookingRepository.findByBookingStatus(bookingStatus,pageable);
        return booking.map(this::mapToBookingDTO);
    }

    @Override
    public Page<BookingDTO> findByUserIdAndBookingStatus(UUID userId, BookingStatus bookingStatus, Pageable pageable) {
        Page<Booking>booking=bookingRepository.findByUserIdAndBookingStatus(userId, bookingStatus, pageable);
        return booking.map(this::mapToBookingDTO);
        
    }

    @Override
    public Optional<BookingDTO> getBookingById(UUID bookingId) {
        Optional<Booking>bookingOpt=bookingRepository.findById(bookingId);
        return bookingOpt.map(this::mapToBookingDTO);
        
    }

    @Override
    public Page<BookingDTO> getBookingByUserId(UUID userId, Pageable pageable) {
       Page<Booking>booking=bookingRepository.findByUserId(userId,pageable);
       return booking.map(this::mapToBookingDTO);
        
    }

    @Override
    public Page<BookingDTO> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<Booking>booking=bookingRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        return booking.map(this::mapToBookingDTO);
        
    }

    @Override
    public BookingDTO updateBooking(UUID bookingId, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.warn("Booking not found with id:{}", bookingId);
                    return new IllegalArgumentException("Booking not found with id: " + bookingId);
                });
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setadvancePayment(bookingDTO.getadvancePayment());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        Booking updatedBooking = bookingRepository.save(booking);
        logger.info("Booking updated successfully with id:{}",bookingId);
        return mapToBookingDTO(updatedBooking);
    }
    
}
