package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class Staff extends User{
	private String expertise;
	private int experience;
	private int payrate;
	private Role role;
	private List<Room> roomsToClean;
	
	
	public Staff(String name, String surname, Role role) {
		super(name, surname);
		this.role = role;
		if(role.equals(Role.CLEANER)) {
			this.roomsToClean = new ArrayList<>();
		}
	}
	
	public Staff(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password, String expertise, int experience, int payrate, Role role) {
		super(name, surname, gender, dateOfBirth, phone, address, username, password);
		this.experience = experience;
		this.expertise = expertise;
		this.payrate = payrate;
		this.role = role;
	}
	
	public Staff(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password, String expertise, int experience, int payrate, Role role, List<Room> roomsToClean) {
		super(name, surname, gender, dateOfBirth, phone, address, username, password);
		this.experience = experience;
		this.expertise = expertise;
		this.payrate = payrate;
		this.role = role;
		this.roomsToClean = roomsToClean;
	}
	
	public int calculateSalary() {
		return experience * payrate;
	}
	
	public List<Room> getRoomsToClean(){
		return roomsToClean;
	}
	
	public String getRoomsToCleanString() {
		String ret = "";
		for(Room room: roomsToClean) {
			ret += room.getId() + " ";
		}
		return ret;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public void addRoomToClean(Room room) {
		this.roomsToClean.add(room);
	}
	
	public int getNOfRooms() {
		if(roomsToClean != null) {
			return this.roomsToClean.size();
		}
		return 0;
	}
	
	public void removeRoomToClean(Room room) {
		this.roomsToClean.remove(room);
	}
	
	public int getExperience() {
		return experience;
	}
	
	public String getExpertise() {
		return expertise;
	}
	
	public int getPayrate() {
		return payrate;
	}
	
	public String toString() {
		String ret = super.toString() + ',' + getExpertise() + ',' + getExperience() + ',' + getPayrate() + ',' + getRole();
		if(role.equals(Role.CLEANER) && !getRoomsToClean().isEmpty()) {
			ret += ',' + getRoomsToCleanString();
		}
	
		return ret;
		
 	}
	
}
