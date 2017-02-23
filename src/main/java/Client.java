public class Client {
	private long id;
	private String lastName;
	private String firstName;
	private Gender gender;
	private long prefere;
	
	public Client()
	{
		id = 0;
		lastName ="";
		firstName="";
		gender =Gender.M;
		prefere = 0;
	}
	
	public Client(long id, String lastName,String firstName,Gender genre)
	{
		this();
		this.id = id;
		this.lastName =lastName;
		this.firstName=firstName;
		this.gender =genre;
	}
	
	public Client(long id, String lastName,String firstName,Gender genre,Book prefere)
	{
		this(id,lastName,firstName,genre);
		this.prefere = prefere.getId();
	}
	

	
	public Client(String lastName,String firstName,Gender genre)
	{
		this();
		this.lastName =lastName;
		this.firstName=firstName;
		this.gender =genre;
	}
		
	public String toString(){
		return this.lastName+" "+this.firstName+"["+this.gender.getName()+"]";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public long getPrefere() {
		return prefere;
	}

	public void setPrefere(long prefere) {
		this.prefere = prefere;
	}
}
