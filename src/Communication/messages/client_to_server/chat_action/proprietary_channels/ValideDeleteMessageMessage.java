package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Message;

import java.util.UUID;

public class ValideDeleteMessageMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 5135287100487841L;
    private final UUID channelID;
    private final Message message;
    private final boolean deleteByCreator;

    public ValideDeleteMessageMessage(Message message, UUID channelID, Boolean deleteByCreator) {
        this.channelID = channelID;
        this.message = message;
        this.deleteByCreator = deleteByCreator;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.deleteMessage(message, channelID, deleteByCreator);
    }
}
