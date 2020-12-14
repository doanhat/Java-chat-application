package Communication.messages.server_to_client.channel_modification.shared_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.ValidateChangeNickNameMessage;
import common.shared_data.UserLite;

import java.util.UUID;

public class TellOwnerToUpdateNicknameMessage extends ServerToClientMessage {
    UserLite user;
    UUID channelID;
    String name;


    public TellOwnerToUpdateNicknameMessage(UserLite user, UUID channelID, String name) {
        this.user = user;
        this.channelID = channelID;
        this.name = name;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().notifyChangeNickName(user, channelID, name);
        commClientController.sendMessage(new ValidateChangeNickNameMessage(user, channelID, name));
    }
}
