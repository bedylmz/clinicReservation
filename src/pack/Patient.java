package pack;

public class Patient extends Person 
{
	private static final long serialVersionUID = 1L;

	public Patient(String name, long ID) 
	{
		super(name, ID);
	}
	
	public String[] getInfo()
	{
		String[] result = new String[2];
		
		result[0] = super.getName();
		result[1] = String.valueOf(super.getNationalID());
		
		return result;
	}
}
