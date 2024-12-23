package services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enums.RoomState;
import models.AdditionalAccommodation;
import models.DateInterval;
import models.Pricing;
import repositories.PricingRepository;

public class PricingService {
	private List<Pricing> priceLists;
	private PricingRepository repository;
	
	public PricingService(String path) {
		priceLists = new ArrayList<Pricing>();
		repository = new PricingRepository(path);
		loadPricings();
	}
	
	public Pricing createPriceList(DateInterval interval, HashMap<String, Double> prices) {
		Pricing priceList = new Pricing(interval, prices);
		priceLists.add(priceList);
		return priceList;
	}
	
	public List<Pricing> getAllPricings(){
		return priceLists;
	}
	
	public double getPrice(String string, LocalDate date) {
		Pricing actual = getPricing(date);
		return actual.getPrice(string);
	}
	
	public Pricing getPricing(LocalDate date) {
		Pricing actual = null;
		for(Pricing pricing: priceLists) {
			if(pricing.getPeriod().contains(date)) {
				actual = pricing;
				break;
			}
		}
		return actual;
	}
	

	public void loadPricings(){
		List<String[]> pricingData = null;
		try {
			pricingData = repository.getPricingData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String[] pricing: pricingData) {
			int day, month, year, day1, month1, year1;
			String[] interval = pricing[0].split("-");
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
			
			HashMap<String, Double> pricemap = new HashMap<>();
			
			for(String keyValue: pricing[1].split(" ")) {
				String key = keyValue.split(":")[0];
				double value = Double.parseDouble(keyValue.split(":")[1]);
				pricemap.put(key, value);
			}
			
			
			createPriceList(new DateInterval(startDate, endDate), pricemap);
		}
	}
	
	public void updateRepository() {
		try {
			repository.update(priceLists);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
