package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class ValidateSaveLikeMessageMessage extends ClientToServerMessage {
    private UUID channelId;
    private UserLite user;
    private Message msg;

    public ValidateSaveLikeMessageMessage(UUID channelId, UserLite user, Message msg){
        this.channelId = channelId;
        this.user = user;
        this.msg = msg;
    }


    @Override
    protected void handle(CommunicationServerController commController) {
       commController.saveLikeMessage(channelId, msg, user);
    }
}
