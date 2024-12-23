package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import enums.Gender;
import enums.Role;
import services.StaffService;
import models.Staff;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.SwingConstants;

public class NewEmployeeDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField dateField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public NewEmployeeDialog(StaffService staffService, JTable table, DefaultTableModel tableModel) {
		setBounds(100, 100, 480, 471);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(45, 30, 70, 13);
		contentPanel.add(usernameLabel);
		
		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setBounds(45, 80, 70, 13);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(45, 130, 70, 13);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Surname:");
		lblNewLabel_2.setBounds(45, 180, 70, 13);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Phone:");
		lblNewLabel_3.setBounds(45, 230, 70, 13);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Address:");
		lblNewLabel_4.setBounds(45, 280, 70, 13);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Role:");
		lblNewLabel_5.setBounds(239, 30, 51, 13);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Gender:");
		lblNewLabel_6.setBounds(239, 80, 70, 13);
		contentPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Years of experience:");
		lblNewLabel_7.setBounds(239, 130, 124, 13);
		contentPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Expertise:");
		lblNewLabel_8.setBounds(239, 180, 70, 13);
		contentPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Salary coef:");
		lblNewLabel_9.setBounds(239, 230, 70, 13);
		contentPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Date of Birth:");
		lblNewLabel_10.setBounds(239, 280, 106, 13);
		contentPanel.add(lblNewLabel_10);
		
		usernameField = new JTextField();
		usernameField.setBounds(131, 26, 84, 22);
		contentPanel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(131, 76, 84, 22);
		contentPanel.add(passwordField);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(131, 126, 84, 22);
		contentPanel.add(nameField);
		
		surnameField = new JTextField();
		surnameField.setColumns(10);
		surnameField.setBounds(131, 176, 84, 22);
		contentPanel.add(surnameField);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		phoneField.setBounds(131, 226, 84, 22);
		contentPanel.add(phoneField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(131, 276, 84, 22);
		contentPanel.add(addressField);
		
		JRadioButton maleRadioButton = new JRadioButton("M");
		maleRadioButton.setBounds(321, 75, 42, 23);
		contentPanel.add(maleRadioButton);
		
		JRadioButton femaleRadioButton = new JRadioButton("F");
		femaleRadioButton.setBounds(371, 75, 42, 23);
		contentPanel.add(femaleRadioButton);
		
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioButton);
		genderGroup.add(femaleRadioButton);
		
		JRadioButton agentRadioButton = new JRadioButton("AGENT");
		agentRadioButton.setBounds(293, 25, 70, 23);
		contentPanel.add(agentRadioButton);
		
		JRadioButton cleanerRadioButton = new JRadioButton("CLEANER");
		cleanerRadioButton.setBounds(365, 25, 89, 23);
		contentPanel.add(cleanerRadioButton);
		
		ButtonGroup roleGroup = new ButtonGroup();
		roleGroup.add(agentRadioButton);
		roleGroup.add(cleanerRadioButton);
		
		JComboBox expertiseBox = new JComboBox();
		expertiseBox.setModel(new DefaultComboBoxModel(new String[] {"college", "high school", "master", "phd"}));
		expertiseBox.setBounds(329, 175, 84, 22);
		contentPanel.add(expertiseBox);
		
		dateField = new JTextField();
		dateField.setColumns(10);
		dateField.setBounds(355, 276, 84, 22);
		contentPanel.add(dateField);
		
		JComboBox experienceBox = new JComboBox();
		experienceBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		experienceBox.setBounds(397, 128, 42, 17);
		contentPanel.add(experienceBox);
		
		JComboBox salaryBox = new JComboBox();
		salaryBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		salaryBox.setBounds(329, 228, 34, 17);
		contentPanel.add(salaryBox);
		
		JLabel lblNewLabel_11 = new JLabel("Invalid input, add missing values!");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setForeground(new Color(255, 0, 0));
		lblNewLabel_11.setBounds(118, 325, 214, 23);
		lblNewLabel_11.setVisible(false);
		contentPanel.add(lblNewLabel_11);
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Gender pol; 
						if(femaleRadioButton.isSelected()) {
							pol = Gender.FEMALE;
						}
						else {
							pol = Gender.MALE;
						}
						
						List<Integer> numbers = new ArrayList<>();
						
						for(String number: dateField.getText().split("/")) {
							numbers.add(Integer.parseInt(number));
						}
						
						Role role;
						if(agentRadioButton.isSelected()) {
							role = Role.AGENT;
						}
						else {
							role = Role.CLEANER;
						}
						Staff staff;
						
						if(role.equals(Role.AGENT)) {
							staff = staffService.createEmployee(nameField.getText(), surnameField.getText(), pol, LocalDate.of(numbers.get(2), numbers.get(1), numbers.get(0)), phoneField.getText(), addressField.getText(), usernameField.getText(), passwordField.getText(), expertiseBox.getSelectedItem().toString(), Integer.parseInt(experienceBox.getSelectedItem().toString()), Integer.parseInt(salaryBox.getSelectedItem().toString()), role);
						}
						else {
							staff = staffService.createEmployee(nameField.getText(), surnameField.getText(), pol, LocalDate.of(numbers.get(2), numbers.get(1), numbers.get(0)), phoneField.getText(), addressField.getText(), usernameField.getText(), passwordField.getText(), expertiseBox.getSelectedItem().toString(), Integer.parseInt(experienceBox.getSelectedItem().toString()), Integer.parseInt(salaryBox.getSelectedItem().toString()), role, List.of());
	
						}
						staffService.updateRepository();
						tableModel.addRow(new String[] {staff.getUsername(), staff.getName(), staff.getSurname(), ""+staff.getRole(), ""+staff.calculateSalary(), staff.getDateOfBirth()});
						table.revalidate();
						table.repaint();
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				
				buttonPane.add(cancelButton);
			}
		}
		
		setVisible(true);
	}
}
