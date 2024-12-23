package gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

import models.Report;
import models.Room;
import models.Staff;
import services.PricingService;
import services.ReportService;
import services.RoomService;

public class GraphsPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  public GraphsPanel(ReportService reportService, PricingService pricingService, RoomService roomService) {
    setSize(800, 500);
    setLayout(null);

    CategoryChart chart = new CategoryChartBuilder()
        .width(400)
        .height(300)
        .title("Income by room type")
        .xAxisTitle("Month")
        .yAxisTitle("Income")
        .build();
    
    HashMap<String, double[]> map = new HashMap<>();
    
    for(String type: roomService.getAllRoomTypes()) {
    	double[] tmp = new double[12];
    	Arrays.fill(tmp, 0.0);
    	map.put(type, tmp);
    }
    
    for(Report report: reportService.getReports()) {
    	for(Room room: report.getBusyRooms()) {
    		double price = pricingService.getPrice(room.getType(), report.getDate());
    		map.get(room.getType())[report.getDate().getMonthValue()-1] += price;
    	}
    }
    
    for(String type: roomService.getAllRoomTypes()) {
    	List<Double> tmp = new ArrayList<Double>();
    	for(double x: map.get(type)) {
    		tmp.add(x);
    	}
    	chart.addSeries(type, Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Avg", "Sep", "Oct", "Nov", "Dec"), tmp)
        .setMarker(SeriesMarkers.CIRCLE);
    }
    XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
    chartPanel.setBounds(50,40,700,250);
    this.add(chartPanel);
    

    PieChart pieChart = new PieChartBuilder()
        .width(335)
        .height(150)
        .title("Cleaned rooms (past 31 days)")
        .build();
    
    PieChart pieChart1 = new PieChartBuilder()
            .width(335)
            .height(150)
            .title("Reservations processed")
            .build();

    Report monthlyReport = reportService.combineReports(LocalDate.now().minusDays(31), LocalDate.now());
    
    for(Staff cleaner: monthlyReport.getCleaners().keySet()) {
    	pieChart.addSeries(cleaner.getName(), monthlyReport.getCleaners().get(cleaner));
    }
    
    pieChart1.addSeries("Approved", monthlyReport.getApproved());
    pieChart1.addSeries("Rejected", monthlyReport.getRejected());
    pieChart1.addSeries("Cancelled", monthlyReport.getCancelled());
    

    XChartPanel<PieChart> pieChartPanel = new XChartPanel<>(pieChart);
    pieChartPanel.setBounds(50,330,335,150);
    this.add(pieChartPanel);
    
    XChartPanel<PieChart> pieChartPanel1 = new XChartPanel<>(pieChart1);
    pieChartPanel1.setBounds(415,330,335,150);
    this.add(pieChartPanel1);
  }
}
