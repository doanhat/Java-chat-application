package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;
import common.sharedData.Channel;

/**
 * Message indiquant au client qu'il s'est bien connecte au channel, et lui fournit l'objet channel complet
 */
public class AcceptJoinChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -5922382684107438936L;
	private final UserLite user;
    private final Channel channel;

    public AcceptJoinChannelMessage(UserLite receiver, Channel channel) {
        this.user = receiver;
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyAcceptedToJoinChannel(user, channel.getId());
    }
}
