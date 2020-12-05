package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.proprietary_channels.ValideSendInviteMessage;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Message de demande au propriétaire d'enregistrement l'invitation
 *
 */
public class TellOwnerInviteMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -8527233704319089062L;
	private final UserLite guest;
    private final UUID     channelID;
    private final String   response;

    public TellOwnerInviteMessage(UserLite guest, UUID channelID, String response) {
        this.guest    = guest;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyInviteChannel(guest, channelID);
        commClientController.sendMessage(new ValideSendInviteMessage(guest, channelID));
    }
}
