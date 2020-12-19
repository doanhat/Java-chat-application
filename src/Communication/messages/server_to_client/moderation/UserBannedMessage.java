package Communication.messages.server_to_client.moderation;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Channel;
import common.shared_data.UserLite;

/**
 * Classe de message allant du serveur au client servant à notifier le bannissement d'un client par le serveur
 * @author Martin Passard
 *
 */
public class UserBannedMessage extends ServerToClientMessage {

	private static final long serialVersionUID = 3615983488978768567L;

	private final UserLite user;
	private final Channel channel;
	private final int duration;
	private final String expl;
    
	public UserBannedMessage(UserLite user, Channel channel, int duration, String expl) {
		super();
		this.user = user;
		this.channel = channel;
		this.duration = duration;
		this.expl = expl;
	}


	@Override
	protected void handle(CommunicationClientController commClientController) {
		UserLite userRunning = commClientController.getCommunicationClientInterface().getLocalUser();
		
		if(user.getId().equals(userRunning.getId())) {
			//TODO implementation modules cas où on est l'utilisateur banni
			/*
			 * commClientController.dataClientHandler().removeChannelFromList(Channel channel, duration, expl);
			 */
		}else {
			// commClientController.channelHandler().notifyUserBanned(userLite user, UUID channel, int duration, String expl);
			
			
		}
	}

}
