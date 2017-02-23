
public enum Gender {
	M("M"),
	F("F");
	
	private String name ="";
	
	Gender(String s)
	{
		this.name = s;
	}
	
	public String getName()
	{
		return this.name;
	}
	
}
