package pack;

public class Doctor extends Person
{
	
	private static final long serialVersionUID = 1L;

	private final int diplomaID;

	private Schedule schedule; 
	
	private int maxPatientPerDay = 7; // i set 7 as a default 
	

	public Doctor(String name, long ID, int diplomaID) 
	{
		super(name, ID);
		this.diplomaID = diplomaID;
		schedule = new Schedule(maxPatientPerDay);
	}
	
	public Doctor(String name, long ID, int diplomaID, int maxPatientPerDay) 
	{
		super(name, ID);
		this.diplomaID = diplomaID;
		this.maxPatientPerDay = maxPatientPerDay;
		schedule = new Schedule(maxPatientPerDay);
		
	}
	
	public Schedule getSchedule() {return schedule;}
	
	public int getDiplomaID() {return diplomaID;}
	
	public String toString()
	{ 
		return super.toString() + " - Diploma ID: " + diplomaID + " - Schedule: " + schedule;
	}
	
	public String[] getInfo()
	{
		String[] result = new String[2];
		
		result[0] = super.getName();
		result[1] = String.valueOf(super.getNationalID());
		result[1] = String.valueOf(diplomaID);
		
		return result;
	}
	
}
