package com.example.ticket.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket.Entity.TicketEntity;
import com.example.ticket.Repository.TicketRepository;

@Service
public class TicketService {
	@Autowired
	private TicketRepository ticketRepository;

	public List<TicketEntity> getAllTickets() {
		return ticketRepository.findAll();
	}

	public TicketEntity saveTicket(TicketEntity ticket) {
		return ticketRepository.save(ticket);
	}

	public void deleteTicket(Long id) {
		ticketRepository.deleteById(id);
	}

	public Optional<TicketEntity> getTicketById(Long id) {
		return ticketRepository.findById(id);
	}

	public List<TicketEntity> getAvailableTickets(String date) {
		return ticketRepository.findAll();
	}
}
