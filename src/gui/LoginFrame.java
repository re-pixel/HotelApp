package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import enums.Role;
import models.Guest;
import models.Staff;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import services.AdditionalAccommodationService;
import services.GuestService;
import services.PricingService;
import services.ReportService;
import services.ReservationService;
import services.RoomService;
import services.StaffService;

import java.awt.Color;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JLabel warningLabel;

	public LoginFrame() {
		
		GuestService guestService = new GuestService("./database/guests.csv");
		RoomService roomService = new RoomService("./database/rooms.csv");
		PricingService pricingService = new PricingService("./database/prices.csv");
		AdditionalAccommodationService serviceService = new AdditionalAccommodationService("./database/services.csv");
		ReservationService reservationService = new ReservationService("./database/reservations.csv", guestService, roomService, serviceService, pricingService);

		StaffService staffService = new StaffService("./database/staff.csv", guestService, roomService, reservationService);
		ReportService reportService = new ReportService("./database/reports.csv", staffService, roomService, reservationService);
		
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Login");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel usernameLabel = new JLabel("Username:");
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		
		passwordField = new JPasswordField();
		
		loginButton = new JButton("Login");
		
		warningLabel = new JLabel("Incorrect password or username!");
		warningLabel.setForeground(new Color(255, 0, 0));
		warningLabel.setVisible(false);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(267, Short.MAX_VALUE)
					.addComponent(loginButton)
					.addGap(109))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(99)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(warningLabel)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(passwordLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(usernameLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(passwordField)
								.addComponent(usernameField, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))
					.addContainerGap(134, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(42)
					.addComponent(warningLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(loginButton)
					.addContainerGap(80, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = (String) usernameField.getText();
				char[] passwordArray = passwordField.getPassword();
				String password = new String(passwordArray);
				
				Guest guest = guestService.Login(username, password);
				Staff employee = staffService.Login(username, password);
				
				
				
				
				if(guest != null) {
					dispose();
					new GuestView(guest, guestService, reservationService, roomService, serviceService, reportService);
				}

				else if(employee != null) {
					dispose();
					
					if(employee.getRole() == Role.ADMIN) {
						
						new AdminView(employee, guestService, staffService, reservationService, roomService, serviceService, reportService, pricingService);
						
						
					}
					else if(employee.getRole() == Role.AGENT) {
						
						new AgentView(employee, guestService, staffService, reservationService, roomService, serviceService, reportService);
					
						
					}
					else {
						
						new CleanerView(employee, staffService, roomService, reportService);
						
					}
				}
				
				else {
					usernameField.setText("");
					passwordField.setText("");
					warningLabel.setVisible(true);
				}
			}
		});
	}
}
