package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;
import common.shared_data.Channel;

import java.util.List;

/**
 * Cette classe indique au client qu'il a été correctement autorisé à rejoindre le canal désiré
 * Message indiquant au client qu'il s'est bien connecte au channel, et lui fournit l'objet channel complet
 */
public class JoinChannelResponseMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -65167438936L;
	private final UserLite user;
    private final Channel channel;
    private final List<UserLite> activeUsers;
    private final boolean accepted;

    public JoinChannelResponseMessage(UserLite receiver, Channel channel, List<UserLite> activeUsers, boolean accepted) {
        this.user = receiver;
        this.channel = channel;
        this.accepted = accepted;
        this.activeUsers = activeUsers;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyJoinChannelResponse(user, channel, activeUsers, accepted);
    }
}
