package Communication.messages.server_to_client.channel_handling;

import java.util.logging.Level;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;

/**
 * Message de refus de création d'un cannal envoyé par le serveur si il n'autorise pas la création d'un canal
 */
public class RefuseCreationChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = 73255288821227363L;
	private final Channel refusedChannel;
	

    public RefuseCreationChannelMessage(Channel refusedChannel) {
        this.refusedChannel = refusedChannel;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        logger.log(Level.WARNING, "Creation channel pour {} est refusé", refusedChannel.getId());

        commController.notifyCreationChannelRefused(refusedChannel);
    }
}