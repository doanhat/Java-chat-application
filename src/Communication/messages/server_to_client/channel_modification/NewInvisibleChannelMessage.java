package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;
import java.util.UUID;

public class NewInvisibleChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = 3965212554134221180L;
	private final UUID channelID;

    public NewInvisibleChannelMessage(UUID channelID){
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyInvisibleChannel(channelID);
    }
}
