package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.proprietary_channels.NewAdminConfirmation;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerToAddAdminMessage extends ServerToClientMessage {
    private final UserLite user;
    private final UUID channelID;

    /**
     * Message avertissant un utilisateur qu'un nouvel administrateur a été ajouté
     * @param user
     * @param channelID
     */
    public TellOwnerToAddAdminMessage(UserLite user, UUID channelID){
        this.user = user;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.saveNewAdmin(channelID, user);

        // Send Confirmation back to server
        commController.sendMessage(new NewAdminConfirmation(user, channelID));
    }
}