package common.sharedData;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserLite implements Serializable {

	private UUID id;
	private String nickName;
	private String avatar;

	public UserLite(String nickName, String avatar) {
		this.id = UUID.randomUUID();
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

	@Override
	public String toString() {
		return this.nickName;
	}


}
