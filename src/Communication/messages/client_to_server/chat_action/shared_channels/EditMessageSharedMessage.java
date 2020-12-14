package Communication.messages.client_to_server.chat_action.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.UUID;

public class EditMessageSharedMessage extends ClientToServerMessage {
    private final UUID channelID;
    private final Message message;
    private final Message newMessage;

    public EditMessageSharedMessage(Message message, Message newMessage, UUID channelID) {
        this.channelID  = channelID;
        this.message = message;
        this.newMessage = newMessage;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.saveEdit(message, newMessage, channelID);

    }
}
