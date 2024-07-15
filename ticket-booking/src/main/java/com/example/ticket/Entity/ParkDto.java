package com.example.ticket.Entity;

public class ParkDto {
	private Long id;
	private String name;
	private String location;
	private double rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ParkDTO [id=" + id + ", name=" + name + ", location=" + location + ", rating=" + rating + "]";
	}

	public ParkDto(Long id, String name, String location, double rating) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.rating = rating;
	}

	public ParkDto() {
		super();
	}

}