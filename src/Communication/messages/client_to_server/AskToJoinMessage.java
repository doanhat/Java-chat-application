package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AcceptJoinChannelMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.messages.server_to_client.RefuseJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class AskToJoinMessage extends ClientToServerMessage {

    private final UserLite sender;
    private final UUID channelID;

    /**
     * Message notifiant l'arrive d'un utilisateur sur un channel
     * @param channelID
     * @param requester
     */
    public AskToJoinMessage(UUID channelID, UserLite requester) {
        this.sender = requester;
        this.channelID = channelID;
    }

    /**
     * Ajoute l'utilisateur sur le channel
     * Genere un message avertissant l'utilisateur de son ajout
     * Genere un message avertissant les autres utilisateurs du channel de l'arrivé d'un nouvel utilisateur
     * @param commController
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel != null && commController.requestJoinChannel(channel, sender))
        {
            // send Acceptation back to sender
            commController.sendMessage(sender.getId(), new AcceptJoinChannelMessage(sender, channelID));

            // notify other users new User has joined channel
            commController.sendMulticast(channel.getAcceptedPersons(),
                                         new NewUserJoinChannelMessage(sender, channelID),
                                         sender);
        }
        else {
            // send Refusal back to sender
            commController.sendMessage(sender.getId(), new RefuseJoinChannelMessage(sender, channelID));
        }
    }
}
