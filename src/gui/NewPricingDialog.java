package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import enums.Gender;
import enums.Role;
import models.AdditionalAccommodation;
import models.DateInterval;
import models.Pricing;
import models.Staff;
import services.AdditionalAccommodationService;
import services.PricingService;
import services.RoomService;
import services.StaffService;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;

public class NewPricingDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable roomTable;
	private JTable additionalsTable;
	private Pricing newPricing;
	/**
	 * Create the dialog.
	 */
	public NewPricingDialog(JTable table, DefaultTableModel tableModel, PricingService pricingService, RoomService roomService, AdditionalAccommodationService serviceService) {
		setBounds(100, 100, 452, 530);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		DefaultTableModel roomTableModel = new DefaultTableModel();
		
		roomTableModel.addColumn("Type");
		roomTableModel.addColumn("Price");
		
		for(String type: roomService.getAllRoomTypes()) {
			roomTableModel.addRow(new String[] {type, ""});
		}
		
		
		DefaultTableModel additionalsTableModel = new DefaultTableModel();
		
		additionalsTableModel.addColumn("Service");
		additionalsTableModel.addColumn("Price");
		
		for(AdditionalAccommodation service: serviceService.getAllServices()) {
			additionalsTableModel.addRow(new String[] {service.getName(), ""});
		}
		
		JLabel lblRoomTypes = new JLabel("Room Types:");
		lblRoomTypes.setBounds(256, 71, 146, 21);
		contentPanel.add(lblRoomTypes);
		
		JLabel lblAdditionals = new JLabel("Additionals:");
		lblAdditionals.setBounds(50, 71, 146, 21);
		contentPanel.add(lblAdditionals);
		
		JLabel lblNewLabel = new JLabel("From:");
		lblNewLabel.setBounds(50, 31, 40, 14);
		contentPanel.add(lblNewLabel);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		JFormattedTextField startDateField = new JFormattedTextField(dateFormat);
		startDateField.setBounds(97, 28, 77, 21);
		contentPanel.add(startDateField);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(191, 31, 40, 14);
		contentPanel.add(lblTo);
		
		JFormattedTextField endDateField = new JFormattedTextField(dateFormat);
		endDateField.setBounds(223, 28, 77, 21);
		contentPanel.add(endDateField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 99, 146, 211);
		contentPanel.add(scrollPane);
		additionalsTable = new JTable(additionalsTableModel);
		scrollPane.setViewportView(additionalsTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(256, 99, 146, 150);
		contentPanel.add(scrollPane_1);
		roomTable = new JTable(roomTableModel);
		scrollPane_1.setViewportView(roomTable);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int len = roomTableModel.getRowCount();
						HashMap<String, Double> pricesMap = new HashMap<>();
						
						for(int i=0; i<len; i++) {
							String key = (String) roomTableModel.getValueAt(i, 0);
							double value = Double.parseDouble((String) roomTableModel.getValueAt(i, 1));
							pricesMap.put(key, value);
						}
						
						len = additionalsTableModel.getRowCount();
						
						for(int i=0; i<len; i++) {
							String key = (String) additionalsTableModel.getValueAt(i, 0);
							double value = Double.parseDouble((String) additionalsTableModel.getValueAt(i, 1));
							pricesMap.put(key, value);
						}
						
						Date startingDate = (Date) startDateField.getValue();
						Date endingDate = (Date) endDateField.getValue();
						
						LocalDate startDate = startingDate.toInstant().atZone(ZoneId.of("Europe/Belgrade")).toLocalDate();
						LocalDate endDate = endingDate.toInstant().atZone(ZoneId.of("Europe/Belgrade")).toLocalDate();
							
								
						DateInterval period = new DateInterval(startDate, endDate);
						
						newPricing = pricingService.createPriceList(period, pricesMap);
						pricingService.updateRepository();
						
						Vector<String> row = new Vector<String>();
						row.add(period.toString());
						for(int i=1;i<tableModel.getColumnCount();i++) {
							System.out.println(tableModel.getColumnName(i));
							row.add("" + newPricing.getPrice(tableModel.getColumnName(i)));
						}
						tableModel.addRow(row);
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
	
	public Pricing getNewPricing() {
		return newPricing;
	}
}
