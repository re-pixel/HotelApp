package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.SpinnerDateModel;
import javax.swing.JLabel;
import javax.swing.JList;

import services.*;

import org.jdatepicker.*;

import models.Guest;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import java.awt.Color;

public class GuestView {

	private static final long serialVersionUID = 1L;
	private JComponent previous;
	private JFrame frame;


	/**
	 * Create the frame.
	 */
	public GuestView(Guest guest, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		
		initialize(guest, guestService, reservationService, roomService, serviceService, reportService);
		
	}
	
	public void initialize(Guest guest, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		this.frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Guest view");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton newReservationButton = new JButton("New Reservation");
		toolBar.add(newReservationButton);
		
		
		
		JButton myReservationsButton = new JButton("My Reservations");
		toolBar.add(myReservationsButton);
		
		JButton logoutButton = new JButton("Logout");
		toolBar.add(logoutButton);
		
		JPanel contentPanel = new JPanel();
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setBounds(336, 11, 67, 14);
		contentPanel.add(welcomeLabel);
		
		previous = contentPanel;
		
		
		newReservationButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				NewReservationPanel reservationPanel = new NewReservationPanel(guest, guestService, reservationService, roomService, serviceService);
				frame.getContentPane().add(reservationPanel, BorderLayout.CENTER);
				previous = reservationPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		
		myReservationsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				MyReservationsPanel myReservationsPanel = new MyReservationsPanel(guest, guestService, reservationService, roomService, serviceService, reportService);
				frame.getContentPane().add(myReservationsPanel, BorderLayout.CENTER);
				previous = myReservationsPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		
		logoutButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				LoginFrame newLogin = new LoginFrame();
			}
		});
		frame.setVisible(true);
	}
}
