package repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Room;

public class RoomRepository {
	private String csvFile;
    private BufferedReader reader;
    private BufferedWriter writer;
    
	public RoomRepository(String path) {
		csvFile = path;
	}
	
	public List<String[]> getRoomData() throws IOException{
		try {
			this.reader = new BufferedReader(new FileReader(this.csvFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> roomData = new ArrayList<String[]>();
		String line;
		 while ((line = reader.readLine()) != null) {
	     // Process each value in the line
			 roomData.add(line.split(","));
		 }
		 
		 reader.close();
		 return roomData;
	}
	
	public void update(List<Room> rooms) {
		try {
			this.writer = new BufferedWriter(new FileWriter(csvFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Room room: rooms) {
			try {
				writer.write(room.toString() + '\n');
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
