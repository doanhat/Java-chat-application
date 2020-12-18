package common.shared_data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class Kick {
	private UserLite user;
	private UUID channelId;
	private String reason;
	private boolean permanentKick;
	@JsonFormat
		(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date endKick;
	
	public Kick(UserLite user, UUID channelId, String reason, boolean permanent) {
		this.user = user;
		this.channelId = channelId;
		this.reason = reason;
		this.permanentKick = permanent;
	}
	
	public Kick(UserLite user, UUID channelId, String reason, Date end) {
		this.user = user;
		this.channelId = channelId;
		this.reason = reason;
		this.permanentKick = false;
		this.endKick = end;
	}
	
	public UserLite getUser() {
		return user;
	}
	
	public void setUser(UserLite user) {
		this.user = user;
	}
	
	public UUID getChannelId() {
		return channelId;
	}
	
	public void setChannelId(UUID channelId) {
		this.channelId = channelId;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public boolean isPermanentKick() {
		return permanentKick;
	}
	
	public void setPermanentKick(boolean permanentKick) {
		this.permanentKick = permanentKick;
	}
	
	public Date getEndKick() {
		return endKick;
	}
	
	public void setEndKick(Date endKick) {
		this.endKick = endKick;
	}
	
	public boolean isStillKicked() {
		return (permanentKick || (endKick != null && (new Date().before(endKick))));
	}
}
