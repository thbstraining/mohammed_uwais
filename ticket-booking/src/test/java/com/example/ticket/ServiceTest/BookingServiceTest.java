package com.example.ticket.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.ticket.Entity.BookingEntity;
import com.example.ticket.Repository.BookingRepository;
import com.example.ticket.Service.BookingService;

public class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;

	@InjectMocks
	private BookingService bookingService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllBookings() {

		List<BookingEntity> bookings = new ArrayList<>();
		bookings.add(new BookingEntity(1L, 1L, 1L, 1L, 5, "2024-07-15"));
		when(bookingRepository.findAll()).thenReturn(bookings);

		List<BookingEntity> result = bookingService.getAllBookings();

		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getId());
	}

	@Test
	public void testGetBookingById_Found() {
		BookingEntity booking = new BookingEntity(1L, 1L, 1L, 1L, 5, "2024-07-15");
		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		BookingEntity result = bookingService.getBookingById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void testGetBookingById_NotFound() {
		when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

		BookingEntity result = bookingService.getBookingById(1L);

		assertNull(result);
	}

	@Test
	public void testCreateBooking() {
		BookingEntity bookingToCreate = new BookingEntity(null, 1L, 1L, 1L, 5, "2024-07-15");
		BookingEntity createdBooking = new BookingEntity(1L, 1L, 1L, 1L, 5, "2024-07-15");
		when(bookingRepository.save(bookingToCreate)).thenReturn(createdBooking);

		BookingEntity result = bookingService.createBooking(bookingToCreate);

		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void testDeleteBooking() {
		bookingService.deleteBooking(1L);

		verify(bookingRepository, times(1)).deleteById(1L);
	}
}
