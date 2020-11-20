package common.sharedData;

import java.io.Serializable;
import java.util.UUID;

public class UserLite implements Serializable {

	private UUID id;
	private String nickName;
	private String avatar;

	public UserLite(UUID id, String nickName, String avatar) {
		this.id = id;
		this.nickName = nickName;
		this.avatar = avatar;
	}

	public UserLite() {
	}

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
}
