package models;

import java.time.LocalDate;

import enums.Gender;

public class Guest extends User {
	
	public Guest(String name, String surname) {
		super(name, surname);
	}
	
	public Guest(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password) {
		super(name, surname, gender, dateOfBirth, phone, address, username, password);
	}
	
	public String toString() {
		return super.toString();
	}
}
