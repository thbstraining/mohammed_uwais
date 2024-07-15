package com.example.ticket.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private double price;
	private String validity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "TicketEntity [id=" + id + ", type=" + type + ", price=" + price + ", validity=" + validity + "]";
	}

	public TicketEntity(Long id, String type, double price, String validity) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.validity = validity;
	}

	public TicketEntity() {
		super();
	}

}
