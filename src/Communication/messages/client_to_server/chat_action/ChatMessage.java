package Communication.messages.client_to_server.chat_action;

import Communication.common.ChatOperation;
import Communication.common.ChatPackage;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.chat_action.proprietary_channels.InformOwnerChatMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.ChannelType;

public class ChatMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 165413248758221L;
    private final ChatOperation operation;
    private final ChatPackage chatPackage;

    public ChatMessage(ChatOperation operation, ChatPackage chatPackage) {
        this.operation = operation;
        this.chatPackage = chatPackage;
    }


    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(chatPackage.channelID);

        if (channel == null) {
            return;
        }

        // Server serves as a proxy in case of proprietary Channel
        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(channel.getCreator().getId(), new InformOwnerChatMessage(operation, chatPackage));
        }
        else {
            commController.handleChat(operation, chatPackage);
        }
    }
}
