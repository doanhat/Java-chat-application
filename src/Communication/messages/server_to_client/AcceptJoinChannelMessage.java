package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.AskToJoinMessage;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Cette classe indique au client qu'il a été correctement autorisé à rejoindre le canal désiré, en réponse à un {@link AskToJoinMessage}
 */
public class AcceptJoinChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -5922382684107438936L;
	private final UserLite user;
    private final UUID channelID;

    public AcceptJoinChannelMessage(UserLite receiver, UUID channelID) {
        this.user = receiver;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        System.err.println("Accepted to join channel " + channelID);
        commClientController.notifyAcceptedToJoinChannel(user, channelID);
    }
}
