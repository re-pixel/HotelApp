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
import models.Report;

public class ReportRepository {
	private BufferedReader reader;
	private BufferedWriter writer;
	private String csvFile;
	public ReportRepository(String path) {
		csvFile = path;
	}
	
	
	public List<String[]> getReportData() throws IOException{
		try {
			this.reader = new BufferedReader(new FileReader(this.csvFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> reportData = new ArrayList<String[]>();
		String line;
		 while ((line = reader.readLine()) != null) {
	     // Process each value in the line
			 reportData.add(line.split(","));
		 }
		 
		 reader.close();
		 return reportData;
	}
	
	public void update(List<Report> reports) {
		try {
			this.writer = new BufferedWriter(new FileWriter(csvFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Report report: reports) {
			try {
				writer.write(report.toString() + '\n');
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
