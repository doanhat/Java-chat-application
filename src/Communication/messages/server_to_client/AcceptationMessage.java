package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.List;

public class AcceptationMessage extends ServerToClientMessage {

    private final List<Channel>   channelsList;
    private final List<UserLite>  usersList;

    public AcceptationMessage(List<Channel> channelsList, List<UserLite> usersList) {
        this.channelsList = channelsList;
        this.usersList    = usersList;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyConnectionSuccess(usersList, channelsList);
    }
}
