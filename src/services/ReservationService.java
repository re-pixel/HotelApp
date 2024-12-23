package services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import enums.Gender;
import enums.ReservationState;
import enums.Role;
import enums.RoomState;
import models.AdditionalAccommodation;
import models.DateInterval;
import models.Guest;
import models.Pricing;
import models.Reservation;
import models.Room;
import models.Staff;
import repositories.ReservationRepository;

public class ReservationService {
	private List<Reservation> reservations;
	private ReservationRepository repository;
	private AdditionalAccommodationService serviceService;
	private RoomService roomService;
	private GuestService guestService;
	private PricingService pricingService;
	
	public ReservationService(String path, GuestService guestService, RoomService roomService, AdditionalAccommodationService serviceService, PricingService pricingService) {
		this.reservations = new ArrayList<Reservation>();
		this.repository = new ReservationRepository(path);
		this.pricingService = pricingService;
		this.guestService = guestService;
		this.roomService = roomService;
		this.serviceService = serviceService;
		loadReservations();
	}
	
	public List<Reservation> getAllReservations(){
		return reservations;
	}
	
	public Reservation createReservation(Guest guest, Room room, DateInterval interval, List<AdditionalAccommodation> services, double price) {
		Reservation reservation = new Reservation(guest, room, interval, services, ReservationState.APPROVED, price);
		
		reservations.add(reservation);
		return reservation;
	}
	
	public Reservation createReservation(Guest guest, String desiredRoomType, DateInterval interval, List<AdditionalAccommodation> services) {
		Reservation reservation = new Reservation(guest, desiredRoomType, interval, services, ReservationState.ON_HOLD);
		
		calculatePrice(reservation);
		reservations.add(reservation);
		return reservation;
	}
	
	public Reservation createReservation(Guest guest, String desiredRoomType, DateInterval interval, List<AdditionalAccommodation> services, ReservationState state, double price) {
		Reservation reservation = new Reservation(guest, desiredRoomType, interval, services, state, price);
		reservations.add(reservation);
		return reservation;
	}
	
	public double calculatePrice(Reservation reservation) {
		double price = 0;
		double price2 = 0;
		LocalDate start = reservation.getStartDate();
		LocalDate end = reservation.getEndDate();
		String type;
		if(reservation.getRoom() != null) {
			type = reservation.getRoom().getType();
		}
		else {
			type = reservation.getDesiredType();
		}
		for(Pricing pricing: pricingService.getAllPricings()) {
			DateInterval period = pricing.getPeriod();
			if(period.contains(start) && period.contains(end)) {
				price += pricing.getPrices().get(type);
				for(AdditionalAccommodation service: reservation.getServices()) {
					price += pricing.getPrices().get(service.toString());
			}
			price *= reservation.getInterval().durationDays()-1;
			break;
				
			}
			
			else if(period.contains(start)) {
				price += pricing.getPrices().get(type);
				for(AdditionalAccommodation service: reservation.getServices()) {
					price += pricing.getPrices().get(service.toString());
			}
				price *= (new DateInterval(start, period.getEndDate())).durationDays();
				
		}
			else if(period.contains(end)) {
				price2 += pricing.getPrices().get(type);
				for(AdditionalAccommodation service: reservation.getServices()) {
					price2 += pricing.getPrices().get(service.toString());
			}
				price2 *= (new DateInterval(period.getEndDate(), end)).durationDays();
				price += price2;
				break;
		}
			else {
				continue;
			}
	}
		reservation.setPrice(price);
		return price;
	}
	
	public void changeReservation(Reservation reservation, List<AdditionalAccommodation> services) {
		reservation.setServices(services);
	}
	
	public List<Reservation> getGuestReservations(Guest guest){
		List<Reservation> list = new ArrayList<>();
		for(Reservation reservation: reservations) {
			if(reservation.getGuest().getUsername().equals(guest.getUsername())) {
				list.add(reservation);
			}
		}
		return list;
	}
	
	public List<Reservation> getActiveGuestReservations(Guest guest){
		List<Reservation> list = new ArrayList<>();
		for(Reservation reservation: reservations) {
			if(reservation.getGuest().getUsername().equals(guest.getUsername()) && reservation.getInterval().contains(LocalDate.now()) && reservation.getReservationState().equals(ReservationState.APPROVED)) {
				list.add(reservation);
			}
		}
		return list;
	}
	
	
	public boolean approveReservation(Staff agent, Reservation reservation, RoomService roomService) {
		List<Room> availableRooms = roomService.getAvailableRoomsByType(reservation.getDesiredType(), reservation.getInterval());
		if(!availableRooms.isEmpty()) {
			reservation.setRoom(availableRooms.get(0));
			reservation.setState(ReservationState.APPROVED);
			return true;
			}
		reservation.setState(ReservationState.REJECTED);
		reservation.setPrice(0);
		return false;
	}
	
	public void beginReservation(Reservation reservation) {
		reservation.getRoom().setState(RoomState.BUSY);
	}
	
	public void cancelReservation(Reservation reservation) {
		reservation.setState(ReservationState.CANCELLED);
		updateRepository();
	}
	
	
	public void addServices(Staff agent, Reservation reservation, List<AdditionalAccommodation> services) {
		if(agent.getRole().equals(Role.AGENT)) {
			List<AdditionalAccommodation> list = new ArrayList<AdditionalAccommodation>(reservation.getServices());
			list.addAll(services);
			reservation.setServices(list);
		}
	}
	
	public void endReservation(Reservation reservation) {
		reservation.getRoom().setState(RoomState.CLEANING);
	}
	
	public void cancelReservation(Guest guest, Reservation reservation) {
		reservation.setState(ReservationState.CANCELLED);
	}
	
	public void loadReservations(){
		List<String[]> reservationData = null;
		try {
			reservationData = repository.getReservationData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String[] reservation: reservationData) {
			int day, month, year, day1, month1, year1;
			String[] interval = reservation[2].split("-");
			String[] date = interval[0].split("/");
			String[] date1 = interval[1].split("/");
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);
			
			day1 = Integer.parseInt(date1[0]);
			month1 = Integer.parseInt(date1[1]);
			year1 = Integer.parseInt(date1[2]);
			
			
			LocalDate startDate = LocalDate.of(year, month, day);
			LocalDate endDate = LocalDate.of(year1, month1, day1);
			
			String[] services = reservation[3].split(" ");
			List<AdditionalAccommodation> additional = new ArrayList<AdditionalAccommodation>();
			for(String service: services) {
				additional.add(serviceService.getServiceByName(service));
			}
			ReservationState state;
			
			if(reservation[5].equals("ON_HOLD")) {
				state = ReservationState.ON_HOLD;
			}
			else if(reservation[5].equals("APPROVED")) {
				state = ReservationState.APPROVED;
			}
			else if(reservation[5].equals("REJECTED")) {
				state = ReservationState.REJECTED;
			}
			else {
				state = ReservationState.CANCELLED;
			}
			Room room;
			if(state.equals(ReservationState.APPROVED)) {
				room = roomService.getRoomById(Integer.parseInt(reservation[1]));
				createReservation(guestService.getGuestByUsername(reservation[0]), room, new DateInterval(startDate, endDate), additional, Double.parseDouble(reservation[4]));
			}
			else {
				createReservation(guestService.getGuestByUsername(reservation[0]), reservation[1], new DateInterval(startDate, endDate), additional, state, Double.parseDouble(reservation[4]));
			}
			
		}
	}
	
	public void updateRepository() {
		try {
			repository.update(reservations);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
