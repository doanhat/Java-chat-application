package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.UUID;

public class NewUserAuthorizeChannelMessage extends ServerToClientMessage {


    private static final long serialVersionUID = -1457887784776431438L;
    private final UserLite user;
    private final UUID channelID;

    /**
     * Crée un message indiquant l'arrivée d'un nouvel utilisateur
     * @param userWhoAuthorized utilisateur ayant rejoint le canal
     * @param channelID Canal en question
     */
    public NewUserAuthorizeChannelMessage(UserLite userWhoAuthorized, UUID channelID) {
        this.user = userWhoAuthorized;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyUserAuthorizeChannel(user, channelID);
    }
}
