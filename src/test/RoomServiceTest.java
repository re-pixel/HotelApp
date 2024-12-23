package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.RoomState;
import models.DateInterval;
import models.Room;
import services.RoomService;

class RoomServiceTest {
	
	private RoomService service;

	@BeforeEach
	void setUp() throws Exception {
		service = new RoomService("./database/test/roomsTest.csv");
	}

	@Test
    void testCreateRoom() {
        Room room = service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        
        assertNotNull(room);
        assertEquals(1, service.getAllRooms().size());
    }

    @Test
    void testCreateRoomDuplicateId() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        Room room = service.createRoom(1, "Double", RoomState.FREE, new String[]{"Fridge"});
        
        assertNull(room);
        assertEquals(1, service.getAllRooms().size());
    }

    @Test
    void testChangeRoomType() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        service.changeRoomType("Single", "Double");

        Room room = service.getRoomById(1);

        assertEquals("Double", room.getType());
    }

    @Test
    void testGetAllRooms() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        service.createRoom(2, "Double", RoomState.BUSY, new String[]{"Fridge"});
        
        List<Room> rooms = service.getAllRooms();
        
        assertEquals(2, rooms.size());
    }

    @Test
    void testGetAvailableRoomTypes() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        service.createRoom(2, "Double", RoomState.BUSY, new String[]{"Fridge"});
        
        DateInterval interval = new DateInterval(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        Set<String> availableRoomTypes = service.getAvailableRoomTypes(interval);
        
        assertEquals(1, availableRoomTypes.size());
        assertTrue(availableRoomTypes.contains("Single"));
    }

    @Test
    void testGetRooms() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        service.createRoom(2, "Single", RoomState.BUSY, new String[]{"Fridge"});
        
        List<Room> rooms = service.getRooms("Single");
        
        assertEquals(2, rooms.size());
    }

    @Test
    void testGetAvailableRoomsByType() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        service.createRoom(2, "Single", RoomState.BUSY, new String[]{"Fridge"});
        
        DateInterval interval = new DateInterval(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        List<Room> rooms = service.getAvailableRoomsByType("Single", interval);
        
        assertEquals(2, rooms.size());
    }

    @Test
    void testGetAllRoomTypes() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        service.createRoom(2, "Double", RoomState.BUSY, new String[]{"Fridge"});
        
        Set<String> roomTypes = service.getAllRoomTypes();
        
        assertEquals(2, roomTypes.size());
    }

    @Test
    void testGetRoomById() {
        service.createRoom(1, "Single", RoomState.FREE, new String[]{"TV", "WiFi"});
        
        Room room = service.getRoomById(1);
        
        assertNotNull(room);
        assertEquals(1, room.getId());
    }
}
