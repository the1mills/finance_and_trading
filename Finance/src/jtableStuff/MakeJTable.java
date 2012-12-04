package jtableStuff;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MakeJTable extends JFrame {
	// Instance attributes used in this example
	private JPanel topPanel;
	private JTable table;
	private JScrollPane scrollPane;

	// Constructor of main frame
	public MakeJTable(String[] columnNames, Object[][] dataValues) {
		// Set the frame characteristics
		setTitle("Simple Table Application");
		setSize(600, 200);
		setBackground(Color.white);

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);

		
		for(int i = 0; i < dataValues.length -1 ; i++){
			for(int j = 0; j < dataValues[0].length; j++){
				System.out.println(dataValues[i][j].toString());
			}
		}

		// Create a new table instance
		table = new JTable(dataValues, columnNames);

		// Add the table to a scrolling pane
		scrollPane = new JScrollPane(table);
		topPanel.add(scrollPane, BorderLayout.CENTER);
	}

	// Main entry point for this example
//	public static void main(String args[]) {
//		// Create an instance of the test application
//		MakeJTable mainFrame = new MakeJTable(null,null);
//		mainFrame.setVisible(true);
//	}
}