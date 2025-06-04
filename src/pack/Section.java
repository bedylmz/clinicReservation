package pack;

import java.io.Serializable;
import java.util.LinkedList;

public class Section implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final int ID;
	private String name;
	private LinkedList<Doctor> doctors;
	
	public Section(int ID, String name)
	{
		this.ID = ID;
		this.name = name;
		doctors = new LinkedList<Doctor>();
	}
	
	public int getID() {return ID;}
	public String getName() {return name;}
	
	public LinkedList<Doctor> getDoctors() {return doctors;}
	public void setDoctors(LinkedList<Doctor> doctors) {this.doctors = doctors;}
	
	public void listDoctors()
	{
		for(Doctor doctor : doctors)
		{
			System.out.println(doctor);			
		}
	}
	
	public String listStringDoctors()
	{
		String result = "";
		for(Doctor doctor : doctors)
		{
			result += doctor;			
		}
		return result;
	}
	
	public Doctor getDoctor(int diplomaID)
	{		
		for(Doctor doctor : doctors)
		{
			if(doctor.getDiplomaID() == diplomaID) {return doctor;}
		}
		
		return null;
	}
	
	public Doctor getDoctor(String name)
	{		
		for(Doctor doctor : doctors)
		{
			if(doctor.getName().equals(name)) {return doctor;}
		}
		
		return null;
	}
	
	public void addDoctor(Doctor addDoctor) throws DuplicateInfoException
	{
		for(Doctor doctor : doctors)
		{
			if(doctor.getDiplomaID() == addDoctor.getDiplomaID()) 
				throw new DuplicateInfoException ("Attempt add same doctor diploma id");
		}
		
		doctors.add(addDoctor);
	}
	
	public String toString()
	{
		String result = "";
		
		for(Doctor doctor : doctors)
		{
			result += "\n" + doctor ;
		}
		
		return "Section Name: " + name +" - ID: " + ID + result ;
	}
	
	public String[] getInfo()
	{
		String[] result = new String[2];
		
		result[0] = name;
		result[1] = String.valueOf(ID);
		
		return result;
	}
	
}
