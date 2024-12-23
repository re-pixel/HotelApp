package models;

import java.util.ArrayList;
import java.util.List;

import enums.RoomState;



public class Room {
	private String type;
	private int id;
	private List<DateInterval> reservedDates;
	private RoomState state;
	private String[] additionals;
	
	
	public Room(int id, String type, RoomState state, String[] additionals) {
		this.type = type;
		this.id = id;
		this.reservedDates = new ArrayList<>();
		this.state = state;
		this.additionals = additionals;
	}
	
	public String[] getAdditionals() {
		return additionals;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public int getId() {
		return this.id;
	}
	
	public RoomState getRoomState() {
		return state;
	}
	
	public void setState(RoomState state) {
		this.state = state;
	}
	
	public void addReservedDate(DateInterval interval) {
		reservedDates.add(interval);
	}
	
	public boolean isAvailable(DateInterval interval) {
		for(DateInterval reservedDate: reservedDates) {
			if(interval.overlaps(reservedDate)) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		return getId() + "," + getType() + "," + getRoomState() + "," + String.join(" ", getAdditionals());
	}
}
