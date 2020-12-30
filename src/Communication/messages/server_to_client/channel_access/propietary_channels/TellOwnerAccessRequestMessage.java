package Communication.messages.server_to_client.channel_access.propietary_channels;

import Communication.client.CommunicationClientController;
import Communication.common.ChannelAccessRequest;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.ChannelAccessGrantedMessage;
import common.shared_data.UserLite;

import java.util.UUID;

public class TellOwnerAccessRequestMessage extends ServerToClientMessage {
    private static final long                 serialVersionUID = -8527233704319089062L;
    private final        ChannelAccessRequest request;
    private final        UserLite             user;
    private final        UUID                 channelID;

    public TellOwnerAccessRequestMessage(ChannelAccessRequest request, UserLite newUser, UUID channelID) {
        this.request   = request;
        this.user      = newUser;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        switch (request) {
            case JOIN:
                commClientController.dataClientHandler().addUserToProprietaryChannel(user, channelID);
                break;
            case LEAVE:
                commClientController.dataClientHandler().requestLeaveChannel(channelID, user);
                break;
            case INVITE:
                commClientController.dataClientHandler().inviteUserToProprietaryChannel(user, channelID);
                break;
            case QUIT:
                commClientController.dataClientHandler().requestQuitChannel(channelID, user);
                break;
            default:
        }

        // Give permission to Server
        commClientController.sendMessage(new ChannelAccessGrantedMessage(request, user, channelID));
    }
}
