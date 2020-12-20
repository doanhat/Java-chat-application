package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.UUID;

/**
 * Cette classe permet d'indiquer l'arrivée d'un nouvel utilisateur sur le servuer
 *
 */
public class NewUserJoinChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -1456827784776431438L;
	private final UserLite user;
    private final UUID channelID;

    /**
     * Crée un message indiquant l'arrivée d'un nouvel utilisateur
     * @param userWhoJoined utilisateur ayant rejoint le canal
     * @param channelID Canal en question
     */
    public NewUserJoinChannelMessage(UserLite userWhoJoined, UUID channelID) {
        this.user = userWhoJoined;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.channelHandler().notifyUserJoinedChannel(user, channelID);
    }
}
