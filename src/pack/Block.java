package pack;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

public class Block 
{
	
	public JPanel panel;
	private SpringLayout layout;
	
	public <T> Block(String name, Dimension panelSize, DefaultTableModel tableModel, JFrame addFrame)//BiFunction<LinkedList<T>, DefaultTableModel, JFrame> functionPointer)
	{

		layout = new SpringLayout();
		panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createTitledBorder(name+"s"));
	    panel.setPreferredSize(panelSize);
	   
		JTable table = new JTable(tableModel);
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
	    JScrollPane scrollTable = new JScrollPane(table);
	    
    	JButton add;
    	if(name.equals("Hospital"))
    	{
    		add = new JButton("Add Hospital/ Section/ Doctor");
    	}
    	else
    	{
    		add = new JButton("Add "+name);
    	}
	    
	    add.addActionListener(
	    		new ActionListener() 
		        {
					@Override
					public void actionPerformed(ActionEvent e) 
					{
//							JFrame editFrame = functionPointer.apply(values, tableModel);
						addFrame.setVisible(true);
	
					}
		        }
	    );
	    
	    if(name.equals("Rendezvous"))
	    {
		    Dimension tableSize = new Dimension((int)(panelSize.getWidth()*.7), (int)(panelSize.getHeight()*.7));
		    scrollTable.setPreferredSize(tableSize);
		    add.setPreferredSize(new Dimension((int)(panelSize.getWidth()*0.7), (int)(panelSize.getHeight()*.17)));
		    
		    layout.putConstraint(SpringLayout.WEST, scrollTable, (int)(panelSize.getWidth()*.5-tableSize.getWidth()*.5), SpringLayout.WEST, panel);
		    layout.putConstraint(SpringLayout.NORTH, scrollTable, (int)(panelSize.getHeight()*.01), SpringLayout.NORTH, panel);
		    
		    layout.putConstraint(SpringLayout.WEST, add, (int)(panelSize.getWidth()*.5-tableSize.getWidth()*.5), SpringLayout.WEST, panel);
		    layout.putConstraint(SpringLayout.NORTH, add, (int)(panelSize.getHeight()*.03), SpringLayout.SOUTH, scrollTable);

	    }
	    else
	    {
		    Dimension tableSize = new Dimension((int)(panelSize.getWidth()*.9), (int)(panelSize.getHeight()*.80));
		    scrollTable.setPreferredSize(tableSize);
		    add.setPreferredSize(new Dimension((int)(panelSize.getWidth()*0.9), (int)(panelSize.getHeight()*.1)));
		    
		    layout.putConstraint(SpringLayout.WEST, scrollTable, (int)(panelSize.getWidth()*.05), SpringLayout.WEST, panel);
		    layout.putConstraint(SpringLayout.NORTH, scrollTable, (int)(panelSize.getHeight()*.01), SpringLayout.NORTH, panel);
		    
		    layout.putConstraint(SpringLayout.WEST, add, (int)(panelSize.getWidth()*.05), SpringLayout.WEST, panel);
		    layout.putConstraint(SpringLayout.NORTH, add, (int)(panelSize.getHeight()*.03), SpringLayout.SOUTH, scrollTable);

	    }
	    
	    panel.add(add);  
	    panel.add(scrollTable);   
	}
}
