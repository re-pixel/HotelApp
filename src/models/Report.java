package models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Report {
	private double revenue;
	private LocalDate date;
	private HashMap<Staff, Integer> cleaners;
	private int approved;
	private int rejected;
	private int cancelled;
	private List<Room> busyRooms;
	private HashMap<Room, Integer> roomMap;
	
	
	public Report(LocalDate date, HashMap<Staff, Integer> cleaners, List<Room> busyRooms, double revenue) {
		this.date = date;
		this.cleaners = cleaners;
		approved = 0;
		rejected = 0;
		cancelled = 0;
		this.busyRooms = busyRooms;
		this.revenue = revenue;
	}
	
	public Report(LocalDate date, HashMap<Staff, Integer> cleaners, int approved, int rejected, int cancelled, List<Room> busyRooms, double revenue) {
		this.date = date;
		this.cleaners = cleaners;
		this.approved = approved;
		this.rejected = rejected;
		this.cancelled = cancelled;
		this.busyRooms = busyRooms;
		this.revenue = revenue;
	}
	
	public Report(HashMap<Staff, Integer> cleanerMap, int approved, int rejected, int cancelled, HashMap<Room, Integer> roomMap, double revenue) {
		this.cleaners = cleanerMap;
		this.approved = approved;
		this.rejected = rejected;
		this.cancelled = cancelled;
		this.roomMap = roomMap;
		this.revenue = revenue;
	}

	public String toString() {
		String cleanerString = "";
		for(Staff cleaner: cleaners.keySet()) {
			String tmp = cleaner.getUsername() + ":" + cleaners.get(cleaner);
			cleanerString += tmp + " ";
		}
		String roomString = "";
		if(roomMap == null) {
			for(Room room: busyRooms) {
				roomString += room.getId() + " ";
			}
		}
		else {
			for(Room room: roomMap.keySet()) {
				roomString += room.getId() + ":" + roomMap.get(room) + " ";
			}
		}
		return date + "," + cleanerString + "," + approved + " " + rejected + " " + cancelled + "," + roomString + "," + revenue;
	}
	
	public void addBusyRoom(Room room) {
		busyRooms.add(room);
	}
	
	public void addCleanedRoom(Staff cleaner) {
		cleaners.put(cleaner, cleaners.get(cleaner)+1);
	}
	
	public double getRevenue() {
		return revenue;
	}
	
	public HashMap<Room, Integer> getRoomMap(){
		return roomMap;
	}
	
	  public LocalDate getDate() {
	        return date;
	    }

	    public HashMap<Staff, Integer> getCleaners() {
	        return cleaners;
	    }

	    public int getApproved() {
	        return approved;
	    }

	    public int getRejected() {
	        return rejected;
	    }

	    public int getCancelled() {
	        return cancelled;
	    }

	    public List<Room> getBusyRooms() {
	        return busyRooms;
	    }

	    public void setDate(LocalDate date) {
	        this.date = date;
	    }

	    public void setCleaners(HashMap<Staff, Integer> cleaners) {
	        this.cleaners = cleaners;
	    }

	    public void setApproved(int approved) {
	        this.approved = approved;
	    }

	    public void setRejected(int rejected) {
	        this.rejected = rejected;
	    }

	    public void setCancelled(int cancelled) {
	        this.cancelled = cancelled;
	    }

	    public void setBusyRooms(List<Room> busyRooms) {
	        this.busyRooms = busyRooms;
	    }
	    
	    public void setRevenue(double revenue) {
	    	this.revenue = revenue;
	    }
}

