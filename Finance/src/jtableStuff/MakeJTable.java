package jtableStuff;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
		table = new JTable(dataValues,columnNames);
		table.setRowSelectionAllowed(true);

		//TableModel model = table.getModel();
		
//		table.setModel(new DefaultTableModel(dataValues,columnNames){
//			
//			public void setDataVector(dataValues,columnNames){
//				
//			}
//			
//		});
		
		Class columnClass = String.class;
				
		table.setDefaultRenderer(columnClass, new DefaultTableCellRenderer(){
			
			 public Component getTableCellRendererComponent(JTable table, Object value,   
			            boolean isSelected, boolean hasFocus, int row, int column)   
			    {   
			        if(Integer.parseInt((String)table.getValueAt(row, 0)) > 3)  
			        {  
			            setForeground(Color.black);          
			            setBackground(Color.red);              
			        }      
			        else  
			        {      
			            setBackground(Color.white);      
			            setForeground(Color.black);      
			        }   
			        setText(value !=null ? value.toString() : "");  
			        return this;  
			    }  
		});
		
		
		table.getSelectionModel().addListSelectionListener(
		        new ListSelectionListener() {
		            public void valueChanged(ListSelectionEvent event) {
		                int viewRow = table.getSelectedRow();
		                if (viewRow < 0) {
		                   
		                } else {
		                	table.getModel().setValueAt("yummm",viewRow, 3);
		                	table.addRowSelectionInterval(3, 7);
		                	//table.getCellEditor().getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
		                	//table.getCellEditor(viewRow, 2).;
		                }
		            }
		        }
		);
		
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