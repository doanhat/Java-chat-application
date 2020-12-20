package Communication.messages.client_to_server.chat_action;

import Communication.common.ChannelOperation;
import Communication.common.InfoPackage;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.chat_action.proprietary_channels.InformOwnerChatMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.ChannelType;

public class ChatMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 165413248758221L;
    private final ChannelOperation operation;
    private final InfoPackage infoPackage;

    public ChatMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation = operation;
        this.infoPackage = infoPackage;
    }


    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(infoPackage.channelID);

        if (channel == null) {
            return;
        }

        // Server serves as a proxy in case of proprietary Channel
        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(channel.getCreator().getId(), new InformOwnerChatMessage(operation, infoPackage));
        }
        else {
            commController.handleChat(operation, infoPackage);
        }
    }
}
