package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import Communication.messages.server_to_client.chat_action.proprietary_channels.TellOwnerToSaveEditMessage;
import common.sharedData.UserLite;
import common.sharedData.Message;

import java.util.UUID;

public class EditMessagePropMessage extends ClientToServerMessage {
    private final UUID channelID;
    private final UserLite owner;
    private final Message message;
    private final Message newMessage;

    public EditMessagePropMessage(Message message, Message newMessage, UUID channelID, UserLite owner) {
        this.channelID  = channelID;
        this.owner = owner;
        this.message = message;
        this.newMessage = newMessage;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.sendMessage(owner.getId(), new TellOwnerToSaveEditMessage(message, newMessage, channelID));
    }
}
