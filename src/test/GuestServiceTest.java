package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.Gender;
import models.Guest;
import services.GuestService;

class GuestServiceTest {
	
	private GuestService service;

	@BeforeEach
	void setUp() throws Exception {
		service = new GuestService("./database/test/guestsTest.csv");
	}

	@Test
    void testCreateGuestWithAllParameters() {
        Guest guest = service.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");

        assertNotNull(guest);
        assertEquals(1, service.getAllGuests().size());
    }

    @Test
    void testGetGuestByUsername() {
        service.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");

        Guest guest = service.getGuestByUsername("johndoe");

        assertNotNull(guest);
        assertEquals("johndoe", guest.getUsername());
    }

    @Test
    void testGetAllGuests() {
        service.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        service.createGuest("Jane", "Doe", Gender.FEMALE, LocalDate.of(1992, 2, 2), "0987654321", "456 Elm St", "janedoe", "password");

        List<Guest> guests = service.getAllGuests();

        assertEquals(2, guests.size());
    }

    @Test
    void testLoginSuccess() {
        service.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");

        Guest guest = service.Login("johndoe", "password");

        assertNotNull(guest);
    }

    @Test
    void testLoginFailure() {
        service.createGuest("John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");

        Guest guest = service.Login("johndoe", "wrongpassword");

        assertNull(guest);
    }

}
