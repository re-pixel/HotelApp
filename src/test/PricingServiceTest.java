package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.DateInterval;
import models.Pricing;
import services.PricingService;

class PricingServiceTest {
	
	private PricingService service;

	@BeforeEach
	void setUp() throws Exception {
		service = new PricingService("./database/test/pricesTest.csv");
	}

	 @Test
	    void testCreatePriceList() {
	        DateInterval interval = new DateInterval(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
	        HashMap<String, Double> prices = new HashMap<>();
	        prices.put("Room1", 100.0);
	        
	        Pricing pricing = service.createPriceList(interval, prices);
	        
	        assertNotNull(pricing);
	        assertEquals(1, service.getAllPricings().size());
	    }

	    @Test
	    void testGetAllPricings() {
	        DateInterval interval1 = new DateInterval(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 30));
	        HashMap<String, Double> prices1 = new HashMap<>();
	        prices1.put("Room1", 100.0);
	        
	        DateInterval interval2 = new DateInterval(LocalDate.of(2023, 7, 1), LocalDate.of(2023, 12, 31));
	        HashMap<String, Double> prices2 = new HashMap<>();
	        prices2.put("Room2", 150.0);
	        
	        service.createPriceList(interval1, prices1);
	        service.createPriceList(interval2, prices2);
	        
	        List<Pricing> pricings = service.getAllPricings();
	        
	        assertEquals(2, pricings.size());
	    }

	    @Test
	    void testGetPrice() {
	        DateInterval interval = new DateInterval(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
	        HashMap<String, Double> prices = new HashMap<>();
	        prices.put("Room1", 100.0);
	        
	        service.createPriceList(interval, prices);
	        
	        double price = service.getPrice("Room1", LocalDate.of(2023, 6, 15));
	        
	        assertEquals(100.0, price);
	    }

	    @Test
	    void testGetPricing() {
	        DateInterval interval = new DateInterval(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
	        HashMap<String, Double> prices = new HashMap<>();
	        prices.put("Room1", 100.0);
	        
	        service.createPriceList(interval, prices);
	        
	        Pricing pricing = service.getPricing(LocalDate.of(2023, 6, 15));
	        
	        assertNotNull(pricing);
	    }


}
