package repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.AdditionalAccommodation;

public class AdditionalAccommodationRepository {
	private String csvFile;
    private BufferedReader reader;
    private BufferedWriter writer;
    
	public AdditionalAccommodationRepository(String path) {
		csvFile = path;
	}
	
	public List<String[]> getServiceData() throws IOException{
		try {
			this.reader = new BufferedReader(new FileReader(this.csvFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> serviceData = new ArrayList<String[]>();
		String line;
		 while ((line = reader.readLine()) != null) {
	     // Process each value in the line
			 serviceData.add(line.split(","));
		 }
		 
		 reader.close();
		 return serviceData;
	}
	
	public void update(List<AdditionalAccommodation> services) {
		try {
			this.writer = new BufferedWriter(new FileWriter(csvFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(AdditionalAccommodation service: services) {
			try {
				writer.write(service.toString() + '\n');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
