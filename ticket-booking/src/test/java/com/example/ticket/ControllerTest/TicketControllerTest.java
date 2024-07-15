package com.example.ticket.ControllerTest;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ticket.Controller.TicketController;
import com.example.ticket.Entity.TicketEntity;
import com.example.ticket.Service.TicketService;

public class TicketControllerTest {

	@Mock
	private TicketService ticketService;

	@InjectMocks
	private TicketController ticketController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllTickets() {
		List<TicketEntity> tickets = new ArrayList<>();
		tickets.add(new TicketEntity(1L, "Child Ticket", 10.0, "One Day"));
		tickets.add(new TicketEntity(2L, "Adult Ticket", 20.0, "Multi Day"));
		when(ticketService.getAllTickets()).thenReturn(tickets);

		ResponseEntity<List<TicketEntity>> response = ticketController.getAllTickets();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	public void testGetTicketById_Found() {
		TicketEntity ticket = new TicketEntity(1L, "Child Ticket", 10.0, "One Day");
		when(ticketService.getTicketById(1L)).thenReturn(Optional.of(ticket));

		ResponseEntity<TicketEntity> response = ticketController.getTicketById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	public void testGetTicketById_NotFound() {
		when(ticketService.getTicketById(1L)).thenReturn(Optional.empty());

		ResponseEntity<TicketEntity> response = ticketController.getTicketById(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testCreateTicket() {
		TicketEntity ticketToCreate = new TicketEntity(null, "New Ticket", 15.0, "One day");
		TicketEntity createdTicket = new TicketEntity(1L, "New Ticket", 15.0, "Multi Day");
		when(ticketService.saveTicket(ticketToCreate)).thenReturn(createdTicket);

		ResponseEntity<TicketEntity> response = ticketController.createTicket(ticketToCreate);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	public void testDeleteTicket() {
		ResponseEntity<Void> response = ticketController.deleteTicket(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());

		verify(ticketService, times(1)).deleteTicket(1L);
	}

	@Test
	public void testGetAvailableTickets() {
		List<TicketEntity> availableTickets = new ArrayList<>();
		availableTickets.add(new TicketEntity(1L, "Child Ticket", 10.0, "One Day"));
		availableTickets.add(new TicketEntity(2L, "Adult Ticket", 20.0, "Multi DSSSsay"));
		when(ticketService.getAvailableTickets("2024-07-15")).thenReturn(availableTickets);

		ResponseEntity<List<TicketEntity>> response = ticketController.getAvailableTickets("2024-07-15");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}
}
