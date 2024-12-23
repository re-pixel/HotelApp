package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.AdditionalAccommodation;
import models.Pricing;
import models.Staff;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.PricingService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;
import javax.swing.JButton;

public class PricingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	public PricingPanel(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, PricingService pricingService) {
		
		setSize(800, 500);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 25, 740, 400);
		add(scrollPane);
		
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		JButton newPricingButton = new JButton("Add Pricing");
		newPricingButton.setBounds(645, 432, 120, 25);
		add(newPricingButton);
		
		
		List<String> columnNames = new ArrayList<String>();
		tableModel.addColumn("Period");
		for(String roomType: roomService.getAllRoomTypes()) {
			tableModel.addColumn(roomType);
			columnNames.add(roomType);
		}
		
		for(AdditionalAccommodation service: serviceService.getAllServices()) {
			tableModel.addColumn(service.toString());
			columnNames.add(service.toString());
		}
		
		for(Pricing pricing: pricingService.getAllPricings()) {
			Vector<String> row = new Vector<String>();
			row.add(pricing.getPeriod().toString());
			for(String columnName: columnNames) {
				row.add(pricing.getPrice(columnName) + "");
			}
			
			tableModel.addRow(row);
		}
		

		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		
		newPricingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewPricingDialog(table, tableModel, pricingService, roomService, serviceService);
				
			}
		});
		
		
		
	}
}
