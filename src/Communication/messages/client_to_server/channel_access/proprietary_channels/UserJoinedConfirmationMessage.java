package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AcceptJoinChannelMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class UserJoinedConfirmationMessage extends ClientToServerMessage {
    private final Channel channel;
    private final UserLite user;

    /**
     * Message de confirmation de la connection d'un utilisateur a un channel proprietaire
     * @param user [UserLite] nouveau utilisateur connecte
     * @param channel [Channel] channel rejoint
     */
    public UserJoinedConfirmationMessage(UserLite user, Channel channel) {
        this.channel  = channel;
        this.user = user;

    }

    /**
     * Notifie tous les utilisateurs du channel
     * @param commController Controller du serveur
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        List<UserLite> users = commController.getChannelConnectedUserList(channel.getId()); //Obtient la liste des utilisateurs connectes

        if (channel != null)
        {
            // send Acceptation back to sender
            commController.sendMessage(user.getId(), new AcceptJoinChannelMessage(user, channel));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            commController.sendMulticast(users,
                    new NewUserJoinChannelMessage(user, channel.getId()));
        }
    }
}
