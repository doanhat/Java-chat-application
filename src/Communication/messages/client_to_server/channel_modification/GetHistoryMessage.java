package Communication.messages.client_to_server.channel_modification;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_modification.SendHistoryMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

public class GetHistoryMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 74269207475665L;
    private final UUID channelID;
    private final UserLite sender;

    public GetHistoryMessage(UUID channelID, UserLite user){
        this.channelID = channelID;
        this.sender = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {

        // NOTE : Server should have the list of all active channels, proprietary and shared
        Channel channel = commController.getChannel(channelID);

        if(channel != null) {
            commController.sendMessage(sender.getId(), new SendHistoryMessage(channel, commController.channelConnectedUsers(channel)));
        }
    }
}
