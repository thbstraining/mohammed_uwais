package com.example.ticket.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.ticket.Entity.BookingDTO;
import com.example.ticket.Entity.BookingEntity;
import com.example.ticket.Entity.ParkDto;
import com.example.ticket.Entity.TicketEntity;

import com.example.ticket.Service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	@Autowired
	private BookingService bookingService;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/create")
	public ResponseEntity<BookingEntity> createBooking(@RequestBody BookingDTO bookingRequest) {

		String parkServiceUrl = "http://localhost:9070/parks/" + bookingRequest.getParkId();
		String ticketServiceUrl = "http://localhost:9030/api/tickets/available?date=" + bookingRequest.getDate();

		try {

			ResponseEntity<ParkDto> parkResponse = restTemplate.getForEntity(parkServiceUrl, ParkDto.class);
			ParkDto park = parkResponse.getBody();

			TicketEntity[] tickets = restTemplate.getForObject(ticketServiceUrl, TicketEntity[].class);

			double totalAmount = bookingRequest.getChildrenTickets() * tickets[0].getPrice()
					+ bookingRequest.getAdultTickets() * tickets[1].getPrice();

			BookingEntity booking = new BookingEntity();
			booking.setUserId(bookingRequest.getUserId());
			booking.setParkId(bookingRequest.getParkId());
			booking.setTicketId(tickets[0].getId()); // Assuming the first ticket is for children
			booking.setQuantity(bookingRequest.getChildrenTickets() + bookingRequest.getAdultTickets());
			booking.setDate(bookingRequest.getDate());

			BookingEntity savedBooking = bookingService.createBooking(booking);
			return ResponseEntity.ok(savedBooking);
		} catch (HttpClientErrorException.NotFound ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping
	public ResponseEntity<List<BookingEntity>> getAllBookings() {
		List<BookingEntity> bookings = bookingService.getAllBookings();
		return ResponseEntity.ok(bookings);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingEntity> getBookingById(@PathVariable Long id) {
		BookingEntity booking = bookingService.getBookingById(id);
		if (booking != null) {
			return ResponseEntity.ok(booking);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) {
		bookingService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}

}
