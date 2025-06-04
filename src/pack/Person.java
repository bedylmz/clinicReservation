package pack;

import java.io.Serializable;

public class Person implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private final long nationalID;
	
	public Person(String name, long ID)
	{
		this.name = name;
		nationalID = ID;
	}
	
	public String toString()
	{
		return "Name: " + name + " - National ID: "+ (nationalID);
	}
	
	public String 	getName() 				{return name;}
	public void 	setName(String name) 	{this.name = name;}
	public long 	getNationalID() 		{return nationalID;}

}
