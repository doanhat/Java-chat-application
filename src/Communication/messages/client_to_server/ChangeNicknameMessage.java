package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public class ChangeNicknameMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 1142553945824515068L;
	private final UserLite user;
	private final Channel channel;
	private final String newNickname;
	

	/**
	 * Message de demande de changment de Nickname
	 * @param user utilisateur demandant le changement
	 * @param channel Canal pour lequel l'utilisateur demande de changer le nickname
	 * @param newNickname nouveau nickname demandé
	 */
	public ChangeNicknameMessage(UserLite user, Channel channel, String newNickname) {
		super();
		this.user = user;
		this.channel = channel;
		this.newNickname = newNickname;
	}

	@Override
	protected void handle(CommunicationServerController commServerController) {
		commServerController.requestNicknameChange(user, channel, newNickname);
	}

}
