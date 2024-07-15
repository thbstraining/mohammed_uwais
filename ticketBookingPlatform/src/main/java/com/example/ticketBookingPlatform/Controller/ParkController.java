package com.example.ticketBookingPlatform.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketBookingPlatform.Entity.ParkEntity;
import com.example.ticketBookingPlatform.Service.ParkService;

@RestController
@RequestMapping("/parks")
public class ParkController {
	@Autowired
	private ParkService parkService;

	@GetMapping
	public ResponseEntity<List<ParkEntity>> getAllParks() {
		List<ParkEntity> parks = parkService.getAllParks();
		return new ResponseEntity<>(parks, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ParkEntity> getParkById(@PathVariable("id") Long id) {
		Optional<ParkEntity> park = parkService.getParkById(id);
		return park.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<ParkEntity> createPark(@RequestBody ParkEntity park) {
		ParkEntity createdPark = parkService.createPark(park);
		return new ResponseEntity<>(createdPark, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePark(@PathVariable("id") Long id) {
		parkService.deletePark(id);
		return ResponseEntity.noContent().build();
	}

}
