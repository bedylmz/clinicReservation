package pack;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;

public class Schedule implements Serializable
{
	private static final long serialVersionUID = 1L;

	private LinkedList<Rendezvous> sessions;
	
	private int maxPatientPerDay;
	
	//this attribute show in UML diagram
	@SuppressWarnings("unused")
	private Doctor doctor;
	
	public Schedule(int maxPatientPerDay)
	{
		this.maxPatientPerDay = maxPatientPerDay;
		sessions = new LinkedList<Rendezvous>();
	}
	
	public void addRendezvous(Rendezvous rendezvous) throws Exception
	{
		int currentPatientPerDay = 1; // i set 1 because i also count desired date in it.
		for(Rendezvous ren : sessions)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(rendezvous.getDateTime());
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(ren.getDateTime());
			
			if (cal.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&&cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
			{
				currentPatientPerDay++;
			}
		}
		
		if(currentPatientPerDay <= maxPatientPerDay) {sessions.add(rendezvous);}
		else {throw new Exception("max");}
	}
	
	public boolean addRendezvousBoolean(Rendezvous rendezvous)
	{
		int currentPatientPerDay = 1; // i set 1 because i also count desired date in it.
		for(Rendezvous ren : sessions)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(rendezvous.getDateTime());
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(ren.getDateTime());
			
			if (cal.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&&cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
			{
				currentPatientPerDay++;
			}
		}
		
		if(currentPatientPerDay <= maxPatientPerDay) {sessions.add(rendezvous); return true;}
		return false;
	}
	
	public Rendezvous getRendezvous(Rendezvous rendezvous)
	{
		for(Rendezvous ren : sessions)
		{
			if(ren.equals(rendezvous))
			{
				return ren;
			}
		}
		return null;
	}
	
	public String toString()
	{
		String result = "";
		Calendar cal = Calendar.getInstance();
		
		for(Rendezvous ren : sessions)
		{
			cal.setTime(ren.getDateTime());
			result += cal.get(Calendar.DATE)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR) + " | ";
		}
		
		return result + " - maxPatientPerDay: " + maxPatientPerDay ;
	}
	
}
