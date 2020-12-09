package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class ValidateChangeNickNameMessage extends ClientToServerMessage {
    private UserLite user;
    private UUID channelId;
    private String name;

    public ValidateChangeNickNameMessage(UserLite user, UUID channelId, String name) {
        this.user = user;
        this.channelId = channelId;
        this.name = name;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel ch = commController.getChannel(channelId);
        commController.requestNicknameChange(user, ch, name);
    }
}
