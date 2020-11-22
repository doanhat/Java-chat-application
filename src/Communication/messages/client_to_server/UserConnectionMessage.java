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

        System.err.println("Accepte connection du client " + user.getId());

        commController.sendMessage(user.getId(), new AcceptationMessage(userChannels, onlineUsers));

        // broadcast nouveau client info aux autres clients
        commController.sendBroadcast(new NewUserConnectedMessage(user));
    }
}
