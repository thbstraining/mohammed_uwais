package com.example.ticket.Entity;

public class BookingDTO {

	private Long userId;
	private Long parkId;
	private String parkName;
	private String location;
	private String date;
	private int childrenTickets;
	private int adultTickets;
	private double totalAmount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getChildrenTickets() {
		return childrenTickets;
	}

	public void setChildrenTickets(int childrenTickets) {
		this.childrenTickets = childrenTickets;
	}

	public int getAdultTickets() {
		return adultTickets;
	}

	public void setAdultTickets(int adultTickets) {
		this.adultTickets = adultTickets;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BookingDTO(Long userId, Long parkId, String parkName, String location, String date, int childrenTickets,
			int adultTickets, double totalAmount) {
		super();
		this.userId = userId;
		this.parkId = parkId;
		this.parkName = parkName;
		this.location = location;
		this.date = date;
		this.childrenTickets = childrenTickets;
		this.adultTickets = adultTickets;
		this.totalAmount = totalAmount;
	}

	public BookingDTO() {
		super();
	}

}
