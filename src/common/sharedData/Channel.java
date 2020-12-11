package common.sharedData;

import java.io.Serializable;
import java.util.*;

public class Channel implements Serializable {
	public Channel() {
	}

	private UUID id;
	private String name;
	private UserLite creator;
	private String description;
	private Visibility visibility;
	private ChannelType type;
	private List<UserLite> administrators;
	private List<UserLite> acceptedPersons;
	private List<UserLite> invitedPersons;
	private Map<String, String> nickNames;
	private List<Kick> kicked;
	private List<Message> messages;

	public Channel(String name, UserLite creator, String description, Visibility visibility, ChannelType type) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.creator = creator;
		this.description = description;
		this.visibility = visibility;
		this.type = type;
		this.administrators = new ArrayList<>();
		this.administrators.add(creator);
		this.acceptedPersons = new ArrayList<>();
		this.acceptedPersons = new ArrayList<>();
		this.acceptedPersons.add(creator);
		this.nickNames = new HashMap<>();
		this.nickNames.put(creator.getId().toString(), creator.getNickName());
		this.kicked = new ArrayList<>();
		this.messages = new ArrayList<>();
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

	public void updateCreator(UserLite creator) {
		if (getCreator() == null || !getCreator().getId().equals(creator.getId())){
			this.creator = creator;
			this.administrators.add(creator);
			this.acceptedPersons.add(creator);
			this.nickNames.put(creator.getId().toString(), creator.getNickName());
		}
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

	public ChannelType getType() {
		return type;
	}

	public void setType(ChannelType type) {
		this.type = type;
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

	public List<UserLite> getInvitedPersons() {
		return invitedPersons;
	}

	public void setInvitedPersons(List<UserLite> invitedPersons) {
		this.invitedPersons = invitedPersons;
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
		for (Message msg : messages) {
			if(msg.getId().equals(m.getId())){
				messages.remove(m);
				break;
			}
		}
		messages.add(m);
	}

	public void addAdmin(UserLite user) {
		if (!userIsAdmin(user.getId())){
			this.administrators.add(user);
		}
		addUser(user);
	}

	public void addUser(UserLite user) {
		if (!userInChannel(user.getId())){
			this.acceptedPersons.add(user);
			if(userInInvitedList(user.getId())) {
				removeUserFromInvited(user.getId());
			}
		}
	}

	public void addInvitedUser(UserLite user) {
		if (!userInChannel(user.getId()) && !userInInvitedList(user.getId())) {
			this.invitedPersons.add(user);
		}
	}

	public boolean userInChannel(UUID userID){
		for (UserLite user : acceptedPersons) {
			if(user.getId().equals(userID))
				return true;
		}
		return false;
	}

	public boolean userInInvitedList(UUID userID){
		for (UserLite user : invitedPersons) {
			if(user.getId().equals(userID))
				return true;
		}
		return false;
	}

	public boolean userIsAdmin(UUID userID){
		for (UserLite user : administrators) {
			if(user.getId().equals(userID))
				return true;
		}
		return false;
	}

	public void removeUser(UUID idUser){
		this.acceptedPersons.removeIf(person ->(person.getId().equals(idUser)));
	}

	public void removeUserFromInvited(UUID idUser){
		this.invitedPersons.removeIf(person ->(person.getId().equals(idUser)));
	}

	public void kickPermanentUser(UserLite user, String reason) {
		this.kicked.add(new Kick(user, this, reason, true));
	}

	public void kickUser(UserLite user, String reason, Date end) {
		this.kicked.add(new Kick(user, this, reason, end));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Channel channel = (Channel) o;
		return Objects.equals(id, channel.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
