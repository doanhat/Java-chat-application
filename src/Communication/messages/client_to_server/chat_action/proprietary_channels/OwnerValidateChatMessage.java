package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.common.ChatOperation;
import Communication.common.ChatPackage;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;

public class OwnerValidateChatMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 5135287100487841L;
    private final ChatOperation operation;
    private final ChatPackage chatPackage;

    public OwnerValidateChatMessage(ChatOperation operation, ChatPackage chatPackage) {
        this.operation = operation;
        this.chatPackage = chatPackage;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.handleChat(operation, chatPackage);
    }
}
