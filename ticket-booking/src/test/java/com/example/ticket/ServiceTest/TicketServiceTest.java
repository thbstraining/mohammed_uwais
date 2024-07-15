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

import com.example.ticket.Entity.TicketEntity;
import com.example.ticket.Repository.TicketRepository;
import com.example.ticket.Service.TicketService;

public class TicketServiceTest {

	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private TicketService ticketService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllTickets() {
		List<TicketEntity> tickets = new ArrayList<>();
		tickets.add(new TicketEntity(1L, "Child Ticket", 10.0, "One Day"));
		tickets.add(new TicketEntity(2L, "Adult Ticket", 20.0, "Multi day"));
		when(ticketRepository.findAll()).thenReturn(tickets);

		List<TicketEntity> result = ticketService.getAllTickets();

		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId());
	}

	@Test
	public void testGetTicketById_Found() {
		TicketEntity ticket = new TicketEntity(1L, "Child Ticket", 10.0, "One Day");
		when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

		Optional<TicketEntity> result = ticketService.getTicketById(1L);

		assertTrue(result.isPresent());
		assertEquals(1L, result.get().getId());
	}

	@Test
	public void testGetTicketById_NotFound() {
		when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

		Optional<TicketEntity> result = ticketService.getTicketById(1L);

		assertFalse(result.isPresent());
	}

	@Test
	public void testSaveTicket() {
		TicketEntity ticketToSave = new TicketEntity(null, "New Ticket", 15.0, "One Day");
		TicketEntity savedTicket = new TicketEntity(1L, "New Ticket", 15.0, "Multi day");
		when(ticketRepository.save(ticketToSave)).thenReturn(savedTicket);

		TicketEntity result = ticketService.saveTicket(ticketToSave);

		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void testDeleteTicket() {
		ticketService.deleteTicket(1L);

		verify(ticketRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testGetAvailableTickets() {
		List<TicketEntity> tickets = new ArrayList<>();
		tickets.add(new TicketEntity(1L, "Child Ticket", 10.0, "One Day"));
		tickets.add(new TicketEntity(2L, "Adult Ticket", 20.0, "Multi day"));
		when(ticketRepository.findAll()).thenReturn(tickets);

		List<TicketEntity> result = ticketService.getAvailableTickets("2024-07-15");

		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId());
	}
}
