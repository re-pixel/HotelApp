package services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import models.Guest;
import repositories.GuestRepository;

public class GuestService {
	
	private List<Guest> guests;
	private GuestRepository repository;
	
	public GuestService(String path) {
		guests = new ArrayList<Guest>();
		repository = new GuestRepository(path);
		
		this.loadGuests();
	}
	
	public Guest createGuest(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password) {
		Guest guest = new Guest(name, surname, gender, dateOfBirth, phone, address, username, password);
		guests.add(guest);
		
		return guest;
	}
	
	public Guest getGuestByUsername(String username) {
		for(Guest guest: guests) {
			if(username.equals(guest.getUsername())) {
				return guest;
			}
		}
		return null;
	}
	
	public List<Guest> getAllGuests(){
		return guests;
	}
	
	public Guest Login(String username, String password) {
		Guest guest = getGuestByUsername(username);
		if(guest != null && password.equals(guest.getPassword())) {
			return guest;
		}
		return null;
	}
	
	public void loadGuests(){
		List<String[]> guestData = null;
		try {
			guestData = repository.getGuestData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String[] guest: guestData) {
			Gender gender;
			int day, month, year;
			String[] date = guest[3].split("/");
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);
			
			
			LocalDate dateOfBirth = LocalDate.of(year, month, day);
			if(guest[2].equals("FEMALE")) {
				gender = Gender.FEMALE;
			}
			else {
				gender = Gender.MALE;
			}
			
			createGuest(guest[0], guest[1], gender, dateOfBirth, guest[4], guest[5], guest[6], guest[7]);
		}
	}
	
	public void updateRepository() {
		try {
			repository.update(guests);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
