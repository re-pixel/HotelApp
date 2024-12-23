package services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Staff;
import repositories.StaffRepository;
import models.Guest;
import models.Reservation;
import models.Room;
import enums.Gender;
import enums.ReservationState;
import enums.Role;
import enums.RoomState;

public class StaffService {
	private List<Staff> staff;
	private GuestService guestService;
	private StaffRepository repository;
	private RoomService roomService;
	private ReservationService reservationService;
	
	public StaffService(String path, GuestService guestService, RoomService roomService, ReservationService reservationService) {
		this.staff = new ArrayList<Staff>();
		this.guestService = guestService;
		this.roomService = roomService;
		this.repository = new StaffRepository(path);
		this.reservationService = reservationService;
		loadStaff();
		for(Staff mep: staff) {
			System.out.println(mep.toString());
		}
	}
	
	public List<Staff> getStaff() {
		return staff;
	}
	
	public List<Staff> getCleaners(){
		List<Staff> cleaners = new ArrayList<Staff>(); 
		for(Staff employee: staff) {
			if(employee.getRole().equals(Role.CLEANER)) {
				cleaners.add(employee);
			}
		}
		return cleaners;
	}
	
	public Staff createEmployee(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password, String expertise, int experience, int payrate, Role role) {
		Staff employee = new Staff(name, surname, gender, dateOfBirth, phone, address, username, password, expertise, experience, payrate, role);
		staff.add(employee);
		return employee;
	}
	
	public Staff createEmployee(String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password, String expertise, int experience, int payrate, Role role, List<Room> roomsToClean) {
		Staff employee = new Staff(name, surname, gender, dateOfBirth, phone, address, username, password, expertise, experience, payrate, role, roomsToClean);
		staff.add(employee);
		return employee;
	}
	
	public boolean checkIn(Staff agent, Guest guest) {
		List<Reservation> reservations = reservationService.getGuestReservations(guest);
		for(Reservation reservation: reservations) {
			if(reservation.getInterval().contains(LocalDate.now()) && reservation.getReservationState().equals(ReservationState.APPROVED)) {
				reservationService.beginReservation(reservation);
			}
		}
		return false;
	}
	
	public boolean checkOut(Staff agent, Guest guest) {
		List<Reservation> reservations = reservationService.getGuestReservations(guest);
		for(Reservation reservation: reservations) {
			if(reservation.getInterval().contains(LocalDate.now()) && reservation.getReservationState().equals(ReservationState.APPROVED)) {
				reservationService.endReservation(reservation);
				delegateCleaning(reservation.getRoom());
			}
		}
		return false;
	}
	
	public List<Room> getRoomsToClean(Staff cleaner){
		return cleaner.getRoomsToClean();
	}
	
	public Staff Login(String username, String password) {
		Staff employee = getEmployeeByUsername(username);
		if(employee != null && employee.getPassword().equals(password)) {
			return employee;
		}
		return null;
	}
	
	public Staff getEmployeeByUsername(String username) {
		for(Staff employee: staff) {
			if(username.equals(employee.getUsername())) {
				return employee;
			}
		}
		return null;
	}
	
	public Guest addGuest(Staff agent, String name, String surname, Gender gender, LocalDate dateOfBirth, String phone, String address, String username, String password) {
		if(agent.getRole().equals(Role.AGENT)) {
			Guest guest = this.guestService.createGuest(name, surname, gender, dateOfBirth, phone, address, username, password);
			return guest;
		}
		return null;
	}
	
	public void showEmployees() {
		for(Staff employee: this.staff) {
			System.out.println(employee.toString());
		}
	}
	
	public void removeEmployee(Staff employee) {
		this.staff.remove(employee);
		updateRepository();
	}
	
	public void delegateCleaning(Room room) {
		int min_len = 10000;
		Staff chosen = null;
		for(Staff cleaner: staff) {
			if(cleaner.getRole().equals(Role.CLEANER)) {
				if(cleaner.getNOfRooms() < min_len) {
					chosen = cleaner;
					min_len = cleaner.getNOfRooms();
				}
			}
		}
		chosen.addRoomToClean(room);
		updateRepository();
	}
	
	public void finishCleaning(Staff cleaner, Room room) {
		cleaner.removeRoomToClean(room);
		room.setState(RoomState.FREE);
		roomService.updateRepository();
		updateRepository();
	}
	
	public void loadStaff(){
		List<String[]> staffData = null;
		try {
			staffData = repository.getStaffData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String[] employee: staffData) {
			Gender gender;
			int day, month, year;
			String[] date = employee[3].split("/");
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);
			
			
			LocalDate dateOfBirth = LocalDate.of(year, month, day);
			if(employee[2].equals("FEMALE")) {
				gender = Gender.FEMALE;
			}
			else {
				gender = Gender.MALE;
			}
			
			Role role;
			if(employee[11].equals("ADMIN")) {
				role = Role.ADMIN;
			}
			else if(employee[11].equals("AGENT")) {
				role = Role.AGENT;
			}
			else {
				role = Role.CLEANER;
				List<Room> roomsToClean = new ArrayList<Room>();
				if(employee.length == 13) {
					for(String id: employee[12].split(" ")) {
						roomsToClean.add(roomService.getRoomById(Integer.parseInt(id)));
					}
				}
				createEmployee(employee[0], employee[1], gender, dateOfBirth, employee[4], employee[5], employee[6], employee[7], employee[8], Integer.parseInt(employee[9]), Integer.parseInt(employee[10]), role, roomsToClean);
				continue;
			}
			
			createEmployee(employee[0], employee[1], gender, dateOfBirth, employee[4], employee[5], employee[6], employee[7], employee[8], Integer.parseInt(employee[9]), Integer.parseInt(employee[10]), role);
		}
	}
	
	public void updateRepository() {
		try {
			repository.update(staff);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
