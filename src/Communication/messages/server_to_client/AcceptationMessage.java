package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class AcceptationMessage extends ServerToClientMessage {

    private final List<Channel>   channelsList;
    private final List<UserLite>  usersList;

    /**
     * Message avertissant un utilisateur qu'il s'est bien connecte au serveur
     * @param channelsList
     * @param usersList
     */
    public AcceptationMessage(List<Channel> channelsList, List<UserLite> usersList) {
        this.channelsList = channelsList;
        this.usersList    = usersList;
    }

    /**
     * Notifie le controller du succes
     * @param commController
     */
    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyConnectionSuccess(usersList, channelsList);
    }
}
