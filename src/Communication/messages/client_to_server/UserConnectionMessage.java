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

/** 
 * Cette classe sert à indiquer au serveur que l'on vient de se connecter
 *
 */
public class UserConnectionMessage extends ClientToServerMessage {

	private static final long serialVersionUID = -4369939063238047930L;
	private final UserLite user;

    public UserConnectionMessage(UserLite user) {
        this.user = user;
    }

    public UserLite getUser() {
        return user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // Handle Connection in NetworkUser constructor to avoid synchronization with DF
    }
}
