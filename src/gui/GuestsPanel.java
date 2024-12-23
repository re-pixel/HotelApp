package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.Guest;
import models.Reservation;
import models.Staff;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;

public class GuestsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton checkInButton;
	private JButton checkOutButton;
	
	/**
	 * Create the 
	 */
	public GuestsPanel(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		initialize(employee, guestService, staffService, reservationService, roomService, serviceService, reportService);
	}
	
	public void initialize(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {

		setSize(800, 500);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 75, 650, 350);
		add(scrollPane);
		
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		tableModel.addColumn("Username");
		tableModel.addColumn("Name");
		tableModel.addColumn("Surname");
		tableModel.addColumn("Phone");
		tableModel.addColumn("Address");
		tableModel.addColumn("Date of birth");
		
		for(Guest guest: guestService.getAllGuests()) {
			tableModel.addRow(new String[] {guest.getUsername(), guest.getName(), guest.getSurname(), guest.getPhone(), guest.getAdress(), guest.getDateOfBirth()});
		}
		
		checkInButton = new JButton("Check In");
		checkInButton.setBounds(636, 436, 89, 23);
		checkInButton.setEnabled(true);
		add(checkInButton);
		
		checkOutButton = new JButton("Check Out");
		checkOutButton.setBounds(516, 436, 100, 23);
		checkOutButton.setEnabled(true);
		add(checkOutButton);
		
		
		setVisible(true);
		
		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Guest guest = guestService.getAllGuests().get(i);
				staffService.checkIn(employee, guest);
				reportService.processCheckIn(guest);
				roomService.updateRepository();
			}
		});
		
		
		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Guest guest = guestService.getAllGuests().get(i);
				staffService.checkOut(employee, guest);
				roomService.updateRepository();
			}
		});
	}
	
	public void replaceButtons() {
		remove(checkInButton);
		remove(checkOutButton);
		
		
	}
}

