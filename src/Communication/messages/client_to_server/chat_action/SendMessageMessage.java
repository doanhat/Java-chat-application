package Communication.messages.client_to_server.chat_action;

import java.util.UUID;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.chat_action.ReceiveMessageMessage;
import Communication.messages.server_to_client.chat_action.proprietary_channels.TellOwnerToSaveMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.ChannelType;
import common.sharedData.Message;

public class SendMessageMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 5513528799348758221L;
	private final Message message;
    private final UUID    channelID;
    private final Message response;

    public SendMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel == null) {
            System.err.println("SendMessageMessage: channel est null");
            return;
        }

        // Tell data server to save message for both shared and proprietary channels, in order to update active Channel on server
        commController.saveMessage(message, channel, response);

        // Server serves as a proxy in case of proprietary Channel
        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(channel.getCreator().getId(), new TellOwnerToSaveMessage(message, channelID, response));
        }

        commController.sendMulticast(channel.getJoinedPersons(),
                                     new ReceiveMessageMessage(message, channelID, response));
    }
}
