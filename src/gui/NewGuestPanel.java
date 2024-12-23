package gui;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import enums.Gender;
import models.AdditionalAccommodation;
import models.DateInterval;
import models.Staff;
import services.GuestService;
import services.StaffService;

import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Color;

public class NewGuestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField username;
	private JTextField surname;
	private JTextField name;
	private JPasswordField password;
	private JTextField address;
	private JTextField phone;
	private JTextField dateOfBirth;

	/**
	 * Create the panel.
	 */
	public NewGuestPanel(Staff employee, GuestService guestService, StaffService staffService) {
		
		setSize(800, 500);
		setLayout(null);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(150, 70, 75, 15);
		add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(150, 120, 75, 15);
		add(passwordLabel);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(150, 170, 75, 15);
		add(nameLabel);
		
		JLabel surnameLabel = new JLabel("Surname:");
		surnameLabel.setBounds(150, 220, 75, 15);
		add(surnameLabel);
		
		JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
		dateOfBirthLabel.setBounds(400, 220, 82, 15);
		add(dateOfBirthLabel);
		
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setBounds(400, 120, 75, 15);
		add(addressLabel);
		
		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setBounds(400, 170, 75, 15);
		add(phoneLabel);
		
		JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setBounds(400, 70, 75, 15);
		add(genderLabel);
		
		username = new JTextField();
		username.setBounds(235, 67, 96, 20);
		add(username);
		username.setColumns(10);
		
		surname = new JTextField();
		surname.setBounds(235, 217, 96, 20);
		add(surname);
		surname.setColumns(10);
		
		name = new JTextField();
		name.setBounds(235, 167, 96, 20);
		add(name);
		name.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(235, 117, 96, 18);
		add(password);
		
		JRadioButton maleRadioButton = new JRadioButton("M");
		maleRadioButton.setBounds(481, 66, 42, 23);
		add(maleRadioButton);
		
		JRadioButton femaleRadioButton = new JRadioButton("F");
		femaleRadioButton.setBounds(525, 66, 42, 23);
		add(femaleRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(maleRadioButton);
		group.add(femaleRadioButton);
		
		address = new JTextField();
		address.setBounds(481, 117, 96, 20);
		add(address);
		address.setColumns(10);
		
		phone = new JTextField();
		phone.setBounds(481, 167, 96, 20);
		add(phone);
		phone.setColumns(10);
		
		dateOfBirth = new JTextField();
		dateOfBirth.setBounds(481, 217, 96, 20);
		add(dateOfBirth);
		dateOfBirth.setColumns(10);
		
		JButton createButton = new JButton("Create");
		createButton.setBounds(525, 285, 89, 23);
		add(createButton);
		
		JLabel lblNewLabel = new JLabel("*");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBounds(221, 70, 29, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("*");
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(221, 120, 29, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("*");
		lblNewLabel_2.setEnabled(false);
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(221, 170, 29, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("*");
		lblNewLabel_3.setEnabled(false);
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setBounds(221, 220, 29, 14);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("*");
		lblNewLabel_4.setEnabled(false);
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setBounds(463, 70, 29, 14);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("*");
		lblNewLabel_5.setEnabled(false);
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(463, 120, 29, 14);
		add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("*");
		lblNewLabel_6.setEnabled(false);
		lblNewLabel_6.setForeground(Color.RED);
		lblNewLabel_6.setBounds(463, 170, 29, 14);
		add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("*");
		lblNewLabel_7.setEnabled(false);
		lblNewLabel_7.setForeground(Color.RED);
		lblNewLabel_7.setBounds(474, 220, 29, 14);
		add(lblNewLabel_7);
		
		JLabel successLabel = new JLabel("New guest registered successfully!");
		successLabel.setForeground(new Color(0, 255, 64));
		successLabel.setBounds(235, 21, 287, 20);
		successLabel.setHorizontalAlignment(SwingConstants.CENTER);
		successLabel.setVisible(false);
		add(successLabel);
		
		
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Gender pol; 
				if(femaleRadioButton.isSelected()) {
					pol = Gender.FEMALE;
				}
				else {
					pol = Gender.MALE;
				}
				
				List<Integer> numbers = new ArrayList<>();
				
				for(String number: dateOfBirth.getText().split("/")) {
					numbers.add(Integer.parseInt(number));
				}
				
				staffService.addGuest(employee, name.getText(), surname.getText(), pol, LocalDate.of(numbers.get(2), numbers.get(1), numbers.get(0)), phone.getText(), address.getText(), username.getText(), new String(password.getPassword()));
				guestService.updateRepository();
				successLabel.setVisible(true);
				
				name.setText("");
				username.setText("");
				surname.setText("");
				dateOfBirth.setText("");
				address.setText("");
				phone.setText("");
				password.setText("");
				group.clearSelection();
				
			}
		});
	}

}
