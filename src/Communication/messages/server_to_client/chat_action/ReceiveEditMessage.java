package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Message;

import java.util.UUID;

/**
 * Indique au utilisateur qu'un message à été édité
 */
public class ReceiveEditMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -8527237423704319L;
    private final Message msg;
    private final Message newMsg;
    private final UUID channelID;

    public ReceiveEditMessage(Message msg, Message newMsg, UUID channelID) {
        this.msg = msg;
        this.newMsg = newMsg;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().notifyEditMessage(msg, newMsg, channelID);
    }
}
