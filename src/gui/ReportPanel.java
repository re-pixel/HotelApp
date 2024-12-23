package gui;

import javax.swing.JPanel;

import services.PricingService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;

import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.DateInterval;
import models.Report;
import models.Room;
import javax.swing.JScrollPane;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable table_1;
	private Report report;
	
	private ReportService reportService;
	private StaffService staffService;
	private RoomService roomService;
	private ReservationService reservationService;
	private PricingService pricingService;

	/**
	 * Create the panel.
	 */
	public ReportPanel(ReportService reportService, StaffService staffService, RoomService roomService, ReservationService reservationService, PricingService pricingService) {
		
		this.reportService = reportService;
		this.staffService = staffService;
		this.roomService = roomService;
		this.reservationService = reservationService;
		this.pricingService = pricingService;
		
		setSize(800, 500);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("From:");
		lblNewLabel.setBounds(69, 35, 109, 18);
		add(lblNewLabel);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		JFormattedTextField startDateField = new JFormattedTextField(dateFormat);
		startDateField.setBounds(154, 36, 109, 18);
		add(startDateField);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(270, 35, 109, 18);
		add(lblTo);
		
		JFormattedTextField endDateField = new JFormattedTextField(dateFormat);
		endDateField.setBounds(348, 36, 109, 18);
		add(endDateField);
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBounds(547, 35, 95, 18);
		add(confirmButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(113, 108, 266, 191);
		add(scrollPane);
		
		
		
		
		DefaultTableModel model = new DefaultTableModel();
		DefaultTableModel model1 = new DefaultTableModel();
		
		model.addColumn("Id");
		model.addColumn("Type");
		model.addColumn("Overnights");
		model.addColumn("Income");
		
		for(Room room: roomService.getAllRooms()) {
			model.addRow(new String[] {room.getId()+"", room.getType(), "", ""});
		}
		
		model1.addColumn("Status");
		model1.addColumn("Number");
		
		model1.addRow(new String[] {"Approved", " "});
		model1.addRow(new String[] {"Rejected", " "});
		model1.addRow(new String[] {"Cancelled", " "});
		model1.addRow(new String[] {"Total revenue", " "});
		

		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(448, 108, 197, 191);
		add(scrollPane_1);
		
		table_1 = new JTable(model1);
		scrollPane_1.setViewportView(table_1);
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date startingDate = (Date) startDateField.getValue();
				Date endingDate = (Date) endDateField.getValue();
				
				LocalDate startDate = startingDate.toInstant().atZone(ZoneId.of("Europe/Belgrade")).toLocalDate();
				LocalDate endDate = endingDate.toInstant().atZone(ZoneId.of("Europe/Belgrade")).toLocalDate();
				
				DateInterval interval = new DateInterval(startDate, endDate);
				
				updateTables(interval, model, model1);
			}
		});	
	}
	
	void updateTables(DateInterval interval, DefaultTableModel model, DefaultTableModel model1) {
		report = reportService.combineReports(interval.getStartDate(), interval.getEndDate());
		int len1, len2;
		len1 = model.getRowCount();
		len2 = model1.getRowCount();
		for(int i=0;i<len1;i++) {
			model.removeRow(0);
		}
		for(int i=0;i<len2;i++) {
			model1.removeRow(0);
		}

		for(Room room: report.getRoomMap().keySet()) {
			double price = 0;
			for(int i=0;i<interval.durationDays();i++) {
				price += pricingService.getPrice(room.getType(), interval.getStartDate().plusDays(i));
			}
			model.addRow(new String[] {room.getId()+"", room.getType(), report.getRoomMap().get(room)+"", price+""});
		}
		
		model1.addRow(new String[] {"Approved", report.getApproved()+""});
		model1.addRow(new String[] {"Rejected", report.getRejected()+""});
		model1.addRow(new String[] {"Cancelled", report.getCancelled()+""});
		model1.addRow(new String[] {"Total revenue", report.getRevenue()+""});
	}
}
