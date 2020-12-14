package Communication.messages.server_to_client.chat_action.proprietary_channels;

import Communication.client.CommunicationClientController;
import Communication.common.ChatOperation;
import Communication.common.ChatPackage;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.OwnerValidateChatMessage;

public class InformOwnerChatMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -287745780048758221L;
    private final ChatOperation operation;
    private final ChatPackage chatPackage;

    public InformOwnerChatMessage(ChatOperation operation, ChatPackage chatPackage) {
        this.operation = operation;
        this.chatPackage = chatPackage;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().saveChat(operation, chatPackage);

        // Send Confirmation back to Server
        commController.sendMessage(new OwnerValidateChatMessage(operation, chatPackage));
    }
}
