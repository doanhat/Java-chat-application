package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NewInvisibleChannelsMessage extends ServerToClientMessage {

	private static final long serialVersionUID = 3965212554134221180L;
	private final List<UUID> channelIDs;

    public NewInvisibleChannelsMessage(UUID channelID){
        this.channelIDs = Collections.singletonList(channelID);
    }

    public NewInvisibleChannelsMessage(List<UUID> channelIDs){
        this.channelIDs = channelIDs;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyInvisibleChannels(channelIDs);
    }
}
