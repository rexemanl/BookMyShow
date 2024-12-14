package com.book.bookmyshow.controllers;

import com.book.bookmyshow.dtos.BookTicketRequestDTO;
import com.book.bookmyshow.dtos.BookTicketResponseDTO;
import com.book.bookmyshow.dtos.ResponseStatus;
import com.book.bookmyshow.models.Booking;
import com.book.bookmyshow.services.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class BookingController {

    @Autowired
    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    BookTicketResponseDTO bookTicket(BookTicketRequestDTO request){

        BookTicketResponseDTO response = new BookTicketResponseDTO();


        try{
            Booking booking  = bookingService.bookTicket(
                    request.getShowSeatIds(),
                    request.getShowId(),
                    request.getUserId()
            );

            response.setBookingId(booking.getId());
            response.setAmount(booking.getAmount());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            System.out.println(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
