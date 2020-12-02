package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerToAddAdminMessage extends ServerToClientMessage {
    private UUID channel;
    private UserLite user;

    public UUID getChannel() {
        return channel;
    }

    public void setChannel(UUID channel) {
        this.channel = channel;
    }

    public UserLite getUser() {
        return user;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    public TellOwnerToAddAdminMessage(UserLite user, UUID channel) {
        this.channel = channel;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyTellOwnerToAddAdmin(user, channel);
    }
}
