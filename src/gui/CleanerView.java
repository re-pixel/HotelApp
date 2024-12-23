package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import models.Room;
import models.Staff;
import services.ReportService;
import services.RoomService;
import services.StaffService;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CleanerView {

	private JFrame frame;
	private JTable table;

	public CleanerView(Staff cleaner, StaffService staffService, RoomService roomService, ReportService reportService) {
		initialize(cleaner, staffService, roomService, reportService);
	}
	
	private void initialize(Staff cleaner, StaffService staffService, RoomService roomService, ReportService reportService) {
		frame = new JFrame();
		frame.setSize(400, 400);
		frame.setTitle("Cleaner view");
		frame.getContentPane().setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 386, 23);
		frame.getContentPane().add(toolBar);
		
		JButton roomsButton = new JButton("Rooms");
		toolBar.add(roomsButton);
		
		JButton logoutButton = new JButton("Logout");
		toolBar.add(logoutButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 34, 317, 268);
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		tableModel.addColumn("Room Id");
		tableModel.addColumn("Room Type");
		scrollPane.setViewportView(table);
		
		for(Room room: staffService.getRoomsToClean(cleaner)) {
			tableModel.addRow(new String[] {room.toString().split(",")[0], room.toString().split(",")[1]});
		}
		
		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(263, 309, 89, 23);
		frame.getContentPane().add(removeButton);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.setVisible(true);
		
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				staffService.finishCleaning(cleaner, staffService.getRoomsToClean(cleaner).get(i));
				tableModel.removeRow(i);
				reportService.getReport(LocalDate.now()).addCleanedRoom(cleaner);
				reportService.updateRepository();
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
