package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.List;

public class SendHistoryMessage extends ServerToClientMessage {
    private Channel channel;
    private List<Message> history;


    public Channel getChannel() {
        return channel;
    }

    public List<Message> getHistory() {
        return history;
    }

    public SendHistoryMessage(Channel channel, List<Message> history){
        this.channel = channel;
        this.history = history;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.sendHistory(channel, history);
    }
}
