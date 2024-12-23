package models;

import java.time.*;
import enums.Gender;

public class User {
	private String username;
    private String password;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate dateOfBirth;  
    private String phone;
    private String address;

    public User(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
    }
    
    public User(String name, String surname) {
    	this.name = name;
    	this.surname = surname;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getSurname() {
    	return surname;
    }
    
    public Gender getGender() {
    	return gender;
    }
    
    public String getDateOfBirth() {
    	int day = dateOfBirth.getDayOfMonth();
    	int month = dateOfBirth.getMonthValue();
    	int year = dateOfBirth.getYear();
    	return String.valueOf(day) + '/' + String.valueOf(month) + '/' + String.valueOf(year);
    }
    
    public String getPhone() {
    	return phone;
    }
    
    public String getAdress() {
    	return address;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public String toString() {
    	return getName() + ',' + getSurname() + ',' + getGender() + ',' + getDateOfBirth() + ',' + getPhone() + ','  + getAdress() + ',' + getUsername() + ',' + getPassword(); 
    }
}
