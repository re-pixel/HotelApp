package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import services.AdditionalAccommodationService;
import services.GuestService;
import services.PricingService;
import services.ReservationService;
import services.RoomService;

class ReservationServiceTest {
	
	private AdditionalAccommodationService serviceService;
	private RoomService roomService;
	private GuestService guestService;
	private PricingService pricingService;
	
	private ReservationService service;

	@BeforeEach
	void setUp() throws Exception {
		serviceService = new AdditionalAccommodationService("./database/test/servicesTest.csv");
		roomService = new RoomService("./database/test/roomsTest.csv");
		guestService = new GuestService("./database/test/guestsTest.csv");
		pricingService = new PricingService("./database/test/pricesTest.csv");
		
		service = new ReservationService("./database/test/reservationsTest.csv", guestService, roomService, serviceService, pricingService);
	}

	 @Test
	    void testCreateReservationWithPrice() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
	        Room room = new Room(1, "Single", RoomState.FREE, new String[]{});
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        double price = 300.0;

	        Reservation reservation = service.createReservation(guest, room, interval, services, price);

	        assertNotNull(reservation);
	        assertEquals(1, service.getAllReservations().size());
	        assertEquals(price, reservation.getPrice());
	    }

	    @Test
	    void testCreateReservationWithoutPrice() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
	        String desiredRoomType = "Single";
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        Reservation reservation = service.createReservation(guest, desiredRoomType, interval, services);

	        assertNotNull(reservation);
	        assertEquals(1, service.getAllReservations().size());
	        assertEquals(ReservationState.ON_HOLD, reservation.getReservationState());
	    }

	    @Test
	    void testCalculatePrice() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
	        String desiredRoomType = "Single";
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        HashMap<String, Double> map = new HashMap<>();
	        map.put("Single", 100.0);
	        map.put("Breakfast", 10.0);

	        Pricing pricing = new Pricing(new DateInterval(LocalDate.now(), LocalDate.now().plusMonths(1)), map);
	        pricingService.getAllPricings().add(pricing);

	        Reservation reservation = service.createReservation(guest, desiredRoomType, interval, services);
	        double price = service.calculatePrice(reservation);

	        assertEquals(300.0, price);
	    }

	    @Test
	    void testApproveReservation() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
	        String desiredRoomType = "Single";
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        Staff agent = new Staff("Jane", "Doe", Role.AGENT);

	        Room room = new Room(1, "Single", RoomState.FREE, new String[]{});
	        roomService.getAllRooms().add(room);

	        Reservation reservation = service.createReservation(guest, desiredRoomType, interval, services);

	        boolean result = service.approveReservation(agent, reservation, roomService);

	        assertTrue(result);
	        assertEquals(ReservationState.APPROVED, reservation.getReservationState());
	        assertEquals(room, reservation.getRoom());
	    }

	    @Test
	    void testCancelReservation() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");

	        Room room = roomService.createRoom(1, "Single", RoomState.FREE, new String[]{"TV"});
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        double price = 300.0;

	        Reservation reservation = service.createReservation(guest, room, interval, services, price);

	        service.cancelReservation(reservation);

	        assertEquals(ReservationState.CANCELLED, reservation.getReservationState());
	    }

	    @Test
	    void testBeginReservation() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");

	        Room room = roomService.createRoom(1, "Single", RoomState.FREE, new String[]{"TV"});
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        double price = 300.0;

	        Reservation reservation = service.createReservation(guest, room, interval, services, price);

	        service.beginReservation(reservation);

	        assertEquals(RoomState.BUSY, room.getRoomState());
	    }

	    @Test
	    void testEndReservation() {
	        Guest guest = guestService.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
	        
	        Room room = roomService.createRoom(1, "Single", RoomState.FREE, new String[]{"TV"});
	        DateInterval interval = new DateInterval(LocalDate.now(), LocalDate.now().plusDays(3));
	        List<AdditionalAccommodation> services = new ArrayList<>();
	        double price = 300.0;

	        Reservation reservation = service.createReservation(guest, room, interval, services, price);

	        service.endReservation(reservation);

	        assertEquals(RoomState.CLEANING, room.getRoomState());
	    }


}
