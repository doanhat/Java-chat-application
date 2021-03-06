package common.shared_data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {
	public Message() {
	}

	private UUID id;
	@JsonFormat
		(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy@HH:mm:ss")
	private Date date;
	private String content;
	private boolean edited;
	private UserLite author;
	private List<UserLite> likes;
	private boolean deletedByUser;
	private boolean deletedByAdmin;
	private UUID parentMessageId;
	private List<Message> answers;

	public Message(String message, UserLite author) {
		this.id = UUID.randomUUID();
		this.answers = new ArrayList<>();
		this.date = new Date();
		this.likes = new ArrayList<>();
		this.content = message;
		this.author = author;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		if(deletedByAdmin) {
			return "(Supprimé par un administrateur)";
		}

		if(deletedByUser) {
			return "(Supprimé par l'utilisateur)";
		}

		return content;
	}

	public void setMessage(String message) {
		this.content = message;
		this.edited = true;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public UserLite getAuthor() {
		return author;
	}

	public void setAuthor(UserLite author) {
		this.author = author;
	}

	public List<UserLite> getLikes() {
		return likes;
	}

	public void setLikes(List<UserLite> likes) {
		this.likes = likes;
	}

	public boolean isDeletedByUser() {
		return deletedByUser;
	}

	public void addLike(UserLite userLite){
		if (!likedByUser(userLite.getId())){
			likes.add(userLite);
		}else{
			likes.removeIf(user -> user.getId().equals(userLite.getId()));
		}
	}

	public boolean likedByUser(UUID userID){
		for (UserLite user : likes) {
			if(user.getId().equals(userID))
				return true;
		}
		return false;
	}

	public void delete(boolean deletedByUser) {
		this.deletedByUser = deletedByUser;
		this.deletedByAdmin = !deletedByUser;
	}

	public boolean isDeletedByAdmin() {
		return deletedByAdmin;
	}

	public void setDeletedByUser(boolean deletedByUser) {
		this.deletedByUser = deletedByUser;
	}

	public void setDeletedByAdmin(boolean deletedByAdmin) {
		this.deletedByAdmin = deletedByAdmin;
	}

	public UUID getParentMessageId() {
		return parentMessageId;
	}

	public void setParentMessageId(UUID parentMessageId) {
		this.parentMessageId = parentMessageId;
	}

	public List<Message> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Message> answers) {
		this.answers = answers;
	}

	public void addAnswers(Message answer) {
		for (Message ans: answers){
			if (ans.getId().equals(answer.getId())){
				answers.remove(ans);
			}
		}
		this.answers.add(answer);
		answer.setParentMessageId(this.id);
	}

	public int countLikes() {
		return this.likes.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message m = (Message) o;
		return Objects.equals(id, m.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
