package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import enums.ReservationState;
import models.Reservation;
import models.Staff;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReservationsPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton deleteButton;
	private JButton changeButton;
	private JButton approveButton;
	/**
	 * Create the 
	 */
	public ReservationsPanel(JFrame frame, Staff employee, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		initialize(frame, employee, guestService, reservationService, roomService, serviceService, reportService);
	}
	
	public void initialize(JFrame frame, Staff employee, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {

		setSize(800, 500);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 75, 650, 350);
		add(scrollPane);
		
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		tableModel.addColumn("User");
		tableModel.addColumn("Room/Room type");
		tableModel.addColumn("Interval");
		tableModel.addColumn("Services");
		tableModel.addColumn("Price");
		tableModel.addColumn("State");
		
		for(Reservation reservation: reservationService.getAllReservations()) {
			tableModel.addRow(reservation.toString().split(","));
		}
		
		approveButton = new JButton("Approve");
		approveButton.setBounds(636, 436, 89, 23);
		approveButton.setEnabled(true);
		add(approveButton);
		
		changeButton = new JButton("Change");
		changeButton.setBounds(537, 436, 89, 23);
		changeButton.setEnabled(true);
		add(changeButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(438, 436, 89, 23);
		deleteButton.setEnabled(false);
		add(deleteButton);
		
		
		
		setVisible(true);
		
		approveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Reservation reservation = reservationService.getAllReservations().get(i);
				boolean flag = reservationService.approveReservation(employee, reservation, roomService);
				reservationService.updateRepository();
				if(flag) {
					table.setValueAt(reservation.getRoom().getId(), i, 1);	
				}

				table.setValueAt(reservation.getReservationState(), i, 5);
				reportService.registerReservation(reservation);
			}
		});
		
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Reservation reservation = reservationService.getAllReservations().get(i);
				
				new ChangeReservationDialog(reservation, reservationService, serviceService, table, i);
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
	}
	
	public void replaceButtons() {
		remove(changeButton);
		remove(deleteButton);
		remove(approveButton);
		
	}
}
