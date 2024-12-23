package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import enums.Gender;
import enums.Role;
import models.AdditionalAccommodation;
import models.Reservation;
import models.Staff;
import services.AdditionalAccommodationService;
import services.ReservationService;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class ChangeReservationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private List<AdditionalAccommodation> chosenServices;

	public ChangeReservationDialog(Reservation reservation, ReservationService reservationService, AdditionalAccommodationService serviceService, JTable table, int i) {
		setBounds(100, 100, 396, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Services");
			lblNewLabel.setBounds(69, 39, 70, 15);
			contentPanel.add(lblNewLabel);
		}
		
		List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
		
		int y = 68;
		
		for(AdditionalAccommodation service: serviceService.getAllServices()){
			JCheckBox chckbxNewCheckBox = new JCheckBox(service.getName());
			checkBoxList.add(chckbxNewCheckBox);
			chckbxNewCheckBox.setBounds(69, y, 100, 21);
			y += 24;
			if(reservation.getServices().contains(service)) {
				chckbxNewCheckBox.setSelected(true);
			}
			contentPanel.add(chckbxNewCheckBox);
		}

		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						chosenServices = new ArrayList<AdditionalAccommodation>();
						
						for(JCheckBox checkBox: checkBoxList){
							if(checkBox.isSelected()) {
								chosenServices.add(serviceService.getServiceByName(checkBox.getText()));
							}
						}
						
						reservation.setServices(chosenServices);
						reservationService.calculatePrice(reservation);
						reservationService.updateRepository();

						table.setValueAt(reservation.getServicesString(), i, 3);
						table.setValueAt(reservation.getPrice(), i, 4);
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
		setVisible(true);
	}
	
	public List<AdditionalAccommodation> getServices(){
		return chosenServices;
	}
}
