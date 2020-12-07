package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
// FIXME
//import Communication.messages.client_to_server.AskToJoinMessage;
import java.util.logging.*;
import common.sharedData.UserLite;
import common.sharedData.Channel;

/**
 * Cette classe indique au client qu'il a été correctement autorisé à rejoindre le canal désiré, en réponse à un {@link //AskToJoinMessage}
 * Message indiquant au client qu'il s'est bien connecte au channel, et lui fournit l'objet channel complet
 */
public class JoinChannelResponseMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -5922382684107438936L;
	private final UserLite user;
    private final Channel channel;
    private final boolean accepted;

    public JoinChannelResponseMessage(UserLite receiver, Channel channel, boolean accepted) {
        this.user = receiver;
        this.channel = channel;
        this.accepted = accepted;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyJoinChannelResponse(user, channel, accepted);
    }
}
