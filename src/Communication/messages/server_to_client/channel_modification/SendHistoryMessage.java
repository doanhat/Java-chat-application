package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.List;

public class SendHistoryMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -1351564319089062L;
    private final Channel channel;
    private final List<UserLite> activeUsers;

    public SendHistoryMessage(Channel channel, List<UserLite> activeUsers){
        this.channel = channel;
        this.activeUsers = activeUsers;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.channelHandler().returnChannelHistory(channel, activeUsers);
    }
}
