package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.Gender;
import enums.Role;
import models.Guest;
import models.Staff;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.PricingService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;

class StaffServiceTest {
	
	private AdditionalAccommodationService serviceService;
	private RoomService roomService;
	private GuestService guestService;
	private PricingService pricingService;
	private ReservationService reservationService;
	
	private StaffService service;
	
	@BeforeEach
	void setUp() throws Exception {
		serviceService = new AdditionalAccommodationService("./database/test/servicesTest.csv");
		roomService = new RoomService("./database/test/roomsTest.csv");
		guestService = new GuestService("./database/test/guestsTest.csv");
		pricingService = new PricingService("./database/test/pricesTest.csv");
		reservationService = new ReservationService("./database/test/reservationsTest.csv", guestService, roomService, serviceService, pricingService);
		
		service = new StaffService("./database/test/staffTest.csv", guestService, roomService, reservationService);
	}

    @Test
    void testCreateEmployeeWithDetails() {
        Staff employee = service.createEmployee("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "123-456-7890", "123 Street", "johndoe", "password", "Expert", 5, 20, Role.CLEANER);

        assertNotNull(employee);
        assertEquals(1, service.getStaff().size());
        assertEquals("John", employee.getName());
        assertEquals(Role.CLEANER, employee.getRole());
        assertEquals(5, employee.getExperience());
    }

    @Test
    void testGetCleaners() {
        service.createEmployee("John", "Doe", Gender.MALE, LocalDate.of(1985, 5, 15), "555-1234", "123 Elm St", "jdoe", "password", "Expert", 10, 5000, Role.CLEANER);
        service.createEmployee("Jane", "Smith", Gender.FEMALE, LocalDate.of(1990, 8, 22), "555-5678", "456 Oak St", "jsmith", "password", "Expert", 8, 4000, Role.AGENT);

        List<Staff> cleaners = service.getCleaners();

        assertEquals(1, cleaners.size());
        assertEquals("John", cleaners.get(0).getName());
    }

    @Test
    void testLogin() {
        service.createEmployee("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "123-456-7890", "123 Street", "johndoe", "password", "Expert", 5, 20, Role.AGENT);

        Staff loggedIn = service.Login("johndoe", "password");

        assertNotNull(loggedIn);
        assertEquals("John", loggedIn.getName());
    }

    @Test
    void testAddGuest() {
        Staff agent = service.createEmployee("Jalen", "Smith", Gender.FEMALE, LocalDate.of(1990, 8, 22), "555-5678", "456 Oak St", "jsmith", "password", "Expert", 8, 4000, Role.AGENT);
        Guest guest = service.addGuest(agent, "Jane", "Smith", Gender.FEMALE, LocalDate.of(1995, 5, 5), "123-456-7890", "456 Street", "janesmith", "password");

        assertNotNull(guest);
        assertEquals(1, guestService.getAllGuests().size());
        assertEquals("Jane", guest.getName());
    }

    @Test
    void testRemoveEmployee() {
        Staff employee = service.createEmployee("Jalen", "Smith", Gender.FEMALE, LocalDate.of(1990, 8, 22), "555-5678", "456 Oak St", "jsmith", "password", "Expert", 8, 4000, Role.AGENT);

        service.removeEmployee(employee);

        assertEquals(0, service.getStaff().size());
    }

}
