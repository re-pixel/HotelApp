package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import models.AdditionalAccommodation;
import models.Reservation;
import services.AdditionalAccommodationService;
import services.GuestService;
import services.ReservationService;
import services.RoomService;

public class ChangeReservationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel endDateLabel;
	
	public ChangeReservationPanel(Reservation reservation, GuestService guestService, ReservationService reservationService, RoomService roomService, AdditionalAccommodationService serviceService) {
		
		JPanel datePanel = new JPanel();
		

		JScrollPane serviceScrollPane = new JScrollPane();
		serviceScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton btnNewButton = new JButton("Confirm");
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePanel, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
							.addGap(71)
							.addComponent(serviceScrollPane, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(311, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(serviceScrollPane, Alignment.LEADING)
						.addComponent(datePanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 211, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(btnNewButton)
					.addContainerGap(207, Short.MAX_VALUE))
		);
		
		JLabel serviceHeader = new JLabel("Services:");
		serviceScrollPane.setColumnHeaderView(serviceHeader);
		
		JPanel serviceBodyPanel = new JPanel();
		serviceScrollPane.setViewportView(serviceBodyPanel);
		
		
		List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
		for(AdditionalAccommodation service: serviceService.getAllServices()) {
			JCheckBox checkBox = new JCheckBox(service.getName());
			checkBoxList.add(checkBox);
			for(AdditionalAccommodation add: reservation.getServices()) {
				if(add.getName().equals(service.getName())){
					checkBox.setSelected(true);
				}
			}
				
		}
		
		
		GroupLayout gl_serviceBodyPanel = new GroupLayout(serviceBodyPanel);
		
		ParallelGroup checkBoxGroup = gl_serviceBodyPanel.createParallelGroup(Alignment.LEADING);
		
		for(JCheckBox box: checkBoxList) {
			checkBoxGroup.addComponent(box);
		}
		
		
		gl_serviceBodyPanel.setHorizontalGroup(
			gl_serviceBodyPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_serviceBodyPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(checkBoxGroup)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		
	
		
		SequentialGroup checkBoxVerticalGroup = gl_serviceBodyPanel.createSequentialGroup();
		for(JCheckBox box: checkBoxList) {
			checkBoxVerticalGroup.addComponent(box);
		}
		
		gl_serviceBodyPanel.setVerticalGroup(
			gl_serviceBodyPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(checkBoxVerticalGroup)
		);
		serviceBodyPanel.setLayout(gl_serviceBodyPanel);
		
		
		
		JLabel chooseDatesLabel = new JLabel("Choose dates");
		
		JLabel startDateLabel = new JLabel("Start date:");
		
		
			
		SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(new Date());
        
        // Create a JSpinner with the SpinnerDateModel
        JSpinner startDateSpinner = new JSpinner(model);
        
        // Set the JSpinner editor to display date format
        JSpinner.DateEditor de_startDateSpinner = new JSpinner.DateEditor(startDateSpinner, "dd-MM-yyyy");
        startDateSpinner.setEditor(de_startDateSpinner);
		
		endDateLabel = new JLabel("End date:");
		
		SpinnerDateModel model2 = new SpinnerDateModel();
        model2.setValue(new Date());
        
        // Create a JSpinner with the SpinnerDateModel
        JSpinner endDateSpinner = new JSpinner(model2);
        
        
        // Set the JSpinner editor to display date format
        JSpinner.DateEditor de_endDateSpinner = new JSpinner.DateEditor(endDateSpinner, "dd-MM-yyyy");
        endDateSpinner.setEditor(de_endDateSpinner);
		
        
		JComboBox<String> roomTypeComboBox = new JComboBox<String>();
		roomTypeComboBox.addItem(reservation.getDesiredType());
		for(String roomType: roomService.getAllRoomTypes()) {
			roomTypeComboBox.addItem(roomType);
		}
		
		JLabel roomTypeLabel = new JLabel("Room type:");
		
		
		GroupLayout gl_datePanel = new GroupLayout(datePanel);
		gl_datePanel.setHorizontalGroup(
			gl_datePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_datePanel.createSequentialGroup()
					.addGroup(gl_datePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_datePanel.createSequentialGroup()
							.addGap(118)
							.addComponent(chooseDatesLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_datePanel.createSequentialGroup()
							.addGap(61)
							.addGroup(gl_datePanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(roomTypeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(endDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(startDateLabel, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
							.addGap(50)
							.addGroup(gl_datePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(endDateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startDateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(roomTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_datePanel.createSequentialGroup()
							.addGap(419)))
					.addContainerGap(473, Short.MAX_VALUE))
		);
		gl_datePanel.setVerticalGroup(
			gl_datePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_datePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(chooseDatesLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_datePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(startDateLabel)
						.addComponent(startDateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_datePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(endDateLabel)
						.addComponent(endDateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addGroup(gl_datePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(roomTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(roomTypeLabel))
					.addGap(63)
					.addContainerGap(248, Short.MAX_VALUE))
		);
		
		datePanel.setLayout(gl_datePanel);
		setLayout(groupLayout);
		setVisible(true);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<AdditionalAccommodation> services = new ArrayList<AdditionalAccommodation>();
				for(JCheckBox box: checkBoxList) {
					if(box.isSelected()) {
						System.out.println(box.getText());
						services.add(serviceService.findService(box.getText()));
					}
				}
				
				reservationService.changeReservation(reservation, services);
				reservationService.updateRepository();
				
				setVisible(false);
			}
		});
	}


}
