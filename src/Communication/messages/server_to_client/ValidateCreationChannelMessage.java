package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;

/**
 * Message validant la création d'un canal particulier
 *
 */
public class ValidateCreationChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -2287773862822477125L;
	private final Channel newChannel;

    public ValidateCreationChannelMessage(Channel newChannel) {
        this.newChannel = newChannel;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        System.err.println("Creation channel" + newChannel.getId() + " est accepté");
        commController.notifyChannelCreated(newChannel);
    }
}
