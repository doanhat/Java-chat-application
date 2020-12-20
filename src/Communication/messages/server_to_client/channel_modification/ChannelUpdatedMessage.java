package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.UUID;

public class ChannelUpdatedMessage extends ServerToClientMessage {

    private final UUID channelID;

    public ChannelUpdatedMessage(UUID channelID){
        this.channelID = channelID;
    }

    protected void handle(CommunicationClientController commController){
        //TODO V4 : voir avec Data pour la méthode permettant de notifier de la modification d'un channel
        //commController.dataClientHandler().notifyModifiedChannel(UUID channelID);
    }

}
