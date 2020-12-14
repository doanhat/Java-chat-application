package Communication.messages.server_to_client.channel_access.propietary_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.NewAdminConfirmation;
import common.shared_data.UserLite;

import java.util.UUID;

public class TellOwnerToAddAdminMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -852723370437162L;
    private final UserLite user;
    private final UUID channelID;

    public TellOwnerToAddAdminMessage(UserLite user, UUID channelID){
        this.user = user;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.addAdminToProprietaryChannel(channelID, user);

        // Send Confirmation back to server
        commController.sendMessage(new NewAdminConfirmation(user, channelID));
    }
}