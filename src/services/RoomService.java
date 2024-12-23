package services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enums.Gender;
import enums.RoomState;
import models.DateInterval;
import models.Room;
import repositories.RoomRepository;

public class RoomService {
	private List<Room> rooms;
	private RoomRepository repository;
	
	public RoomService(String path) {
		rooms = new ArrayList<Room>();
		repository = new RoomRepository(path);
		loadRooms();
	}
	
	public Room createRoom(int id, String type, RoomState state, String[] additionals) {
		for(Room room: this.rooms) {
			if(room.getId() == id) {
				return null;
			}
		}
		Room room = new Room(id, type, state, additionals);
		rooms.add(room);
		return room;
	}
	
	public void changeRoomType(String oldType, String newType) {
		for(Room room: this.rooms) {
			if(room.getType().equals(oldType)) {
				room.setType(newType);
				return;
			}
		}
	}
	
	public List<Room> getAllRooms(){
		return rooms;
	}
	
	public Set<String> getAvailableRoomTypes(DateInterval interval){
		Set<String> availableRoomTypes = new HashSet<String>();
		for(Room room: this.rooms) {
			if(room.isAvailable(interval)) {
				availableRoomTypes.add(room.getType());
			}
		}
		
		return availableRoomTypes;
	}
	
	public List<Room> getRooms(String type){
		List<Room> rooms = new ArrayList<Room>();
		for(Room room: this.rooms) {
			if(room.getType().equals(type)) {
				rooms.add(room);
			}
		}
		return rooms;
	}
	
	public void showAllRooms() {
		for(Room room: this.rooms) {
			System.out.println(room.toString());
		}
	}
	
	public List<Room> getAvailableRoomsByType(String type, DateInterval interval){
		List<Room> list = new ArrayList<>();
		for(Room room: this.rooms) {
			if(room.getType().equals(type) && room.isAvailable(interval)) {
				list.add(room);
				System.out.println(room.toString());
			}
		}
		return list;
	}
	
	public Set<String> getAllRoomTypes(){
		Set<String> roomTypes = new HashSet<String>();
		for(Room room: this.rooms) {
			roomTypes.add(room.getType());
		}
		
		return roomTypes;
	}
	
	public Room getRoomById(int id) {
		for(Room room: this.rooms) {
			if(room.getId() == id) {
				return room;
			}
		}
		return null;
	}
	
	
	public void loadRooms(){
		List<String[]> roomData = null;
		try {
			roomData = repository.getRoomData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for(String[] room: roomData) {
			
			RoomState state;
			if(room[2].equals("FREE")) {
				state = RoomState.FREE;
			}
			else if(room[2].equals("BUSY")) {
				state = RoomState.BUSY;
			}
			else {
				state = RoomState.CLEANING;
			}
			
			createRoom(Integer.parseInt(room[0]), room[1], state, room[3].split(" "));
		}
	}
	
	public void updateRepository() {
		try {
			repository.update(rooms);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
