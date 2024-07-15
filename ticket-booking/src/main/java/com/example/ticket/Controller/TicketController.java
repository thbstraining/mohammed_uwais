package com.example.ticket.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket.Entity.TicketEntity;
import com.example.ticket.Service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
	@Autowired
	private TicketService ticketService;

	@GetMapping
	public ResponseEntity<List<TicketEntity>> getAllTickets() {
		List<TicketEntity> tickets = ticketService.getAllTickets();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TicketEntity> getTicketById(@PathVariable("id") Long id) {
		Optional<TicketEntity> ticket = ticketService.getTicketById(id);
		return ticket.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<TicketEntity> createTicket(@RequestBody TicketEntity ticket) {
		TicketEntity createdTicket = ticketService.saveTicket(ticket);
		return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
		ticketService.deleteTicket(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/available")
	public ResponseEntity<List<TicketEntity>> getAvailableTickets(@RequestParam String date) {
		List<TicketEntity> tickets = ticketService.getAvailableTickets(date);
		return ResponseEntity.ok(tickets);
	}
}
