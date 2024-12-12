package com.book.bookmyshow.services;

import com.book.bookmyshow.models.*;
import com.book.bookmyshow.repositories.BookingRepository;
import com.book.bookmyshow.repositories.ShowRepository;
import com.book.bookmyshow.repositories.ShowSeatRepository;
import com.book.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    UserRepository userRepository;
    BookingRepository bookingRepository;
    ShowRepository showRepository;
    ShowSeatRepository showSeatRepository;

    public BookingService(UserRepository userRepository, BookingRepository bookingRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(List<Integer> showSeatIds, int showId, int userId) {

        // 1. Get User using the userId
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        // 2. Get Show using the showID
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException("Show not found");
        }
        Show show = showOptional.get();

        // -------- START TRANSACTION ---------
        // 3. Get ShowSeats via showSeatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        // 4. check if all the seats are available
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new RuntimeException("Selected seat not available");
            }
            }
            //      5. if yes , mark all the seats as "BLOCKED"
        for(ShowSeat showSeat1 : showSeats){
            showSeat1.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat1.setBlockedAt(new Date());
        }
            // 5.1 save the seats in the db
        showSeatRepository.saveAll(showSeats);
            // ------- STOP TRANSACTION ------

        Booking booking = new Booking();
        booking.setBookedBy(user);
        booking.setBookingDate(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(100);
        booking.setShowSeats(showSeats);

        return bookingRepository.save(booking);



    }
}
