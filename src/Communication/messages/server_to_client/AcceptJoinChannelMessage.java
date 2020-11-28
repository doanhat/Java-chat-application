package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class AcceptJoinChannelMessage extends ServerToClientMessage {

    private final UserLite user;
    private final UUID channelID;

    /**
     * Message avertissant un utilisateur qu'il a bien rejoint un channel
     * @param receiver
     * @param channelID
     */
    public AcceptJoinChannelMessage(UserLite receiver, UUID channelID) {
        this.user = receiver;
        this.channelID = channelID;
    }

    /**
     * Notifie le controller du succes
     * @param commClientController
     */
    @Override
    protected void handle(CommunicationClientController commClientController) {
        System.err.println("Accepted to join channel " + channelID);
        commClientController.notifyAcceptedToJoinChannel(user, channelID);
    }
}
