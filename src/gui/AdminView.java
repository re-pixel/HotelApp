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
import services.PricingService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;

public class AdminView{

	private JFrame frame;
	private JComponent previous;

	public AdminView(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService, PricingService pricingService) {
		initialize(employee, guestService, staffService, reservationService, roomService, serviceService, reportService, pricingService);
		
	}
	
	public void initialize(Staff employee, GuestService guestService, StaffService staffService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService, ReportService reportService, PricingService pricingService) {
		
		this.frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Admin view");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton guestsButton = new JButton("Guests");
		toolBar.add(guestsButton);
		
		JButton staffButton = new JButton("Staff");
		toolBar.add(staffButton);
		
		JButton reservationsButton = new JButton("Reservations");
		toolBar.add(reservationsButton);
		
		JButton roomsButton = new JButton("Rooms");
		toolBar.add(roomsButton);
		
		JButton pricingButton = new JButton("Pricing");
		toolBar.add(pricingButton);
		
		JButton servicesButton = new JButton("Services");
		toolBar.add(servicesButton);
		
		JButton reportsButton = new JButton("Reports");
		toolBar.add(reportsButton);
		
		JButton graphsButton = new JButton("Graphs");
		toolBar.add(graphsButton);
		
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
		
		guestsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				GuestsPanel guestsPanel = new GuestsPanel(employee, guestService, staffService, reservationService, roomService, serviceService, reportService);
				guestsPanel.replaceButtons();
				
				frame.getContentPane().add(guestsPanel, BorderLayout.CENTER);
				previous = guestsPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		staffButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				StaffPanel staffPanel = new StaffPanel(employee, guestService, staffService, reservationService, roomService, serviceService);
				
				frame.getContentPane().add(staffPanel, BorderLayout.CENTER);
				previous = staffPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		reservationsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				ReservationsPanel reservationsPanel = new ReservationsPanel(frame, employee, guestService, reservationService, roomService, serviceService, reportService);
				reservationsPanel.replaceButtons();
				
				frame.getContentPane().add(reservationsPanel, BorderLayout.CENTER);
				previous = reservationsPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		
		pricingButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				PricingPanel pricingPanel = new PricingPanel(employee, guestService, staffService, reservationService, roomService, serviceService, pricingService);
				
				frame.getContentPane().add(pricingPanel, BorderLayout.CENTER);
				previous = pricingPanel;
				
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
		
		reportsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				ReportPanel reportPanel = new ReportPanel(reportService, staffService, roomService, reservationService, pricingService);
				
				frame.getContentPane().add(reportPanel, BorderLayout.CENTER);
				previous = reportPanel;
				
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
			}
		});
		
		
		graphsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(previous);
				GraphsPanel graphsPanel = new GraphsPanel(reportService, pricingService, roomService);
				
				frame.getContentPane().add(graphsPanel, BorderLayout.CENTER);
				previous = graphsPanel;
				
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
