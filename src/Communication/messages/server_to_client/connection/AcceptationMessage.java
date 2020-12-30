package Communication.messages.server_to_client.connection;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.List;

/**
 * Ce message sert au serveur à indiquer qu'il accèpte le client, en lui transmettant au passage la liste de canaux et la liste des utilisateurs
 */
public class AcceptationMessage extends ServerToClientMessage {

    private static final long           serialVersionUID = -2723562845245870330L;
    private final        UserLite       user;
    private final        List<Channel>  channelsList;
    private final        List<UserLite> usersList;

    public AcceptationMessage(UserLite sender, List<Channel> channelsList, List<UserLite> usersList) {
        this.user         = sender;
        this.channelsList = channelsList;
        this.usersList    = usersList;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.connectionAccepted(user, usersList, channelsList);
    }
}
