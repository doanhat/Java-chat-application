package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class AcceptationMessage extends ServerToClientMessage {

    private List<Channel> channelsList;
    private List<UserLite> usersList;

    public AcceptationMessage(List<Channel> channelsList, List<UserLite> usersList) {
        this.channelsList = channelsList;
        this.usersList = usersList;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        // TODO give user lists and channel list to other modules
    }
}
