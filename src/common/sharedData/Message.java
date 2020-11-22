package common.sharedData;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Message implements Serializable {
	public Message() {
	}

	private UUID id;
	@JsonFormat
		(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

	private Date date;
	private String message;
	private boolean edited;
	private UserLite author;
	private List<UserLite> likes;
	private boolean deletedByUser;
	private boolean deletedByAdmin;
	private UUID parentMessageId;
	private List<Message> answers;

	public Message(String message, UserLite author) {
		this.id = UUID.randomUUID();
		this.answers = new ArrayList<Message>();
		this.date = new Date();
		this.likes = new ArrayList<UserLite>();
		this.message = message;
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
			return "(Supprmé par l'utilisateur)";
		}

		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public void delete(boolean deletedByUser) {
		this.deletedByUser = deletedByUser;
		this.deletedByAdmin = !deletedByUser;
	}

	public boolean isDeletedByAdmin() {
		return deletedByAdmin;
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
		this.answers.add(answer);
		answer.setParentMessageId(this.getId());

	}

	public int countLikes() {
		return this.likes.size();
	}

	public void like(UserLite user) {
		if(!this.likes.contains(user)) {
			this.likes.add(user);
		}
	}

}
