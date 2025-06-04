package pack;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")

public class CRS 
{
	private static HashMap<Long, Patient> patients;
	private static HashMap<Integer, Hospital> hospitals;
	private static LinkedList<Rendezvous> rendezvous;
	private static LinkedList<Doctor> doctors;
	private static LinkedList<Section> sections;
	
	public CRS()
	{
		patients = new HashMap<Long, Patient>();
		hospitals = new HashMap<Integer, Hospital>();
		
		rendezvous = new LinkedList<Rendezvous>();
		doctors = new LinkedList<Doctor>();
		sections = new LinkedList<Section>();
	}
	
	public static LinkedList<Doctor> getDoctors() {return doctors;}
	
	public static LinkedList<Section> getSections() {return sections;}
	
	public static Collection<Hospital> getHospitals() {return hospitals.values();}
	
	
	//if this function cannot add the patient appropriate list return false.
	public static boolean addPatient(String name, long patientID) throws DuplicateInfoException
	{
		if(patients.get(patientID) == null)
		{
			Patient p = new Patient(name, patientID);
			if(patients.put(patientID, p) == null)
			{
				return false;
			}
			return true;
		}
		
		throw new DuplicateInfoException("");
	}
	
	//if this function cannot add the patient appropriate list return false.
	public boolean addPatient(Patient patient) throws DuplicateInfoException
	{
		if(patients.get(patient.getNationalID()) == null)
		{
			if(patients.put(patient.getNationalID(), patient) == null)
			{
				return false;
			}
			return true;
		}
		
		throw new DuplicateInfoException("");
	}
	
	public static Long getPatientID(String name)
	{
		for(Patient patient : patients.values())
		{
			if(patient.getName().equals(name)) {return patient.getNationalID();}
		}
		return null;
	}
	
	public static boolean addHospital(String name, int ID) throws DuplicateInfoException
	{
		if(hospitals.get(ID) == null)
		{
			Hospital h = new Hospital(ID, name);
			if(hospitals.put(h.getID(), h) == null)
			{
				return false;
			}
			return true;
		}
		throw new DuplicateInfoException("");
	}
	
	//if this function cannot add the hospital appropriate list return false.
	public static boolean addHospital(Hospital h) throws DuplicateInfoException
	{
		if(hospitals.get(h.getID()) == null)
		{
			if(hospitals.put(h.getID(), h) == null)
			{
				return false;
			}
			return true;
		}
		throw new DuplicateInfoException("");
	}
	
	
	public static Hospital getHospital(String name) 
	{
		for(Hospital hospital : hospitals.values())
		{
			if(hospital.getName().equals(name)) {return hospital;}
		}
		return null;
	}
	
	public Hospital getHospital(int id) 
	{
		return hospitals.get(id);
	}
	
	public boolean makeRendezvous(Date desiredDate, long patientID, int hospitalID, int sectionID, int diplomaID)
	{
		Patient  patient;
		Hospital hospital;
		Section  section;
		Doctor   doctor;
		
		if (patients.get(patientID) == null) 
		{
			return false;
		}
		patient = patients.get(patientID);
		

		if (hospitals.get(hospitalID) == null) 
		{
			return false;
		}
		hospital = hospitals.get(hospitalID);
		
		
		if (hospital.getSection(sectionID) == null) 
		{
			return false;
		}
		section = hospital.getSection(sectionID);
		
		
		if (section.getDoctor(diplomaID) == null) 
		{
			return false;
		}
		doctor = section.getDoctor(diplomaID);
		
		
		Rendezvous r;
		try 
		{
			r = new Rendezvous(desiredDate, patient, doctor);
			rendezvous.add(r);
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
		
	}
	
	public static void makeRendezvous(long patientID, int hospitalID, int sectionID, int diplomaID, Date desiredDate) throws Exception
	{
		Patient  patient;
		Hospital hospital;
		Section  section;
		Doctor   doctor;
		
		if (patients.get(patientID) == null) 
		{
			throw new IDException("This patient not added system yet.");
		}
		patient = patients.get(patientID);
		

		if (hospitals.get(hospitalID) == null) 
		{
			throw new IDException("This hospital not added system yet.");
		}
		hospital = hospitals.get(hospitalID);
		
		
		if (hospital.getSection(sectionID) == null) 
		{
			throw new IDException("This section not added system yet.");
		}
		section = hospital.getSection(sectionID);
		
		
		if (section.getDoctor(diplomaID) == null) 
		{
			throw new IDException("This doctor not added system yet.");
		}
		doctor = section.getDoctor(diplomaID);
		
		
		Rendezvous r = new Rendezvous(desiredDate, patient, doctor);
		
		rendezvous.add(r);
		
	}

	public String toString()
	{
		String result = "Patiens\n";
		
		for(Patient p : patients.values())
		{
			result += p +"\n";
		}
		
		result += "\nHospitals\n";
		
		for(Hospital h : hospitals.values())
		{
			result += h +"\n";
		}
		
		result += "\nRendezvous\n";
		
		for(Rendezvous r : rendezvous)
		{
			result += r +"\n";
		}
		
		return result ;
	}
	
	public static class GUI
	{
		private static JFrame frame;
		private static SpringLayout layout;
		
		static int frameSizeX;
		static int frameSizeY;
		
		private static JPanel hospitalPanel;
		private static JPanel sectionPanel;
		private static JPanel doctorPanel;
		private static JPanel patientPanel;
		private static JPanel rendezvousPanel;
		
		private static DefaultTableModel sectionTableModel;
		private static DefaultTableModel doctorTableModel;
		
		private static Dimension screenSize;
		private static Dimension panelSize;
		
		public GUI() 
		{
			
		}
		
		public static void setUp()
		{
			
			// to get screen size
	        Toolkit toolkit = Toolkit.getDefaultToolkit();
	        // Get the screen size
	        screenSize = toolkit.getScreenSize();
	        
	        //create default frame
			layout = new SpringLayout();
			frame = new JFrame("CRS");
			frame.setLayout(layout);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        frameSizeX = (int)(screenSize.getWidth()/1.2);
	        frameSizeY = (int)(screenSize.getHeight()/1.05);
	        
	        frame.setSize(frameSizeX, frameSizeY);
	        frame.setLocationRelativeTo(null); // Center the frame on the screen
	        
	        panelSize = new Dimension((int)(GUI.frameSizeX*.24), (int)(GUI.frameSizeY*.6));
	        // Create a menu bar  
            JMenuBar bar = new JMenuBar();  
            JMenu menu = new JMenu("File");  
            bar.add(menu);
            JMenuItem save = new JMenuItem("Save file");
            save.addActionListener(new ActionListener(){
            //use synchronized for thread safety
				@Override
				public synchronized void actionPerformed(ActionEvent e) 
				{
					save();
					
				}});
            JMenuItem load = new JMenuItem("Load file");
            load.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					load(false);				
				}});
            menu.add(save);  
            menu.add(load);  
            frame.setJMenuBar(bar);
	        
	        //Hospital Block
			String[] hospitalColumn = {"Name", "ID"};
			String[][] hospitalRows = new  String[hospitals.values().size()][];
			int hosptialIndex = 0;
			
			for(Hospital h :  hospitals.values())
			{
				hospitalRows[hosptialIndex] = h.getInfo();
				hosptialIndex++;
			}

			DefaultTableModel hosptialTableModel = new DefaultTableModel(hospitalRows, hospitalColumn){
	            @Override // Make all cells non-editable
	            public boolean isCellEditable(int row, int column) {return false;}};
	        
	        Block hospitalBlock = new Block("Hospital", panelSize, hosptialTableModel, addFrameHospital(hosptialTableModel));
	        hospitalPanel = hospitalBlock.panel;
			//end of Hospital Block
	        
	        //Section Block
			String[] sectionColumn = {"Name", "ID"};
			String[][] sectionRows = new  String[sections.size()][];
			int sectionIndex = 0;
			
			for(Section h :  sections)
			{
				sectionRows[sectionIndex] = h.getInfo();
				sectionIndex++;
			}

			sectionTableModel = new DefaultTableModel(sectionRows, sectionColumn) {
	            @Override// Make all cells non-editable
	            public boolean isCellEditable(int row, int column) {return false;}};
	        Block sectionBlock = new Block("Section", panelSize, sectionTableModel, addFrameSection(null, null));
	        sectionPanel = sectionBlock.panel;
			//end of Section Block
	        
	        //Doctor Block
	        String[] doctorColumn = {"Name", "National ID", "Diploma ID"};
	        String[][] doctorRows = new  String[doctors.size()][];
	        int doctorIndex = 0;
	        
	        for(Doctor d :  doctors)
	        {
	        	doctorRows[doctorIndex] = d.getInfo();
	        	doctorIndex++;
	        }
	        
	        doctorTableModel = new DefaultTableModel(doctorRows, doctorColumn){
	            @Override // Make all cells non-editable
	            public boolean isCellEditable(int row, int column) {return false;}};
	            
	        Block doctorBlock = new Block("Doctor", panelSize, doctorTableModel, addFrameDoctor(null,null));
	        doctorPanel = doctorBlock.panel;
	        //end of Doctor Block
	        
	        
	        //Patient Block
	        String[] patientColumn = {"Name", "National ID"};
	        String[][] patientRows = new  String[patients.values().size()][];
	        int patientIndex = 0;
	        
	        for(Patient d :  patients.values())
	        {
	        	patientRows[patientIndex] = d.getInfo();
	        	patientIndex++;
	        }
	        
	        DefaultTableModel patientTableModel = new DefaultTableModel(patientRows, patientColumn){
	            @Override // Make all cells non-editable
	            public boolean isCellEditable(int row, int column) {return false; }};
	            
	        Block patientBlock = new Block("Patient", panelSize, patientTableModel, addFramePatient(patientTableModel));
	        patientPanel = patientBlock.panel;
	        //end of Patient Block
	        
	        
	        //Rendezvous Block
	        String[] rendezvousColumn = {"Patient", "Hosptial ID", "Section ID", "Doctor", "Date"};
	        String[][] rendezvousRows = new  String[rendezvous.size()][];
	        int rendezvousIndex = 0;
	        
	        for(Rendezvous d :  rendezvous)
	        {
	        	rendezvousRows[rendezvousIndex] = d.getInfo();
	        	rendezvousIndex++;
	        }
	        
	        DefaultTableModel rendezvousTableModel = new DefaultTableModel(rendezvousRows, rendezvousColumn){
	            @Override // Make all cells non-editable
	            public boolean isCellEditable(int row, int column) {return false;}};
	            
	        Dimension rendezvousPanelSize = new Dimension ((int)(frameSizeX*.98),(int)(frameSizeY*.34));
	        Block rendezvousBlock = new Block("Rendezvous", rendezvousPanelSize, rendezvousTableModel, addFrameRendezvous2(rendezvousTableModel));
	        rendezvousPanel = rendezvousBlock.panel;
	        //end of Rendezvous Block

	        layout.putConstraint(SpringLayout.WEST, hospitalPanel, (int)(frameSizeX*0.005), SpringLayout.WEST, frame);
	        layout.putConstraint(SpringLayout.NORTH, hospitalPanel, (int)(frameSizeY*0.0025), SpringLayout.NORTH, frame);
	        
	        layout.putConstraint(SpringLayout.WEST, sectionPanel, (int)(frameSizeX*0.005), SpringLayout.EAST, hospitalPanel);
	        layout.putConstraint(SpringLayout.NORTH, sectionPanel, (int)(frameSizeY*0.0025), SpringLayout.NORTH, frame);
	        
	        layout.putConstraint(SpringLayout.WEST, doctorPanel, (int)(frameSizeX*0.005), SpringLayout.EAST, sectionPanel);
	        layout.putConstraint(SpringLayout.NORTH, doctorPanel, (int)(frameSizeY*0.0025), SpringLayout.NORTH, frame);
	        
	        layout.putConstraint(SpringLayout.WEST, patientPanel, (int)(frameSizeX*0.005), SpringLayout.EAST, doctorPanel);
	        layout.putConstraint(SpringLayout.NORTH, patientPanel, (int)(frameSizeY*0.0025), SpringLayout.NORTH, frame);
	        
	        layout.putConstraint(SpringLayout.WEST, rendezvousPanel, (int)(frameSizeX*0.005), SpringLayout.WEST, frame);
	        layout.putConstraint(SpringLayout.NORTH, rendezvousPanel, (int)(frameSizeY*0.0025), SpringLayout.SOUTH, hospitalPanel);
	        
	        frame.add(hospitalPanel);
	        frame.add(sectionPanel);
	        frame.add(doctorPanel);
	        frame.add(patientPanel);
	        frame.add(rendezvousPanel);
	        
			frame.setVisible(true);	
		}
		
		public static void close() {frame.dispose();}
		
		//i made it synchronized because to prevent to try load and save same file simultaneously.
		public synchronized static void load(boolean isTerminal)
		{
			try 
			{	
				ObjectInputStream reader = new ObjectInputStream(new FileInputStream("file.ser"));
				//HashMaps
				
				Integer count = (Integer) reader.readObject();
				for(int i = 0; i < count; i++) 
				{
					Patient p = (Patient)reader.readObject();
					patients.put(p.getNationalID(),p);
				}
				
				count = (Integer) reader.readObject();
				for(int i = 0; i < count; i++) 
				{
					Hospital h = (Hospital)reader.readObject();
					hospitals.put(h.getID(),h);
				}
				
				//LinkedLists
				count = (Integer) reader.readObject();
				for(int i = 0; i < count; i++) 
				{
					rendezvous.add((Rendezvous)reader.readObject());
				}
				
				count = (Integer) reader.readObject();
				for(int i = 0; i < count; i++) 
				{
					doctors.add((Doctor)reader.readObject());
				}
				
				count = (Integer) reader.readObject();
				for(int i = 0; i < count; i++) 
				{
					sections.add((Section)reader.readObject());
				}

				reader.close();
				
				if(!isTerminal)
				{
					GUI.close();
					GUI.setUp();
					JOptionPane.showMessageDialog(frame, "Your infos loaded.");
				}
				
				
			} catch (IOException e1) {
				if(isTerminal)
				{
					System.out.println("Your infos could not load!");
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Your infos could not load!");
				}
			} catch (ClassNotFoundException e1) {
				if(isTerminal)
				{
					System.out.println("Your infos could not load!");
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Your infos could not load!");
				}
			}
		}
		
		public synchronized static void save()
		{
			try 
			{	
				ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("file.ser"));
				//HashMaps
				writer.writeObject(patients.size());
				for(Patient a : patients.values()) writer.writeObject(a);
				
				writer.writeObject(hospitals.size());
				for(Hospital a : hospitals.values()) writer.writeObject(a);
				
				//LinkedLists
				writer.writeObject(rendezvous.size());
				for(Rendezvous a : rendezvous) writer.writeObject(a);
				
				writer.writeObject(doctors.size());
				for(Doctor a : doctors) writer.writeObject(a);
				
				writer.writeObject(sections.size());
				for(Section a : sections) writer.writeObject(a);

				writer.close();
				JOptionPane.showMessageDialog(frame, "Your infos saved as a file!");
			} 
			catch (IOException e1) {
				
				e1.printStackTrace();
				
				JOptionPane.showMessageDialog(frame, "Your infos could not save as a file!");
			}
		}
		
		private static JFrame addFrameHospital(DefaultTableModel model)
		{
			JFrame editFrame = new JFrame("Add Hospital/ Section/ Doctor");
			Dimension hospitalPanelSize = panelSize;
			
			editFrame.setLayout(new FlowLayout(FlowLayout.LEFT, (int)(hospitalPanelSize.getHeight()*.03),(int)(hospitalPanelSize.getWidth()*.03)));
	        editFrame.setSize((int)(hospitalPanelSize.getWidth()*1.3), (int)hospitalPanelSize.getHeight());
	        editFrame.setLocationRelativeTo(frame); // Center the frame on the main frame
	        
	        //Panel for name Label and Text field
	        JPanel namePanel = new JPanel();
	        JLabel nameLabel = new JLabel("Name: ");
	        nameLabel.setPreferredSize(new Dimension((int)(hospitalPanelSize.getWidth()*.15),(int)(hospitalPanelSize.getHeight()*.04)));
	        JTextField nameTextField = new JTextField();
	        nameTextField.setPreferredSize(new Dimension((int)(hospitalPanelSize.getWidth()*.5),(int)(hospitalPanelSize.getHeight()*.04)));
	        namePanel.add(nameLabel); 
	        namePanel.add(nameTextField); 
	        
	      	//Panel for ID Label and Text field
	        JPanel IDPanel = new JPanel();
	        JLabel IDLabel = new JLabel("ID: ");
	        IDLabel.setPreferredSize(new Dimension((int)(hospitalPanelSize.getWidth()*.15),(int)(hospitalPanelSize.getHeight()*.04)));
	        JTextField IDTextField = new JTextField();
	        IDTextField.setPreferredSize(new Dimension((int)(hospitalPanelSize.getWidth()*.5),(int)(hospitalPanelSize.getHeight()*.04)));
	        IDPanel.add(IDLabel); 
	        IDPanel.add(IDTextField); 
	        
	        //Section Block
	        Hospital hospitalForSections = new Hospital(1111, "dummy");

	        String[] sectionColumn = {"Name", "ID"};
			String[][] sectionRows = new  String[hospitalForSections.getSections().size()][];
			int sectionsIndex = 0;
			
			for(Section s : hospitalForSections.getSections())
			{
				sectionRows[sectionsIndex] = s.getInfo();
				sectionsIndex++;
			}

			DefaultTableModel tableModel = new DefaultTableModel(sectionRows, sectionColumn){
	            @Override // Make all cells non-editable
	            public boolean isCellEditable(int row, int column) { return false;}};
	            
	        Dimension sectionPanelSize = new Dimension((int)(editFrame.getWidth()*.9), (int)(editFrame.getHeight()*.65));
	        Block sectionBlock = new Block("Section", sectionPanelSize, tableModel, addFrameSection(tableModel, hospitalForSections));
			JPanel sectionPanel = sectionBlock.panel;
			//end of Section Block
	        
	        
	        JButton hospitalAdd = new JButton("Add Hospital");
	        hospitalAdd.setPreferredSize(new Dimension((int)(hospitalPanelSize.getWidth()*0.9), (int)(hospitalPanelSize.getHeight()*.05)));
			
	        hospitalAdd.addActionListener(
			        new ActionListener() 
			        {
			            @Override
			            public void actionPerformed(ActionEvent e) 
			            {
			            	try
			            	{
			            		Hospital hospital = new Hospital(Integer.parseInt(IDTextField.getText()), nameTextField.getText());
				            	hospital.setSections(hospitalForSections.getSections());
				            	
				            	CRS.addHospital(hospital);
				            	
				            	model.addRow(new String[]
				            			{nameTextField.getText(), 
				            			IDTextField.getText(), 
				            			String.valueOf(hospital.getSections().size())});
				            	
				            	nameTextField.setText("");
				            	IDTextField.setText("");
				            	hospitalForSections.setSections(new LinkedList<>());
				            	tableModel.setRowCount(0);
				            	
				            	editFrame.setVisible(false);
			            	}
			            	catch(DuplicateInfoException err)
			            	{
			            		JOptionPane.showMessageDialog(editFrame, "You cannot add same Id Hospital!");;
			            	}
			            	catch(Exception err)
			            	{
			            		JOptionPane.showMessageDialog(editFrame, "Please sure enter infos correctly!");;
			            	}
			            	
			            }
			        }
			);
	        
	        editFrame.add(namePanel);
	        editFrame.add(IDPanel);
	        editFrame.add(sectionPanel);
	        editFrame.add(hospitalAdd);
	        
	        return editFrame;
		}
		
		private static JFrame addFrameSection(DefaultTableModel model, Hospital hospitalForSections)
		{
			JFrame frameE = new JFrame("Add section");
			Dimension size = new Dimension((int)(GUI.frameSizeX*.24), (int)(GUI.frameSizeY*.75));
			
			frameE.setLayout(new FlowLayout(FlowLayout.LEFT, (int)(size.getHeight()*.03),(int)(size.getWidth()*.03)));
			frameE.setSize((int)(size.getWidth()*1.3), (int)size.getHeight());
			frameE.setLocationRelativeTo(frame); // Center the frame on the main frame

			JPanel nameP = new JPanel();
			JLabel nameL = new JLabel("Name: ");
			nameL.setPreferredSize(new Dimension((int)(size.getWidth()*.15),(int)(size.getHeight()*.04)));
			JTextField nameTF = new JTextField();
			nameTF.setPreferredSize(new Dimension((int)(size.getWidth()*.5),(int)(size.getHeight()*.04)));
			nameP.add(nameL); 
			nameP.add(nameTF); 
			frameE.add(nameP);

			JPanel IDPanel = new JPanel();
			JLabel IDLabel = new JLabel("ID: ");
			IDLabel.setPreferredSize(new Dimension((int)(size.getWidth()*.15),(int)(size.getHeight()*.04)));
			JTextField IDTextField = new JTextField();
			IDTextField.setPreferredSize(new Dimension((int)(size.getWidth()*.5),(int)(size.getHeight()*.04)));
			IDPanel.add(IDLabel); 
			IDPanel.add(IDTextField); 
			frameE.add(IDPanel);

			JTextField HIDTextField = new JTextField();
			
			if(hospitalForSections == null && model == null)
			{
				JPanel HIDPanel = new JPanel();
				JLabel HIDLabel = new JLabel("Hospital ID: ");
				HIDLabel.setPreferredSize(new Dimension((int)(size.getWidth()*.25),(int)(size.getHeight()*.04)));
				HIDTextField.setPreferredSize(new Dimension((int)(size.getWidth()*.5),(int)(size.getHeight()*.04)));
				HIDPanel.add(HIDLabel); 
				HIDPanel.add(HIDTextField);
				frameE.add(HIDPanel);
				
			}

			//Doctor Block
			Section sectionForDoctors = new Section(1111, "dummy");
			
			String[] column = {"Name", "ID", "Diploma ID"};
			String[][] rows = new  String[sectionForDoctors.getDoctors().size()][];
			int index = 0;
			
			for(Doctor s : sectionForDoctors.getDoctors())
			{
			    rows[index] = s.getInfo();
			    index++;
			}

			DefaultTableModel tableModel = new DefaultTableModel(rows, column){
	            @Override // Make all cells non-editable
	            public boolean isCellEditable(int row, int column) {return false;}};
	            
	        Dimension doctorPanelSize = new Dimension((int)(frameE.getWidth()*.9), (int)(frameE.getHeight()*.65));
	        Block doctorBlock = new Block("Doctor", doctorPanelSize, tableModel, addFrameDoctor(tableModel, sectionForDoctors));
			JPanel doctorPanel = doctorBlock.panel;
			//end of Doctor Block
			
			JButton sectionAdd = new JButton("Add Sections");
			sectionAdd.setPreferredSize(new Dimension((int)(size.getWidth()*0.9), (int)(size.getHeight()*.05)));
			
			sectionAdd.addActionListener(
			        new ActionListener() 
			        {
			            @Override
			            public void actionPerformed(ActionEvent e) 
			            {
			            	try
			            	{
			            		Section section = new Section(Integer.parseInt(IDTextField.getText()), nameTF.getText());
				            	section.setDoctors(sectionForDoctors.getDoctors());
				            	
				            	if(hospitalForSections == null && model == null)
				    			{
				            		Hospital hospital = hospitals.get(Integer.parseInt(HIDTextField.getText()));
				            		if(hospital  == null)
				            		{
				            			JOptionPane.showMessageDialog(frameE, "Hospital ID was not found in the System");
				            		}
				            		else
				            		{
				            			hospital.addSection(section);
				            			
				            			sections.add(section);
						            	
						            	sectionTableModel.addRow(new String[]
						            			{nameTF.getText(), 
						            			IDTextField.getText(), 
						            			String.valueOf(section.getDoctors().size())});
						            	
						            	nameTF.setText("");
						            	IDTextField.setText("");
						            	HIDTextField.setText("");
						            	tableModel.setRowCount(0);
						            	
						            	frameE.setVisible(false);
				            		}
				    			}
				            	else
				            	{
				            		hospitalForSections.addSection(section);
					            	
					            	model.addRow(new String[]
					            			{nameTF.getText(), 
					            					IDTextField.getText(), 
					            					String.valueOf(section.getDoctors().size())});
					            	
					            	sections.add(section);
					            	
					            	sectionTableModel.addRow(new String[]
					            			{nameTF.getText(), 
					            			IDTextField.getText(), 
					            			String.valueOf(section.getDoctors().size())});
					            	
					            	nameTF.setText("");
					            	IDTextField.setText("");
					            	sectionForDoctors.setDoctors(new LinkedList<>());
					            	tableModel.setRowCount(0);
					            	
					            	frameE.setVisible(false);
				            	}
			            	}
			            	catch(DuplicateInfoException err)
			            	{
			            		JOptionPane.showMessageDialog(frameE, "You cannot add same Id Section!");;
			            	}
			            	catch(Exception err)
			            	{
			            		JOptionPane.showMessageDialog(frameE, "Please sure enter infos correctly!");;
			            	}
			            }
			        }
			);

			frameE.add(doctorPanel);
			frameE.add(sectionAdd);

			return frameE;
		}
		
		private static JFrame addFrameDoctor(DefaultTableModel tableModel, Section sectionForDoctors)
		{
			JFrame editFrame = new JFrame("Add doctor");
			Dimension doctorPanelSize = panelSize;

			editFrame.setLayout(new FlowLayout(FlowLayout.LEFT, (int)(doctorPanelSize.getHeight()*.03),(int)(doctorPanelSize.getWidth()*.03)));
			editFrame.setSize((int)(doctorPanelSize.getWidth()*1.3), (int)doctorPanelSize.getHeight());
			editFrame.setLocationRelativeTo(frame); // Center the frame on the main frame

			JPanel namePanel = new JPanel();
			JLabel nameLabel = new JLabel("Name: ");
			nameLabel.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.25),(int)(doctorPanelSize.getHeight()*.04)));
			JTextField nameTextField = new JTextField();
			nameTextField.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.5),(int)(doctorPanelSize.getHeight()*.04)));
			namePanel.add(nameLabel); 
			namePanel.add(nameTextField); 
			editFrame.add(namePanel);


			JPanel IDPanel = new JPanel();
			JLabel IDLabel = new JLabel("ID: ");
			IDLabel.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.25),(int)(doctorPanelSize.getHeight()*.04)));
			JTextField IDTextField = new JTextField();
			IDTextField.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.5),(int)(doctorPanelSize.getHeight()*.04)));
			IDPanel.add(IDLabel); 
			IDPanel.add(IDTextField); 
			editFrame.add(IDPanel);


			JPanel DIDPanel = new JPanel();
			JLabel DIDLabel = new JLabel("Diploma ID: ");
			DIDLabel.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.25),(int)(doctorPanelSize.getHeight()*.04)));
			JTextField DIDTextField = new JTextField();
			DIDTextField.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.5),(int)(doctorPanelSize.getHeight()*.04)));
			DIDPanel.add(DIDLabel); 
			DIDPanel.add(DIDTextField); 
			editFrame.add(DIDPanel);

			
			JTextField SIDTextField = new JTextField();
			JTextField HIDTextField = new JTextField();
			
			if(sectionForDoctors == null && tableModel == null)
			{
				JPanel SIDPanel = new JPanel();
				JLabel SIDLabel = new JLabel("Section ID: ");
				SIDLabel.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.25),(int)(doctorPanelSize.getHeight()*.04)));
				SIDTextField.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.5),(int)(doctorPanelSize.getHeight()*.04)));
				SIDPanel.add(SIDLabel); 
				SIDPanel.add(SIDTextField);
				editFrame.add(SIDPanel);


				JPanel HIDPanel = new JPanel();
				JLabel HIDLabel = new JLabel("Hospital ID: ");
				HIDLabel.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.25),(int)(doctorPanelSize.getHeight()*.04)));
				HIDTextField.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*.5),(int)(doctorPanelSize.getHeight()*.04)));
				HIDPanel.add(HIDLabel); 
				HIDPanel.add(HIDTextField);
				editFrame.add(HIDPanel);
				
			}
			
			JButton doctorAdd = new JButton("Add doctor");
			doctorAdd.setPreferredSize(new Dimension((int)(doctorPanelSize.getWidth()*0.9), (int)(doctorPanelSize.getHeight()*.05)));
			
			doctorAdd.addActionListener(
			        new ActionListener() 
			        {
			            @Override
			            public void actionPerformed(ActionEvent e) 
			            {
			            	try
			            	{
			            		Doctor doctor = new Doctor(nameTextField.getText(), 
         							   Long.parseLong(IDTextField.getText()), 
         							   Integer.parseInt(DIDTextField.getText()));
         	
	         	
					         	if(sectionForDoctors == null && tableModel == null)
					 			{
					         		Hospital hospital = hospitals.get(Integer.parseInt(HIDTextField.getText()));
					         		
					         		if(hospital == null)
					     			{
					         			JOptionPane.showMessageDialog(editFrame,"Hospital ID was not found in the System");  
					     			}
					         		else
					         		{
						            		Section section = hospital.getSection(Integer.parseInt(SIDTextField.getText()));
						            		if(section == null)
					         			{
						            			JOptionPane.showMessageDialog(editFrame,"Section ID was not found in the Hospital you entered.");  
					         			}
						            		else
						            		{
						            			section.addDoctor(doctor);
						            			doctors.add(doctor);
								            	
								            	doctorTableModel.addRow(new String[]
								            			{nameTextField.getText(), 
								            			IDTextField.getText(), 
								            			DIDTextField.getText()});
								            	
								            	nameTextField.setText("");
								            	IDTextField.setText("");
								            	DIDTextField.setText("");
								            	SIDTextField.setText("");
								            	HIDTextField.setText("");
								            	editFrame.setVisible(false);
						            		}
					         		}
				 			}
				         	else
				         	{
				         		sectionForDoctors.addDoctor(doctor);
					            	
					            	tableModel.addRow(new String[]
					            			{nameTextField.getText(), 
					            					IDTextField.getText(), 
					            					DIDTextField.getText()});
					            	doctors.add(doctor);
					            	
					            	doctorTableModel.addRow(new String[]
					            			{nameTextField.getText(), 
					            			IDTextField.getText(), 
					            			DIDTextField.getText()});
					            	
					            	
					            	nameTextField.setText("");
					            	IDTextField.setText("");
					            	DIDTextField.setText("");
					            	editFrame.setVisible(false);
				         	}
			            	}
			            	catch(DuplicateInfoException err)
			            	{
			            		JOptionPane.showMessageDialog(editFrame, "You cannot add same Id Doctor!");;
			            	}
			            	catch(Exception err)
			            	{
			            		JOptionPane.showMessageDialog(editFrame, "Please sure enter infos correctly!");;
			            	}
			            }
			        }
			);

			editFrame.add(doctorAdd);

			return editFrame;
		}
		
		private static JFrame addFramePatient(DefaultTableModel tableModel)
		{
			JFrame editFrame = new JFrame("Add Patient");
			Dimension patientPanelSize = panelSize;
			
			editFrame.setLayout(new FlowLayout(FlowLayout.LEFT, (int)(patientPanelSize.getHeight()*.03),(int)(patientPanelSize.getWidth()*.03)));
			editFrame.setSize((int)(patientPanelSize.getWidth()*1.3), (int)patientPanelSize.getHeight());
			editFrame.setLocationRelativeTo(frame); // Center the frame on the main frame
			
			JPanel namePanel = new JPanel();
			JLabel nameLabel = new JLabel("Name: ");
			nameLabel.setPreferredSize(new Dimension((int)(patientPanelSize.getWidth()*.25),(int)(patientPanelSize.getHeight()*.04)));
			JTextField nameTextField = new JTextField();
			nameTextField.setPreferredSize(new Dimension((int)(patientPanelSize.getWidth()*.5),(int)(patientPanelSize.getHeight()*.04)));
			namePanel.add(nameLabel); 
			namePanel.add(nameTextField); 
			
			JPanel IDPanel = new JPanel();
			JLabel IDLabel = new JLabel("ID: ");
			IDLabel.setPreferredSize(new Dimension((int)(patientPanelSize.getWidth()*.25),(int)(patientPanelSize.getHeight()*.04)));
			JTextField IDTextField = new JTextField();
			IDTextField.setPreferredSize(new Dimension((int)(patientPanelSize.getWidth()*.5),(int)(patientPanelSize.getHeight()*.04)));
			IDPanel.add(IDLabel); 
			IDPanel.add(IDTextField); 
			
			JButton patientAdd = new JButton("Add Patient");
			patientAdd.setPreferredSize(new Dimension((int)(patientPanelSize.getWidth()*0.9), (int)(patientPanelSize.getHeight()*.05)));
			
			patientAdd.addActionListener(
					new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							try
							{
								if (patients.get(Long.parseLong(IDTextField.getText())) == null)
								{
									Patient patient = new Patient(nameTextField.getText(), 
											Long.parseLong(IDTextField.getText()));
									
									
									patients.put(patient.getNationalID(), patient);
									
									tableModel.addRow(new String[]
											{nameTextField.getText(), 
											IDTextField.getText()});
									
									nameTextField.setText("");
									IDTextField.setText("");
									editFrame.setVisible(false);
								}
								else
								{			            		
									JOptionPane.showMessageDialog(editFrame, "You cannot add with same ID!");;
								}
							}
							catch(Exception err)
			            	{
			            		JOptionPane.showMessageDialog(editFrame, "Please sure enter infos correctly!");;
			            	}
							
							
						}
					}
					);
			
			editFrame.add(namePanel);
			editFrame.add(IDPanel);
			editFrame.add(patientAdd);
			
			return editFrame;
		}
				
		private static JFrame addFrameRendezvous2(DefaultTableModel tableModel)
		{
			
			JFrame editFrame = new JFrame("Add Rendevous");
			Dimension size = panelSize;
			
			editFrame.setLayout(new FlowLayout(FlowLayout.LEFT, (int)(size.getHeight()*.03),(int)(size.getWidth()*.03)));
			editFrame.setSize((int)(size.getWidth()*1.3), (int)size.getHeight());
			editFrame.setLocationRelativeTo(frame); // Center the frame on the main frame
			editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			JPanel patientPanel = new JPanel();
			JLabel patientLabel = new JLabel("Patient: ");
			patientLabel.setPreferredSize(new Dimension((int)(size.getWidth()*.25),(int)(size.getHeight()*.04)));
			JComboBox<String> patientComboBox = new JComboBox<String>();
			patientComboBox.setPreferredSize(new Dimension((int)(size.getWidth()*.60),(int)(size.getHeight()*.04)));
			patientPanel.add(patientLabel); 
			patientPanel.add(patientComboBox); 
			
			JPanel hPanel = new JPanel();
			JLabel hLabel = new JLabel("Hospital: ");
			hLabel.setPreferredSize(new Dimension((int)(size.getWidth()*.25),(int)(size.getHeight()*.04)));
			JComboBox<String> hComboBox = new JComboBox<String>();
			hComboBox.setPreferredSize(new Dimension((int)(size.getWidth()*.60),(int)(size.getHeight()*.04)));
			hPanel.add(hLabel); 
			hPanel.add(hComboBox); 
			
			JPanel sPanel = new JPanel();
			JLabel sLabel = new JLabel("Section: ");
			sLabel.setPreferredSize(new Dimension((int)(size.getWidth()*.25),(int)(size.getHeight()*.04)));
			JComboBox<String> sComboBox = new JComboBox<String>();
			sComboBox.setPreferredSize(new Dimension((int)(size.getWidth()*.60),(int)(size.getHeight()*.04)));
			sPanel.add(sLabel); 
			sPanel.add(sComboBox);
			
			JPanel dPanel = new JPanel();
			JLabel dLabel = new JLabel("Doctor: ");
			dLabel.setPreferredSize(new Dimension((int)(size.getWidth()*.25),(int)(size.getHeight()*.04)));
			JComboBox<String> dComboBox = new JComboBox<String>();
			dComboBox.setPreferredSize(new Dimension((int)(size.getWidth()*.60),(int)(size.getHeight()*.04)));
			dPanel.add(dLabel); 
			dPanel.add(dComboBox);
			
			hComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					LinkedList<Section> sections = new LinkedList<>();
					String hospitalName = (String)hComboBox.getSelectedItem();
					if(hospitalName != null)
					{
						Hospital selectedHospital = CRS.getHospital(hospitalName);
						if(selectedHospital != null)
							sections =  selectedHospital.getSections();
					}
					
					sComboBox.removeAllItems();
					for(Section s : sections)
					{
						sComboBox.addItem(s.getName());
					}
				}
				
			});
			
			sComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					LinkedList<Doctor> doctors = new LinkedList<>();
					
					String hospitalName = (String)hComboBox.getSelectedItem();
					if(hospitalName != null)
					{
						Hospital selectedHospital = CRS.getHospital(hospitalName);
						if(selectedHospital != null)
						{
							String sectionName = (String)sComboBox.getSelectedItem();
							if(sectionName != null)
							{
								Section selectedSection = selectedHospital.getSectionn(sectionName);
								if(selectedSection != null)
								{
									doctors = selectedSection.getDoctors();
									dComboBox.removeAllItems();
									for(Doctor d : doctors)
									{
										dComboBox.addItem(d.getName());
									}
								}
							}
						}
					}
				}
				
			});
			
			JPanel datePanel = new JPanel();
			JLabel dayLabel = new JLabel("Day: ");			
			JTextField dayTextField = new JTextField();
			dayTextField.setPreferredSize(new Dimension(50,20));
			JLabel monthLabel = new JLabel("Month: ");			
			JTextField monthTextField = new JTextField();
			monthTextField.setPreferredSize(new Dimension(50,20));
			JLabel yearLabel = new JLabel("Year: ");			
			JTextField yearTextField = new JTextField();
			yearTextField.setPreferredSize(new Dimension(50,20));
			
			datePanel.add(dayLabel); 
			datePanel.add(dayTextField);
			datePanel.add(monthLabel); 
			datePanel.add(monthTextField);
			datePanel.add(yearLabel); 
			datePanel.add(yearTextField);
			
			
			
			JButton patientAdd = new JButton("Add Rendezvous");
			patientAdd.setPreferredSize(new Dimension((int)(size.getWidth()*0.9), (int)(size.getHeight()*.05)));
			
			patientAdd.addActionListener(
					new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							try
							{
								Calendar calendar = Calendar.getInstance();
								calendar.set(Integer.parseInt(yearTextField.getText()), Integer.parseInt(monthTextField.getText())-1, Integer.parseInt(dayTextField.getText()));
								
								Long patientID = getPatientID((String)patientComboBox.getSelectedItem());
								
								Integer hospitalID = getHospital((String)hComboBox.getSelectedItem()).getID();
								
								Integer sectionID = getHospital((String)hComboBox.getSelectedItem()).getSectionn((String)sComboBox.getSelectedItem()).getID();
								
								Integer diplomaID = getHospital((String)hComboBox.getSelectedItem()).getSectionn((String)sComboBox.getSelectedItem()).getDoctor((String)dComboBox.getSelectedItem()).getDiplomaID();

								makeRendezvous(patientID, hospitalID, sectionID, diplomaID, calendar.getTime());
								
								String[] info = rendezvous.getLast().getInfo();
								info[1] = String.valueOf(hospitalID);
								info[2] = String.valueOf(sectionID);
								
								tableModel.addRow(info);
								
								dayTextField.setText("");
								monthTextField.setText("");
								yearTextField.setText("");
								
								editFrame.setVisible(false);
							}
							catch(Exception err)
							{
								if(err.getMessage().equals("max"))
								{
									JOptionPane.showMessageDialog(editFrame, "You cannot add Rendezvous more than 7 to the Doctor!");;
								}
								else
								{
									JOptionPane.showMessageDialog(editFrame, "Please sure enter infos correctly!");;

								}
							}
						}
					});
			
			editFrame.add(patientPanel);
			editFrame.add(hPanel);
			editFrame.add(sPanel);
			editFrame.add(dPanel);
			editFrame.add(datePanel);
			editFrame.add(patientAdd);
			
			editFrame.addWindowListener(new WindowAdapter() 
			{
	            @Override
	            public void windowActivated(WindowEvent e) 
	            {
	            	patientComboBox.removeAllItems();
	            	for(Patient p : patients.values()) 
	            	{
	            		patientComboBox.addItem(p.getName());
	            	}
	            	hComboBox.removeAllItems();
	            	for(Hospital h : hospitals.values()) 
	            	{
	            		hComboBox.addItem(h.getName());
	            	}
	            }
	        });
			
			return editFrame;
			
		}
	}
}
