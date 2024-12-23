package repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Pricing;
import models.Room;

public class PricingRepository {
	private BufferedReader reader;
	private BufferedWriter writer;
	private String csvFile;
	public PricingRepository(String path) {
		csvFile = path;
	}
	
	
	public List<String[]> getPricingData() throws IOException{
		try {
			this.reader = new BufferedReader(new FileReader(this.csvFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> pricingData = new ArrayList<String[]>();
		String line;
		 while ((line = reader.readLine()) != null) {
	     // Process each value in the line
			 pricingData.add(line.split(","));
		 }
		 
		 reader.close();
		 return pricingData;
	}
	
	public void update(List<Pricing> priceLists) {
		try {
			this.writer = new BufferedWriter(new FileWriter(csvFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Pricing pricing: priceLists) {
			try {
				writer.write(pricing.toString() + '\n');
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
