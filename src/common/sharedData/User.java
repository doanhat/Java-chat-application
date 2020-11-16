package common.sharedData;

import java.util.Date;

public class User extends UserLite {
	
	private String password;
	private String lastName;
	private String firstName;
	private Date birthDate;

	public User(String password, String lastName, String firstName, Date birthDate) {
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	

}
