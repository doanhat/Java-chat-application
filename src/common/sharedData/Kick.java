package common.sharedData;

import java.util.Date;

public class Kick {
	private UserLite user;
	private Channel channel;
	private String reason;
	private boolean permanentKick;
	private Date endKick;
	
	public Kick(UserLite user, Channel channel, String reason, boolean permanent) {
		this.user = user;
		this.channel = channel;
		this.reason = reason;
		this.permanentKick = permanent;
	}
	
	public Kick(UserLite user, Channel channel, String reason, Date end) {
		this.user = user;
		this.channel = channel;
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
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
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
		return permanentKick || (endKick != null && new Date().before(endKick));
	}
}
