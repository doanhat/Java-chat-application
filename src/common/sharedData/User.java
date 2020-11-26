package common.sharedData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties({"userLite"})
public class User extends UserLite {

	private String password;
	private String lastName;
	private String firstName;

	@JsonFormat
		(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

	private Date birthDate;

	// Ce constructor accepte un birthDay du format java.util.Date
	public User(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
		super(nickName, avatar);
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}

	// Ce constructor accepte un birthDay du format String "dd-MM-yyyy", ex "04-05-1998"
	public User(String nickName, String avatar, String password, String lastName, String firstName, String birthDate) throws ParseException {
		super(nickName, avatar);
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		this.birthDate = df.parse(birthDate);
	}

	public User() {
		super();
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

	public UserLite getUserLite(){
		return new UserLite(this.getId(),this.getNickName(),this.getAvatar());
	}

}
