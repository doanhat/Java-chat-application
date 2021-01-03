package communication.messages.client_to_server.channel_access.proprietary_channels;

import communication.common.ChannelAccessRequest;
import communication.messages.abstracts.ClientToServerMessage;
import communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

public class ChannelAccessGrantedMessage extends ClientToServerMessage {
    private static final long                 serialVersionUID = -223713673314704993L;
    private final        ChannelAccessRequest request;
    private final        UUID                 channelID;
    private final        UserLite             user;

    /**
     * Message vers le serveur autorisant un utilisateur a rejoindre un channel
     * @param request Requete
     * @param user Utilisateur pouvant rejoindre
     * @param channelID Channel concerne
     */
    public ChannelAccessGrantedMessage(ChannelAccessRequest request, UserLite user, UUID channelID) {
        this.request   = request;
        this.channelID = channelID;
        this.user      = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // NOTE proprietary channels are already sync on server
        Channel channel = commController.getChannel(channelID);

        if (channel != null) {
            // Even to own channel, we add in join list inside server because it's need it send is message
            commController.changeChannelAccess(request, channel, user);
        }
    }
}
