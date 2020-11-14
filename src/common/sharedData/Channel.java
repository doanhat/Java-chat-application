package common.sharedData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Channel implements Serializable {

	private int id;
	private String name;
	private UserLite creator;
	private String description;
	private Visibility visibility;
	private List<UserLite> administrators;
	private List<UserLite> acceptedPersons;
	private Map<UserLite, String> nickNames;
	private List<Kick> kicked;
	//private List<Message> messages;

	//MODIF : Liste observable pour JavaFX :
	private ObservableList<Message> messages;
	
	public Channel(int id, String name, UserLite creator, String description, Visibility visibility) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.description = description;
		this.visibility = visibility;
		this.administrators = new ArrayList<UserLite>();
		this.administrators.add(creator);
		this.acceptedPersons = new ArrayList<UserLite>();
		this.acceptedPersons.add(creator);
		this.nickNames = new HashMap<UserLite, String>();
		this.nickNames.put(creator, creator.getNickName());
		this.kicked = new ArrayList<Kick>();
		//this.messages = new ArrayList<Message>();
		// JavaFX observable list :
		this.messages = FXCollections.observableArrayList(new ArrayList<Message>());
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public UserLite getCreator() {
		return creator;
	}
	
	public void setCreator(UserLite creator) {
		this.creator = creator;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
	
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	
	public List<UserLite> getAdministrators() {
		return administrators;
	}
	
	public void setAdministrators(List<UserLite> administrators) {
		this.administrators = administrators;
	}
	
	public List<UserLite> getAcceptedPersons() {
		return acceptedPersons;
	}
	
	public void setAcceptedPersons(List<UserLite> acceptedPersons) {
		this.acceptedPersons = acceptedPersons;
	}
	
	public Map<UserLite, String> getNickNames() {
		return nickNames;
	}
	
	public void setNickNames(Map<UserLite, String> nickNames) {
		this.nickNames = nickNames;
	}
	
	public List<Kick> getKicked() {
		return kicked;
	}
	
	public void setKicked(List<Kick> kicked) {
		this.kicked = kicked;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public void setMessages(ObservableList<Message> messages) {
		this.messages = messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = FXCollections.observableArrayList(messages);
	}
	
	public void addMessage(Message m) {
		this.messages.add(m);
	}
	
	public void addAdmin(UserLite user) {
		this.administrators.add(user);
	}
	
	public void addUser(UserLite user) {
		this.acceptedPersons.add(user);
	}
	
	public void kickPermanentUser(UserLite user, String reason) {
		this.kicked.add(new Kick(user, this, reason, true));
	}

	public void kickUser(UserLite user, String reason, Date end) {
		this.kicked.add(new Kick(user, this, reason, end));
	}
	
}
