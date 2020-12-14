package Communication.messages.client_to_server.chat_action.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.UUID;

public class SaveLikeMessageShared extends ClientToServerMessage {
    private UUID channelId;
    private Message msg;
    private UserLite user;

    public UUID getChannelId() {
        return channelId;
    }

    public Message getMsg() {
        return msg;
    }

    public UserLite getUser() {
        return user;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    public SaveLikeMessageShared(UUID channId, Message message, UserLite user){
        this.channelId = channId;
        this.msg = message;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.saveLikeMessage(channelId, msg, user);
    }
}
