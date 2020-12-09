package Communication.messages.client_to_server.channel_modification.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_modification.shared_channels.TellOwnerToUpdateNicknameMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class ChangeNicknamePropChannelMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 1148553945824515068L;
	private final UserLite user;
	private final UUID channel;
	private final String newNickname;
	private final UUID owner;

	public ChangeNicknamePropChannelMessage(UserLite user, UUID channel, String newNickname, UserLite owner) {
		super();
		this.user = user;
		this.channel = channel;
		this.newNickname = newNickname;
		this.owner = owner.getId();
	}

	@Override
	protected void handle(CommunicationServerController commServerController) {
		commServerController.sendMessage(owner, new TellOwnerToUpdateNicknameMessage(user, channel, newNickname));
	}

}
