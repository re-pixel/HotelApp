package gui;

import javax.swing.JPanel;

import models.Guest;
import models.Reservation;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

public class MyReservationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public MyReservationsPanel(Guest guest, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		
		setSize(800,500);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 50, 700, 375);
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
		
		List<Reservation> guestReservations = reservationService.getGuestReservations(guest);
		
		for(Reservation reservation: guestReservations) {
			tableModel.addRow(reservation.toString().split(","));
		}
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(661, 436, 89, 23);
		add(cancelButton);
		
		JButton changeButton = new JButton("Change");
		changeButton.setBounds(562, 436, 89, 23);
		add(changeButton);
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Reservation reservation = guestReservations.get(i);
				reservationService.cancelReservation(reservation);
				table.setValueAt(reservation.getReservationState(), i, 5);
				reportService.registerReservation(reservation);
			}
		});
		
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				Reservation reservation = guestReservations.get(i);
				
				ChangeReservationPanel changeReservationPanel = new ChangeReservationPanel(reservation, guestService, reservationService, roomService, serviceService);
				
			}
		});
		
	}

}
