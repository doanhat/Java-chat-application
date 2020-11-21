package common.sharedData;

import java.io.Serializable;
import java.util.*;

public abstract class Channel implements Serializable {
	public Channel() {
	}

	private UUID id;
	private String name;
	private UserLite creator;
	private String description;
	private Visibility visibility;
	private List<UserLite> administrators;
	private List<UserLite> acceptedPersons;
	private Map<String, String> nickNames;
	private List<Kick> kicked;
	private List<Message> messages;

	public Channel(UUID id, String name, UserLite creator, String description, Visibility visibility) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.description = description;
		this.visibility = visibility;
		this.administrators = new ArrayList<UserLite>();
		this.administrators.add(creator);
		this.acceptedPersons = new ArrayList<UserLite>();
		this.acceptedPersons.add(creator);
		this.nickNames = new HashMap<String, String>();
		this.nickNames.put(creator.getId().toString(), creator.getNickName());
		this.kicked = new ArrayList<Kick>();
		this.messages = new ArrayList<Message>();
	}

	public Channel(String name, UserLite creator, String description, Visibility visibility) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.creator = creator;
		this.description = description;
		this.visibility = visibility;
		this.administrators = new ArrayList<UserLite>();
		this.administrators.add(creator);
		this.acceptedPersons = new ArrayList<UserLite>();
		this.acceptedPersons.add(creator);
		this.nickNames = new HashMap<String, String>();
		this.nickNames.put(creator.getId().toString(), creator.getNickName());
		this.kicked = new ArrayList<Kick>();
		this.messages = new ArrayList<Message>();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public Map<String, String> getNickNames() {
		return nickNames;
	}

	public void setNickNames(Map<String, String> nickNames) {
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

	public void setMessages(List<Message> messages) {
		this.messages = messages;
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
