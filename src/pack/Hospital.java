package pack;

import java.io.Serializable;
import java.util.LinkedList;

public class Hospital implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final int ID;
	private String name;
	private LinkedList<Section> sections;
	
	public Hospital(int ID, String name)
	{
		this.ID = ID;
		this.name = name;
		sections = new LinkedList<Section>();
	}
	
	public int getID() {return ID;}

	public String getName() {return name;}
	
	public LinkedList<Section> getSections() {return sections;}
	
	public void setSections(LinkedList<Section> sections) {this.sections = sections;}

	public Section getSection(int ID)
	{
		for(Section section : sections)
		{
			if(section.getID() == ID) return section;
		}
		return null;
	}
	
	// interestingly this function is specially marked as PRIVATE in the UML diagram, 
	//so I implement this function specially
	@SuppressWarnings("unused")
	private Section getSection(String name)
	{
		for(Section section : sections)
		{
			if(section.getName() == name) return section;
		}
		return null;
	}
	
	public Section getSectionn(String name)
	{
		for(Section section : sections)
		{
			if(section.getName() == name) return section;
		}
		return null;
	}
	
	public void addSection(Section addSection) throws DuplicateInfoException
	{
		for(Section section : sections)
		{
			if(section.getID() == addSection.getID()) 
				throw new DuplicateInfoException ("Attempt to add same section");
		}
		sections.add(addSection);
	}
	
	public String toString()
	{
		String result = "";
		
		for(Section section : sections)
		{
			result += "\n" + section;
		}
		
		return "Hospital Name: " + name +" - ID: " + ID + result ;
	}
	
	public String[] getInfo()
	{
		String[] result = new String[2];
		
		result[0] = name;
		result[1] = String.valueOf(ID);
		
		return result;
	}

}
