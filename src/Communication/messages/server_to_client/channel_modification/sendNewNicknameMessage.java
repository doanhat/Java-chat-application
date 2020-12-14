package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.UUID;

public class sendNewNicknameMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -2578930111910220363L;
    private final UserLite user;
    private final UUID channelID;
    private final String name;

    public sendNewNicknameMessage(UserLite user, UUID channelID, String name) {
        this.user = user;
        this.channelID = channelID;
        this.name = name;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().notifyChangeNickName(user, channelID, name);
    }
}