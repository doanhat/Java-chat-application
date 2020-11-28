package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;

public class ValidateCreationChannelMessage extends ServerToClientMessage {

    private final Channel newChannel;

    /**
     * Message notifiant a un utilisateur que sa demande de création de channel a été prise en compte
     * @param newChannel
     */
    public ValidateCreationChannelMessage(Channel newChannel) {
        this.newChannel = newChannel;
    }

    /**
     * Notifie le controller du succes
     * @param commController
     */
    @Override
    protected void handle(CommunicationClientController commController) {
        System.err.println("Creation channel" + newChannel.getId() + " est accepté");
        commController.notifyChannelCreated(newChannel);
    }
}
