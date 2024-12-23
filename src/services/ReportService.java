package services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enums.Gender;
import enums.ReservationState;
import enums.Role;
import models.DateInterval;
import models.Guest;
import models.Report;
import models.Reservation;
import models.Room;
import models.Staff;
import repositories.ReportRepository;

public class ReportService {
	private Report current;
	private List<Report> reports;
	private StaffService staffService;
	private RoomService roomService;
	private ReportRepository repository;
	private ReservationService reservationService;
	
	
	public ReportService(String path, StaffService staffService, RoomService roomService, ReservationService reservationService) {
		this.reservationService = reservationService;
		this.staffService = staffService;
		this.roomService = roomService;
		this.repository = new ReportRepository(path);
		this.reports = new ArrayList<Report>();
		loadReports();
		this.current = getReport(LocalDate.now());
	}
	
	public Report createReport(LocalDate date) {
		HashMap<Staff, Integer> map = new HashMap<>();
		List<Staff> cleaners = staffService.getCleaners();
		for(Staff cleaner: cleaners) {
			map.put(cleaner, 0);
		}
		List<Room> busyRooms = new ArrayList<Room>();
		double revenue = 0;
		for(Reservation reservation: reservationService.getAllReservations()) {
			if(reservation.getInterval().contains(LocalDate.now()) && reservation.getRoom() != null) {
				busyRooms.add(reservation.getRoom());
			}
		}
		Report report = new Report(date, map, busyRooms, revenue);
		reports.add(report);
		updateRepository();
		return report;
	}
	
	public Report createReport(LocalDate date, HashMap<Staff, Integer> cleaners, int approved, int rejected, int cancelled, List<Room> busyRooms, double revenue) {
		Report report = new Report(date, cleaners, approved, rejected, cancelled, busyRooms, revenue);
		reports.add(report);
		return report;
	}
	
	public List<Report> getReports(){
		return reports;
	}
	
	public Report getReport(LocalDate date) {
		for(Report report: reports) {
			if(report.getDate().equals(date)) {
				return report;
			}
		}
		return createReport(date);
	}
	
	
	public void registerReservation(Reservation reservation) {
		if(reservation.getReservationState().equals(ReservationState.APPROVED)) {
			current.setApproved(current.getApproved()+1);
			current.setRevenue(current.getRevenue()+reservation.getPrice());
		}
		else if(reservation.getReservationState().equals(ReservationState.REJECTED)) {
			current.setRejected(current.getRejected()+1);
		}
		else {
			current.setCancelled(current.getCancelled()+1);
		}
		updateRepository();
	}
	
	public void processCheckIn(Guest guest) {
		List<Reservation> reservations = reservationService.getActiveGuestReservations(guest);
		for(Reservation reservation: reservations) {
			current.addBusyRoom(reservation.getRoom());
		}
		updateRepository();
	}
	
	public Report combineReports(LocalDate startDate, LocalDate endDate) {
		DateInterval interval = new DateInterval(startDate, endDate);
		int len = (int) interval.durationDays();
		int i, j;
		if(reports.size() < len) {
			i = 0;
			j = len;
		}
		else {
			Report first = getReport(startDate);
			i = reports.indexOf(first);
			j = i + len;
		}
		HashMap<Staff, Integer> cleanerMap = new HashMap<>();
		List<Staff> cleaners = staffService.getCleaners();
		for(Staff cleaner: cleaners) {
			cleanerMap.put(cleaner, 0);
		}
		int approved = 0, rejected = 0, cancelled = 0;
		double revenue = 0;
		HashMap<Room, Integer> roomMap = new HashMap<>();
		List<Room> rooms = roomService.getAllRooms();
		for(Room room: rooms) {
			roomMap.put(room, 0);
		}
		while(i<reports.size() && i<j) {
			Report report = reports.get(i);

			for(Staff cleaner: report.getCleaners().keySet()) {
				int tmp = cleanerMap.get(cleaner);
				int addition = report.getCleaners().get(cleaner);
				cleanerMap.replace(cleaner, tmp+addition);
			}
			
			approved += report.getApproved();
			rejected += report.getRejected();
			cancelled += report.getCancelled();
			revenue += report.getRevenue();
			
			for(Room room: report.getBusyRooms()) {
				int tmp = roomMap.get(room);
				roomMap.replace(room, tmp+1);
			}
			
			i++;
		}
		
		return new Report(cleanerMap, approved, rejected, cancelled, roomMap, revenue);
	}
	
	
	public void loadReports(){
		List<String[]> reportData = null;
		try {
			reportData = repository.getReportData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String[] report: reportData) {
			Gender gender;
			int day, month, year;
			String[] date = report[0].split("-");
			day = Integer.parseInt(date[2]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[0]);
			
			LocalDate reportDate = LocalDate.of(year, month, day);
			
			HashMap<Staff, Integer> map = new HashMap<>();
			for(String cleanerString: report[1].split(" ")) {
				Staff key = staffService.getEmployeeByUsername(cleanerString.split(":")[0]);
				int value = Integer.parseInt(cleanerString.split(":")[1]);
				map.put(key, value);
			}
			
			String[] reservationStates = report[2].split(" ");
			
			List<Room> rooms = new ArrayList<Room>();
			for(String roomIdString: report[3].split(" ")){
				rooms.add(roomService.getRoomById(Integer.parseInt(roomIdString)));
			}
			
			createReport(reportDate, map, Integer.parseInt(reservationStates[0]), Integer.parseInt(reservationStates[1]), Integer.parseInt(reservationStates[2]), rooms, Double.parseDouble(report[4]));
		}
			
	}
	
	public void updateRepository() {
		repository.update(reports);
	}

}
