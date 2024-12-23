package repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Guest;

public class GuestRepository {
	
	
	
	private String csvFile;
    private BufferedReader reader;
    private BufferedWriter writer;
    
	public GuestRepository(String path) {
		csvFile = path;
	}
	
	public List<String[]> getGuestData() throws IOException{
		try {
			this.reader = new BufferedReader(new FileReader(this.csvFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> guestData = new ArrayList<String[]>();
		String line;
		 while ((line = reader.readLine()) != null) {
	     // Process each value in the line
			 guestData.add(line.split(","));
		 }
		 
		 reader.close();
		 return guestData;
	}
	
	public void update(List<Guest> guests) {
		try {
			this.writer = new BufferedWriter(new FileWriter(csvFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Guest guest: guests) {
			try {
				writer.write(guest.toString() + '\n');
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
