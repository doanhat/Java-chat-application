package Communication.messages.server_to_client.chat_action.proprietary_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.ValidateEditMessageMessage;
import common.shared_data.Message;

import java.util.UUID;

public class TellOwnerToSaveEditMessage extends ServerToClientMessage {
    private final UUID channelID;
    private final Message message;
    private final Message newMessage;

    public TellOwnerToSaveEditMessage(Message message, Message newMessage, UUID channelID) {
        this.channelID = channelID;
        this.message = message;
        this.newMessage = newMessage;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.sendMessage(new ValidateEditMessageMessage(message, newMessage, channelID));
    }
}
