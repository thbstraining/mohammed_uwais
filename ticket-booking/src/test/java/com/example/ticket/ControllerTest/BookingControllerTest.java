package com.example.ticket.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.ticket.Controller.BookingController;
import com.example.ticket.Entity.BookingDTO;
import com.example.ticket.Entity.BookingEntity;
import com.example.ticket.Entity.ParkDto;
import com.example.ticket.Entity.TicketEntity;
import com.example.ticket.Service.BookingService;

public class BookingControllerTest {

	@Mock
	private BookingService bookingService;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private BookingController bookingController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateBooking_Success() {
		BookingDTO bookingRequest = new BookingDTO(1L, 1L, "Park A", "Location A", "2024-07-15", 2, 3, 0.0);

		ParkDto parkDto = new ParkDto(1L, "Park A", "Location A", 4.5);
		when(restTemplate.getForEntity("http://localhost:9070/parks/1", ParkDto.class))
				.thenReturn(new ResponseEntity<>(parkDto, HttpStatus.OK));

		TicketEntity[] tickets = { new TicketEntity(1L, "Child Ticket", 10.0, "One Day"),
				new TicketEntity(2L, "Adult Ticket", 20.0, "Multi Day") };
		when(restTemplate.getForObject("http://localhost:9030/api/tickets/available?date=2024-07-15",
				TicketEntity[].class)).thenReturn(tickets);

		BookingEntity savedBooking = new BookingEntity();
		savedBooking.setId(1L);
		when(bookingService.createBooking(any(BookingEntity.class))).thenReturn(savedBooking);

		ResponseEntity<BookingEntity> response = bookingController.createBooking(bookingRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	public void testCreateBooking_ParkNotFound() {
		BookingDTO bookingRequest = new BookingDTO(1L, 1L, "Park A", "Location A", "2024-07-15", 2, 3, 0.0);

		when(restTemplate.getForEntity("http://localhost:9070/parks/1", ParkDto.class))
				.thenThrow(HttpClientErrorException.NotFound.class);

		ResponseEntity<BookingEntity> response = bookingController.createBooking(bookingRequest);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testCreateBooking_InternalServerError() {
		BookingDTO bookingRequest = new BookingDTO(1L, 1L, "Park A", "Location A", "2024-07-15", 2, 3, 0.0);

		ParkDto parkDto = new ParkDto(1L, "Park A", "Location A", 4.5);
		when(restTemplate.getForEntity("http://localhost:9070/parks/1", ParkDto.class))
				.thenReturn(new ResponseEntity<>(parkDto, HttpStatus.OK));

		when(restTemplate.getForObject("http://localhost:9030/api/tickets/available?date=2024-07-15",
				TicketEntity[].class)).thenThrow(new RuntimeException("Failed to fetch tickets"));

		ResponseEntity<BookingEntity> response = bookingController.createBooking(bookingRequest);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testGetAllBookings() {
		List<BookingEntity> bookings = new ArrayList<>();
		bookings.add(new BookingEntity(1L, 1L, 1L, 1L, 5, "2024-07-15"));
		when(bookingService.getAllBookings()).thenReturn(bookings);

		ResponseEntity<List<BookingEntity>> response = bookingController.getAllBookings();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	public void testGetBookingById_Found() {
		BookingEntity booking = new BookingEntity(1L, 1L, 1L, 1L, 5, "2024-07-15");
		when(bookingService.getBookingById(1L)).thenReturn(booking);

		ResponseEntity<BookingEntity> response = bookingController.getBookingById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	public void testGetBookingById_NotFound() {
		when(bookingService.getBookingById(1L)).thenReturn(null);

		ResponseEntity<BookingEntity> response = bookingController.getBookingById(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testDeleteBooking() {
		ResponseEntity<Void> response = bookingController.deleteBooking(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());

		verify(bookingService, times(1)).deleteBooking(1L);
	}
}
