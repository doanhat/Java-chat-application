package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Message;

import java.util.UUID;

public class ValidateEditMessageMessage extends ClientToServerMessage {
    private final UUID channelID;
    private final Message message;
    private final Message newMessage;

    public ValidateEditMessageMessage(Message message, Message newMessage, UUID channelID) {
        this.channelID = channelID;
        this.message = message;
        this.newMessage = newMessage;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.saveEdit(message, newMessage, channelID);
    }
}
