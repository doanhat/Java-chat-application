package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;
import common.sharedData.Channel;

import java.util.List;

/**
 * Cette classe indique au client qu'il a été correctement autorisé à rejoindre le canal désiré
 * Message indiquant au client qu'il s'est bien connecte au channel, et lui fournit l'objet channel complet
 */
public class JoinChannelResponseMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -65167438936L;
    private final Channel channel;
    private final List<UserLite> activeUsers;
    private final boolean accepted;

    public JoinChannelResponseMessage(Channel channel, List<UserLite> activeUsers, boolean accepted) {
        this.channel = channel;
        this.accepted = accepted;
        this.activeUsers = activeUsers;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyJoinChannelResponse(channel, activeUsers, accepted);
    }
}
