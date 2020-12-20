package Communication.messages.client_to_server.channel_modification;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_modification.ChannelUpdatedMessage;
import Communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
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

        Channel channel = commController.getChannel(channelID);

        if (channel.getVisibility() == Visibility.PUBLIC || visibility == Visibility.PUBLIC) {
            commController.sendBroadcast(new ChannelUpdatedMessage(channelID), null);
        }
        else {
            commController.sendMulticast(channel.getJoinedPersons(), new ChannelUpdatedMessage(channelID));
        }
    }
}
