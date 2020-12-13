package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

public class SendHistoryMessage extends ServerToClientMessage {

    // Pretty similar to JoinChannelResponseMessage
    private static final long serialVersionUID = -1351564319089062L;
    private final Channel channel;
    private final List<UserLite> activeUsers;

    public SendHistoryMessage(Channel channel, List<UserLite> activeUsers){
        this.channel = channel;
        this.activeUsers = activeUsers;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.returnChannelHistory(channel, activeUsers);
    }
}
