package models;

import java.time.LocalDate;
import java.util.List;

import enums.ReservationState;

public class Reservation {
	private double price;
	private Guest guest;
	private Room room;
	private List<AdditionalAccommodation> services;
	private DateInterval interval;
	private ReservationState state;
	private String desiredRoomType;
	
	public Reservation(Guest guest, Room room, DateInterval interval, List<AdditionalAccommodation> services, ReservationState state, double price) {
		this.room = room;
		this.guest = guest;
		this.interval = interval;
		room.addReservedDate(interval);
		this.services = services;
		this.state = state;
		this.price = price;
	}
	
	public Reservation(Guest guest, String desiredRoomType, DateInterval interval, List<AdditionalAccommodation> services, ReservationState state, double price) {
		this.desiredRoomType = desiredRoomType;
		this.guest = guest;
		this.interval = interval;
		this.services = services;
		this.state = state;
		this.price = price;
	}
	
	public Reservation(Guest guest, String desiredRoomType, DateInterval interval, List<AdditionalAccommodation> services, ReservationState state) {
		this.desiredRoomType = desiredRoomType;
		this.guest = guest;
		this.interval = interval;
		this.services = services;
		this.state = state;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setRoom(Room room) {
		this.room = room;
		this.room.addReservedDate(this.interval);
	}
	
	public String toString() {
		if(getRoom() == null) {
			return guest.getUsername() + "," + desiredRoomType + "," + interval.toString() + "," + getServicesString() + "," + getPrice() + "," + state;	
		}
		else {
			return guest.getUsername() + "," + room.getId() + "," + interval.toString() + "," + getServicesString() + "," + getPrice() +"," + state;
		}
	}
	
	public void addService(AdditionalAccommodation service) {
		services.add(service);
	}
	
	public LocalDate getStartDate() {
		return interval.getStartDate();
	}
	
	public LocalDate getEndDate() {
		return interval.getEndDate();
	}
	
	public ReservationState getReservationState() {
		return state;
	}
	
	public String getServicesString() {
		String serviceString = "";
		if(services.isEmpty()) {
			return serviceString;
		}
		for(AdditionalAccommodation service: getServices()) {
			serviceString += service.toString() + " ";
		}
		return serviceString;
	}
	
	public Guest getGuest() {
		return this.guest;
	}
	
	public void setState(ReservationState state) {
		this.state = state;
	}
	
	public void setServices(List<AdditionalAccommodation> services) {
		this.services = services;
	}
	
	public List<AdditionalAccommodation> getServices(){
		return this.services;
	}
	
	public DateInterval getInterval() {
		return this.interval;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public String getDesiredType() {
		return this.desiredRoomType;
	}
}
