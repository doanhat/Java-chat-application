package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.messages.server_to_client.TellOwnerToSaveMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

import java.util.UUID;

/**
 * Cette classe corrrespond à l'envoi d'un message sur un channel particulier
 *
 */
public class SendMessageMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 5513528799348758221L;
	private final Message message;
    private final UUID    channelID;
    private final Message response;

    /**
     * Message informant de l'arrive d'un message dans un des channel
     * @param message [Message] chat message
     * @param channelID [UUID] ID du channel
     * @param response [Message] réponde du message
     */
    public SendMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    /**
     * Channel partage : Sauvegarde le message et avertit les utilisateurs du channel de son existence
     * Channel proprietaire : Avertit le proprietaire du nouveau message
     * @param commController [CommunicationServerController] communication controlleur du serveur
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel == null) {
            System.err.println("SendMessageMessage: channel est null");
            return;
        }

        // TODO INTEGRATION V2: cross check with data on ICommunicationServerToData.saveMessageIntoHistory()'s purpose
        // Tell data server to save message for both shared and proprietary channels, in order to update active Channel on server
        commController.saveMessage(message, channel, response);

        // Server serves as a proxy in case of proprietary Channel
        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(channel.getCreator().getId(), new TellOwnerToSaveMessage(message, channelID, response));
        }

        commController.sendMulticast(channel.getAcceptedPersons(),
                                     new ReceiveMessageMessage(message, channelID, response));
    }
}
