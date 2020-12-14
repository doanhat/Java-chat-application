package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.chat_action.proprietary_channels.TellOwnerToDeleteMessageMessage;
import Communication.messages.server_to_client.chat_action.proprietary_channels.TellOwnerToLikeMessageMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.UUID;

public class SaveLikeMessageProp extends ClientToServerMessage {
    private Channel channel;
    private Message msg;
    private UserLite user;
    private UUID ownerID;

    public Channel getChannel() {
        return channel;
    }

    public Message getMsg() {
        return msg;
    }

    public UserLite getUser() {
        return user;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    public SaveLikeMessageProp(Channel chann, Message message, UserLite user){
        this.channel = chann;
        this.msg = message;
        this.user = user;
        this.ownerID = chann.getCreator().getId();
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.sendMessage(ownerID, new TellOwnerToLikeMessageMessage(this.user, this.channel.getId(), this.msg));
    }
}
