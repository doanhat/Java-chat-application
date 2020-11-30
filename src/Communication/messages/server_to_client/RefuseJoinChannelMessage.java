package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Message envoyé par le serveur en cas de refus de ce dernier de permettre au client de rejoindre un canal particulier.
 *
 */
public class RefuseJoinChannelMessage extends ServerToClientMessage {

    private final UserLite user;
    private final UUID channelID;

    public RefuseJoinChannelMessage(UserLite receiver, UUID channelID) {
        this.user = receiver;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        System.err.println("Refused to join channel " + channelID);
        commClientController.notifyRefusedToJoinChannel(user, channelID);
    }
}
