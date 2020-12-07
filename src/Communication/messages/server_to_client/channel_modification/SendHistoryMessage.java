package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.List;
import java.util.UUID;

public class SendHistoryMessage extends ServerToClientMessage {
    private UUID channelID;
    private List<Message> history;

    public List<Message> getHistory() {
        return history;
    }

    public SendHistoryMessage(UUID channelID, List<Message> history){
        this.channelID = channelID;
        this.history = history;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.returnChannelHistory(channelID, history);
    }
}
