package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.List;
import java.util.UUID;

public class ReceiveHistoryMessage extends ServerToClientMessage {
    private UUID channelId;
    private List<Message> history;

    public ReceiveHistoryMessage(UUID channelID, List<Message> history){
        this.channelId = channelID;
        this.history = history;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        if(commClientController.requestHistory(channelId) != null) {
            this.history = commClientController.requestHistory(channelId);
        }
    }
}
