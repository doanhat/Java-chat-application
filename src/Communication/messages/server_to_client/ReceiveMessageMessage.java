package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.UUID;

/**
 * Cette classe sert à signaler au client l'arrivée d'un nouveau message dans un canal auquel il est abonné.
 */
public class ReceiveMessageMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -2578930171910220363L;
	private final Message message;
    private final UUID    channelID;
    private final Message response;

    /**
     * Constructeur d'un {@link ReceiveMessageMessage}
     * @param message message reçu
     * @param channelID UUID du canal dans lequel le message à été reçu
     * @param response Message auquel notre message est une réponse
     */
    public ReceiveMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyReceiveMessage(message, channelID, response);
    }
}
