package models;

public class AdditionalAccommodation {
	String name;
	
	
	public AdditionalAccommodation(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return getName();
	}
}
