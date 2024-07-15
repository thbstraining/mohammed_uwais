package com.example.ticket.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket.Entity.BookingEntity;
import com.example.ticket.Repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	public List<BookingEntity> getAllBookings() {
		return bookingRepository.findAll();
	}

	public BookingEntity getBookingById(Long id) {
		Optional<BookingEntity> optionalBooking = bookingRepository.findById(id);
		return optionalBooking.orElse(null);
	}

	public BookingEntity createBooking(BookingEntity booking) {
		return bookingRepository.save(booking);
	}

	public void deleteBooking(Long id) {
		bookingRepository.deleteById(id);
	}

}
