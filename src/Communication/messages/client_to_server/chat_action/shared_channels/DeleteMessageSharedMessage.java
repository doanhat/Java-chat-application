package Communication.messages.client_to_server.chat_action.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.UUID;

public class DeleteMessageSharedMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 5513528710048758221L;
    private final UUID channelID;
    private final Message message;
    private final boolean deleteByCreator;

    public DeleteMessageSharedMessage(Channel channel, Message message, Boolean deleteByCreator) {
        this.channelID = channel.getId();
        this.message = message;
        this.deleteByCreator = deleteByCreator;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.deleteMessage(message, channelID, deleteByCreator);
    }
}

