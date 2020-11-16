package Communication.messages.client_to_server;

import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.AcceptationMessage;
import Communication.messages.server_to_client.NewUserConnectedMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;

import java.util.List;
import java.util.UUID;

public class UserConnectionMessage extends ClientToServerMessage {

    private UserLite user;

    public UserConnectionMessage(UserLite user) {
        this.user = user;
    }

    public UserLite getUser() {
        return user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        List<Channel> userChannels = commController.getUserChannels(user);
        List<UserLite> onlineUsers = commController.onlineUsers();

        NetworkMessage acceptation = new AcceptationMessage(userChannels, onlineUsers);

        commController.sendMessage(user.getId(), acceptation);

        // broadcast nouveau client info aux autres clients
        NetworkMessage newUserNotification = new NewUserConnectedMessage(user);

        for (UserLite otherUser: onlineUsers) {
            commController.sendMessage(otherUser.getId(), newUserNotification);
        }
    }
}
