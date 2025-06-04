package pack;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Rendezvous implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Date dateTime;
	
	private Patient patient;
	
	private Doctor doctor;
	
	public Rendezvous(Date dateTime, Patient patient, Doctor doctor) throws Exception
	{
		this.setDateTime(dateTime);
		
		this.patient = patient;
		
		this.doctor = doctor;
		
		doctor.getSchedule().addRendezvous(this);
		
	}
	
	public Rendezvous(Doctor doctor, Date dateTime, Patient patient)
	{
		this.setDateTime(dateTime);
		
		this.patient = patient;
		
		this.doctor = doctor;
		
		doctor.getSchedule().addRendezvousBoolean(this);
		
	}

	public Date getDateTime() {return dateTime;}

	public void setDateTime(Date dateTime) {this.dateTime = dateTime;}
	
	public String toString()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		return "Patient " + patient + " Doctor " + doctor  +
				" Date: " + cal.get(Calendar.DATE)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR) ;
	}
	
	public String[] getInfo()
	{
		String[] result = new String[5];
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		
		result[0] = patient.getName();
		result[1] = "";
		result[2] = "";
		result[3] = doctor.getName();
		result[4] = cal.get(Calendar.DATE)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR);//String.valueOf(dateTime);
		
		return result;
	}
	
}
