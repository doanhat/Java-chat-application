package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
// FIXME
//import Communication.messages.client_to_server.AskToJoinMessage;
import java.util.logging.*;
import java.util.UUID;
import common.sharedData.UserLite;
import common.sharedData.Channel;

/**
 * Cette classe indique au client qu'il a été correctement autorisé à rejoindre le canal désiré, en réponse à un {@link //AskToJoinMessage}
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
        logger.log(Level.INFO, "Accepted to join channel {}" , channel);
        commClientController.notifyAcceptedToJoinChannel(user, channel);
    }
}