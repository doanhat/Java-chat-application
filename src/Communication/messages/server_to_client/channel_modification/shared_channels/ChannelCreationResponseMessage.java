package Communication.messages.server_to_client.channel_modification.shared_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Channel;

/**
 * Message validant la création d'un canal particulier
 *
 */
public class ChannelCreationResponseMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -2287773862822477125L;
	private final Channel newChannel;
	private final boolean isCreated;

    public ChannelCreationResponseMessage(Channel newChannel, boolean isCreated) {
        this.newChannel = newChannel;
        this.isCreated = isCreated;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.mainHandler().notifyChannelCreationResponse(newChannel, isCreated);
    }
}
