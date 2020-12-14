package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.UUID;

public class LikeSavedMessage extends ServerToClientMessage {
    private UUID channelId;
    private Message msg;
    private UserLite user;


    public LikeSavedMessage(UUID channelId, Message mess, UserLite usr){
        this.channelId = channelId;
        this.msg = mess;
        this.user = usr;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyLikedMessage(channelId, msg, user);
    }
}
