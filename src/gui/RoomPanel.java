package gui;

import javax.swing.JPanel;

import services.RoomService;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.Room;

public class RoomPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	public RoomPanel(RoomService roomService) {
		setSize(800,500);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 60, 369, 245);
		add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		tableModel.addColumn("Id");
		tableModel.addColumn("Type");
		tableModel.addColumn("Status");
		tableModel.addColumn("Additionals");
		
		for(Room room: roomService.getAllRooms()) {
			tableModel.addRow(room.toString().split(","));
		}
		
		setVisible(true);
	}
}
