package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

public class ReplyClientPulseMessage extends ServerToClientMessage {

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.receiveServerHeartBeat();
    }
}