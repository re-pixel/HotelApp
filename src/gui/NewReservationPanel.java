package gui;

import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;

import models.AdditionalAccommodation;
import models.DateInterval;
import models.Guest;
import models.Reservation;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.ReservationService;
import services.RoomService;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;

public class NewReservationPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private JLabel endDateLabel;
	
	/**
	 * Create the panel.
	 */
	public NewReservationPanel(Guest guest, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService) {
		
		setSize(800, 500);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Start Date:");
		lblNewLabel.setBounds(84, 65, 83, 16);
		add(lblNewLabel);
		
		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(84, 122, 83, 16);
		add(lblEndDate);
		
		JLabel lblRoomType = new JLabel("Room Type:");
		lblRoomType.setBounds(84, 177, 83, 16);
		add(lblRoomType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(174, 177, 50, 17);
		for(String roomType: roomService.getAllRoomTypes()) {
			comboBox.addItem(roomType);
		}
		
		add(comboBox);
		
		JPanel panel = new JPanel();
		panel.setBounds(312, 88, 111, 113);
		add(panel);
		panel.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("AC");
		chckbxNewCheckBox.setBounds(0, 3, 73, 21);
		panel.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("TV");
		chckbxNewCheckBox_1.setBounds(0, 24, 73, 21);
		panel.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("balcony");
		chckbxNewCheckBox_2.setBounds(0, 45, 73, 21);
		panel.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("kitchen");
		chckbxNewCheckBox_3.setBounds(0, 66, 73, 21);
		panel.add(chckbxNewCheckBox_3);
		
		JLabel lblRoomAdditionals = new JLabel("Room Additionals:");
		lblRoomAdditionals.setBounds(312, 65, 111, 16);
		add(lblRoomAdditionals);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(467, 88, 129, 192);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAdditionalServices = new JLabel("Additional Services:");
		lblAdditionalServices.setBounds(467, 65, 129, 16);
		add(lblAdditionalServices);
		
		List<JCheckBox> serviceBoxList = new ArrayList<JCheckBox>();
		
		int y = 3;
		for(AdditionalAccommodation service: serviceService.getAllServices()) {
			JCheckBox serviceBox = new JCheckBox(service.getName());
			serviceBox.setBounds(0, y, 90, 21);
			panel_1.add(serviceBox);
			serviceBoxList.add(serviceBox);
			y+=22;
		}
			
		SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(new Date());
        
        // Create a JSpinner with the SpinnerDateModel
        JSpinner startDateSpinner = new JSpinner(model);
        startDateSpinner.setSize(80, 20);
        startDateSpinner.setLocation(174, 64);
        
        // Set the JSpinner editor to display date format
        JSpinner.DateEditor de_startDateSpinner = new JSpinner.DateEditor(startDateSpinner, "dd-MM-yyyy");
        startDateSpinner.setEditor(de_startDateSpinner);
		
		endDateLabel = new JLabel("End date:");
		
		SpinnerDateModel model2 = new SpinnerDateModel();
        model2.setValue(new Date());
        
        // Create a JSpinner with the SpinnerDateModel
        JSpinner endDateSpinner = new JSpinner(model2);
        endDateSpinner.setLocation(174, 121);
        endDateSpinner.setSize(80, 20);
        
        
        // Set the JSpinner editor to display date format
        JSpinner.DateEditor de_endDateSpinner = new JSpinner.DateEditor(endDateSpinner, "dd-MM-yyyy");
        endDateSpinner.setEditor(de_endDateSpinner);
        
        add(startDateSpinner);
        add(endDateSpinner);
        
        JButton btnNewButton = new JButton("Confirm");
        btnNewButton.setBounds(536, 310, 98, 17);
        add(btnNewButton);
		
		
		
		
		
		setVisible(true);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date spinerStartDate = (Date) startDateSpinner.getValue();
				LocalDate startDate = spinerStartDate.toInstant().atZone(ZoneId.of("Europe/Belgrade")).toLocalDate();
				
				Date spinerEndDate = (Date) endDateSpinner.getValue();
				LocalDate endDate = spinerEndDate.toInstant().atZone(ZoneId.of("Europe/Belgrade")).toLocalDate();
				
				DateInterval interval = new DateInterval(startDate, endDate);
				
				String roomType = (String) comboBox.getSelectedItem();
				
				List<AdditionalAccommodation> services = new ArrayList<AdditionalAccommodation>();
				for(JCheckBox box: serviceBoxList) {
					if(box.isSelected()) {
						services.add(serviceService.findService(box.getText()));
					}
				}
				
				Reservation newReservation = reservationService.createReservation(guest, roomType, interval, services);
				reservationService.updateRepository();
			}
		});
	}
}
