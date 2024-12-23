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
import services.ReservationService;
import services.RoomService;
import services.StaffService;

public class StaffPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton addEmployeeButton;
	private JButton removeEmployeeButton;
	
	/**
	 * Create the 
	 */
	public StaffPanel(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService) {
		initialize(employee, guestService, staffService, reservationService, roomService, serviceService);
	}
	
	public void initialize(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService) {

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
		tableModel.addColumn("Role");
		tableModel.addColumn("Salary");
		tableModel.addColumn("Date of birth");
		
		for(Staff staff: staffService.getStaff()) {
			tableModel.addRow(new String[] {staff.getUsername(), staff.getName(), staff.getSurname(), ""+staff.getRole(), ""+staff.calculateSalary(), staff.getDateOfBirth()});
		}
		
		addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setBounds(600, 436, 120, 23);
		addEmployeeButton.setEnabled(true);
		add(addEmployeeButton);
		
		
		removeEmployeeButton = new JButton("Remove");
		removeEmployeeButton.setBounds(500, 436, 90, 23);
		removeEmployeeButton.setEnabled(true);
		add(removeEmployeeButton);
		
		
		setVisible(true);
		
		addEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewEmployeeDialog addEmployeeDialog = new NewEmployeeDialog(staffService, table, tableModel);
				
				
			}
		});
		
		
		removeEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Staff employee = staffService.getStaff().get(i);
				
				staffService.removeEmployee(employee);
				
				tableModel.removeRow(i);
			}
		});
	}
}

