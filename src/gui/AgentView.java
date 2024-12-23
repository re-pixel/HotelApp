package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import models.Staff;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;

public class AgentView {

	private JFrame frame;
	private JComponent previous;

	
	public AgentView(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		initialize(employee, guestService, staffService, reservationService, roomService, serviceService, reportService);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService) {
		this.frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Agent view");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton reservationsButton = new JButton("Reservations");
		toolBar.add(reservationsButton);
		
		JButton guestsButton = new JButton("Guests");
		toolBar.add(guestsButton);
		
		JButton newGuestButton = new JButton("New guest");
		toolBar.add(newGuestButton);
		
		JButton roomsButton = new JButton("Rooms");
		toolBar.add(roomsButton);
		
		JButton logoutButton = new JButton("Logout");
		toolBar.add(logoutButton);
		
		JPanel contentPanel = new JPanel();
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setBounds(336, 11, 67, 14);
		
		
		contentPanel.add(welcomeLabel);
		
		previous = contentPanel;
		
		frame.setVisible(true);
		
		
		reservationsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				ReservationsPanel reservationsPanel = new ReservationsPanel(frame, employee, guestService, reservationService, roomService, serviceService, reportService);
				frame.getContentPane().add(reservationsPanel, BorderLayout.CENTER);
				previous = reservationsPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		guestsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				GuestsPanel guestsPanel = new GuestsPanel(employee, guestService, staffService, reservationService, roomService, serviceService, reportService);
				frame.getContentPane().add(guestsPanel, BorderLayout.CENTER);
				previous = guestsPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		newGuestButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				NewGuestPanel newGuestPanel = new NewGuestPanel(employee, guestService, staffService);
				frame.getContentPane().add(newGuestPanel, BorderLayout.CENTER);
				previous = newGuestPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		roomsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				RoomPanel roomsPanel = new RoomPanel(roomService);
				frame.getContentPane().add(roomsPanel, BorderLayout.CENTER);
				previous = roomsPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		logoutButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				new LoginFrame();
			}
		});
		
	}
}
