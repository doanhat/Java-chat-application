package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.common.ChatOperation;
import Communication.common.ChatPackage;
import Communication.messages.abstracts.ServerToClientMessage;


public class ReceiveChatMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -8527237423704319L;
    private final ChatOperation operation;
    private final ChatPackage chatPackage;

    public ReceiveChatMessage(ChatOperation operation, ChatPackage chatPackage) {
        this.operation = operation;
        this.chatPackage = chatPackage;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().notifyChat(operation, chatPackage);
    }
}
