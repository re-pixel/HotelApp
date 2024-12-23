package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.AdditionalAccommodation;
import services.AdditionalAccommodationService;

class AdditionalAccommodationServiceTest {
	
    private AdditionalAccommodationService service;

    @BeforeEach
    void setUp() {
        service = new AdditionalAccommodationService("./database/test/servicesTest.csv");
        service.loadServices();
        for(AdditionalAccommodation service: service.getAllServices()) {
        	System.out.println(service.getName());
        }
    }

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Tests ended");
	}

	 @Test
	 void testCreateAdditionalAccommodation() {
		 AdditionalAccommodation result = service.createAdditionalAccommodation("waterpark");

	     assertNotNull(result);
	     assertEquals("waterpark", result.getName());
	     assertEquals(1, service.getAllServices().size());
	 }
	 
	 @Test
	    void testRemoveService() {
	        service.createAdditionalAccommodation("waterpark");
	        service.removeService("waterpark");

	        assertNull(service.findService("waterpark"));
	        assertTrue(service.getAllServices().isEmpty());
	    }
	 
	 @Test
	    void testFindService() {
	        service.createAdditionalAccommodation("waterpark");
	        AdditionalAccommodation result = service.findService("waterpark");

	        assertNotNull(result);
	        assertEquals("waterpark", result.getName());
	    }
	 
	 @Test
	    void testGetAllServices() {
	        service.createAdditionalAccommodation("waterpark");
	        service.createAdditionalAccommodation("massage");

	        List<AdditionalAccommodation> allServices = service.getAllServices();
	        assertEquals(2, allServices.size());
	    }
	 
	 @Test
	    void testGetServiceByName() {
	        service.createAdditionalAccommodation("waterpark");
	        AdditionalAccommodation result = service.getServiceByName("waterpark");

	        assertNotNull(result);
	        assertEquals("waterpark", result.getName());
	    }
}
