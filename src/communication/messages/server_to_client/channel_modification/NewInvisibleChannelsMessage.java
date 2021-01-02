package communication.messages.server_to_client.channel_modification;

import communication.client.CommunicationClientController;
import communication.messages.abstracts.ServerToClientMessage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NewInvisibleChannelsMessage extends ServerToClientMessage {

    private static final long       serialVersionUID = 3965212554134221180L;
    private final        List<UUID> channelIDs;
    private final        String     explanation;

    public NewInvisibleChannelsMessage(UUID channelID, String explanation) {
        this.channelIDs  = Collections.singletonList(channelID);
        this.explanation = explanation;
    }

    public NewInvisibleChannelsMessage(List<UUID> channelIDs, String explanation) {
        this.channelIDs  = channelIDs;
        this.explanation = explanation;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().notifyInvisibleChannels(channelIDs, explanation);
    }
}
