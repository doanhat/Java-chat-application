package Communication.messages.server_to_client.chat_action.proprietary_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.ValidateSaveLikeMessageMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.ValideDeleteMessageMessage;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerToLikeMessageMessage extends ServerToClientMessage {

    private UserLite user;
    private UUID channelId;
    private Message msg;

    public TellOwnerToLikeMessageMessage(UserLite user, UUID channelId, Message msg){
        this.user = user;
        this.channelId = channelId;
        this.msg = msg;
    }



    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.saveLike(channelId, msg, user);
        commClientController.sendMessage(new ValidateSaveLikeMessageMessage(channelId, user, msg));
    }
}
