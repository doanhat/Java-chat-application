package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.common.ChannelOperation;
import Communication.common.InfoPackage;
import Communication.messages.abstracts.ServerToClientMessage;


public class ReceiveChatMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -8527237423704319L;
    private final ChannelOperation operation;
    private final InfoPackage infoPackage;

    public ReceiveChatMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation = operation;
        this.infoPackage = infoPackage;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().notifyChat(operation, infoPackage);
    }
}
