package com.example.ticketBookingPlatform.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketBookingPlatform.Entity.ParkEntity;
import com.example.ticketBookingPlatform.Repository.ParkRepository;

@Service
public class ParkService {
	@Autowired
	private ParkRepository parkRepository;

	public List<ParkEntity> getAllParks() {
		return parkRepository.findAll();
	}

	public Optional<ParkEntity> getParkById(Long id) {
		return parkRepository.findById(id);
	}

	public ParkEntity createPark(ParkEntity park) {
		return parkRepository.save(park);
	}

	public void deletePark(Long id) {
		parkRepository.deleteById(id);
	}
}
