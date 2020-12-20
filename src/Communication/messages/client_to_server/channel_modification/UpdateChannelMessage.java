package Communication.messages.client_to_server.channel_modification;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Visibility;

import java.util.UUID;

public class UpdateChannelMessage extends ClientToServerMessage {

    private final UUID channelID;
    private final UUID userID;
    private final String name;
    private final String description;
    private final Visibility visibility;

    public UpdateChannelMessage(UUID channelID, UUID userID, String name, String description, Visibility visibility) {
        this.channelID = channelID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.visibility = visibility;
    }

    @Override
    protected void  handle(CommunicationServerController commController){
        commController.requestUpdateChannel(channelID, userID, name, description, visibility);
    }
}
