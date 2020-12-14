package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;

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
