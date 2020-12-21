package common.shared_data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

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
	private List<UserLite> joinedPersons;
	private List<UserLite> authorizedPersons;
	private Map<String, String> nickNames;
	private List<Kick> kicked;
	private List<Message> messages;

	/**
	 * Inside IHM-Main interface, we need Property to bind the property name and description.
	 * And not only bind the instance of channel
	 * Mark as transient so it will be exclude from serialization
	 */
	private transient StringProperty nameProperty;
	private transient StringProperty descriptionProperty;
	private transient ObjectProperty<Visibility> visibilityProperty;

	public Channel(String name, UserLite creator, String description, Visibility visibility, ChannelType type) {
		this.id = UUID.randomUUID();
		setName(name);
		this.creator = creator;
		setDescription(description);
		setVisibility(visibility);
		this.type = type;
		this.administrators = new ArrayList<>();
		this.administrators.add(creator);
		this.joinedPersons = new ArrayList<>();
		this.authorizedPersons = new ArrayList<>();
		this.joinedPersons.add(creator);
		this.authorizedPersons.add(creator);
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

	private final StringProperty getNameProperty() {
		if (nameProperty == null) {
			nameProperty = new SimpleStringProperty();
			nameProperty.set(name);
		}
		return nameProperty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		getNameProperty().set(name);
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
			this.joinedPersons.add(creator);
			this.nickNames.put(creator.getId().toString(), creator.getNickName());
		}
	}

	private final StringProperty getDescriptionProperty() {
		if (descriptionProperty == null) {
			descriptionProperty = new SimpleStringProperty();
			descriptionProperty.set(description);
		}
		return descriptionProperty;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		getDescriptionProperty().set(description);
	}

	private final ObjectProperty<Visibility> getVisibilityProperty() {
		if (visibilityProperty == null) {
			visibilityProperty = new SimpleObjectProperty<>();
			visibilityProperty.set(visibility);
		}
		return visibilityProperty;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
		getVisibilityProperty().set(visibility);
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

	public List<UserLite> getJoinedPersons() {
		return joinedPersons;
	}

	public void setJoinedPersons(List<UserLite> joinedPersons) {
		this.joinedPersons = joinedPersons;
	}

	public List<UserLite> getAuthorizedPersons() {
		return authorizedPersons;
	}

	public void setAuthorizedPersons(List<UserLite> authorizedPersons) {
		this.authorizedPersons = authorizedPersons;
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
		addJoinedUser(user);
	}

	public void addJoinedUser(UserLite user) {
		if (!userJoinedChannel(user.getId())){
			this.joinedPersons.add(user);
		}
	}

	public void removeAdmin(UUID adminID){
		if (!creator.getId().equals(adminID)){
			administrators.removeIf(user -> user.getId().equals(adminID));
		}
	}

	public UserLite getAdmin(UUID adminID){
		for(UserLite user : administrators){
			if(user.getId().equals(adminID))
				return user;
		}
		return null;
	}

	public boolean messageInChannel(UUID messageID){
		for(Message message : messages){
			if(message.getId().equals(messageID))
				return true;
		}
		return false;
	}

	public void addAuthorizedUser(UserLite user) {
		if (!userIsAuthorized(user.getId())) {
			this.authorizedPersons.add(user);
		}
	}

	public boolean userJoinedChannel(UUID userID){
		for (UserLite user : joinedPersons) {
			if(user.getId().equals(userID))
				return true;
		}
		return false;
	}

	public boolean userIsAuthorized(UUID userID){
		for (UserLite user : authorizedPersons) {
			if(user.getId().equals(userID))
				return true;
		}
		return false;
	}

	public boolean userInChannel(UUID userID){
		for (UserLite user : joinedPersons) {
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
		this.joinedPersons.removeIf(person ->(person.getId().equals(idUser)));
	}

	public void removeAllUser(){
		this.joinedPersons.clear();
	}

	public void removeUserAuthorization(UUID idUser){
		this.authorizedPersons.removeIf(person ->(person.getId().equals(idUser)));
	}

	public void kickPermanentUser(UserLite user, String reason) {
		this.kicked.add(new Kick(user, this.id, reason, true));
	}

	public void kickUser(UserLite user, String reason, Date end) {
		this.kicked.add(new Kick(user, this.id, reason, end));
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

	@Override
	public String toString() {
		return name;
	}

	public static Callback<Channel, javafx.beans.Observable[]> extractor() {
		return (Channel c) -> new javafx.beans.Observable[]{c.getNameProperty(), c.getDescriptionProperty(), c.getVisibilityProperty()};
	}
}
