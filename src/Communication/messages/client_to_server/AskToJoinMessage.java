package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;

public class AskToJoinMessage extends ClientToServerMessage {

    private UserLite sender;
    private Channel channel;
    private boolean proprietaryChannel;
    private boolean publicChannel;
    private List<Message> messageList;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public UserLite getSender() {
        return sender;
    }

    public void setSender(UserLite sender) {
        this.sender = sender;
    }

    public void setProprietaryChannel(boolean proprietaryChannel) {
        this.proprietaryChannel = proprietaryChannel;
    }

    public void setPublicChannel(boolean publicChannel) {
        this.publicChannel = publicChannel;
    }

    public boolean isPublicChannel() {
        return publicChannel;
    }

    public boolean isProprietaryChannel() {
        return proprietaryChannel;
    }

    @Override
    protected void handle(CommunicationServerController commClientController) {
        if (isProprietaryChannel() == false){
            commClientController.requestJoinSharedChannel(channel, sender);
        }else{
            messageList = commClientController.requestJoinOwnedChannel(channel, sender);

        }

    }
}
