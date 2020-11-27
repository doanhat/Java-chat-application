package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.AcceptJoinChannelMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.messages.server_to_client.NewVisibleChannelMessage;
import Communication.messages.server_to_client.ValidateCreationChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.UUID;

public class SendInviteMessage extends ClientToServerMessage {
    private UserLite sender;
    private UserLite receiver;
    private Message message;
    private Channel channel;

    public SendInviteMessage(UserLite sender, UserLite receiver, Message message, Channel channel){
        this.channel = channel;
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }


    @Override
    protected void handle(CommunicationServerController commController) {

        if(receiver != null && channel != null) {
            commController.SendInvite(sender.getId(), receiver.getId(), message);
            commController.sendMessage(receiver.getId(), new NewUserJoinChannelMessage(receiver, channel.getId()));

        }

    }
}
