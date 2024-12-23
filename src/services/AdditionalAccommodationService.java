package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.AdditionalAccommodation;
import repositories.AdditionalAccommodationRepository;

public class AdditionalAccommodationService {
	private List<AdditionalAccommodation> services;
	private AdditionalAccommodationRepository repository;
	
	public AdditionalAccommodationService(String path) {
		services = new ArrayList<AdditionalAccommodation>();
		repository = new AdditionalAccommodationRepository(path);
		loadServices();
	}
	
	public AdditionalAccommodation createAdditionalAccommodation(String name) {
		AdditionalAccommodation service = new AdditionalAccommodation(name);
		this.services.add(service);
		return service;
	}
	
	public void removeService(String name) {
		for(AdditionalAccommodation service: services) {
			if(service.getName().equals(name)) {
				services.remove(service);
				return;
			}
		}
	}
	
	public AdditionalAccommodation findService(String name) {
		for(AdditionalAccommodation service: services) {
			if(service.getName().equals(name)) {
				return service;
			}
		}
		return null;
	}
	
	public List<AdditionalAccommodation> getAllServices() {
		return services;
	}
	
	public AdditionalAccommodation getServiceByName(String name) {
		for(AdditionalAccommodation service: services) {
			if(service.getName().equals(name)) {
				return service;
			}
		}
		return null;
	}
	
	public void loadServices(){
		List<String[]> serviceData = null;
		try {
			serviceData = repository.getServiceData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String[] service: serviceData) {
			createAdditionalAccommodation(service[0]);
		}
	}
	
	public void updateRepository() {
		try {
			repository.update(services);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
