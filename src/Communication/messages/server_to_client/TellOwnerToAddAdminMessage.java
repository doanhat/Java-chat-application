package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.NewAdminConfirmation;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerToAddAdminMessage extends ServerToClientMessage {
    private final UserLite user;
    private final UUID channelID;

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