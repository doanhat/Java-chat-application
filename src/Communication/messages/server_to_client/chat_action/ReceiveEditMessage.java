package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.UUID;

public class ReceiveEditMessage extends ServerToClientMessage {

    Message msg;
    Message newMsg;
    UUID channelID;


    public ReceiveEditMessage(Message msg, Message newMsg, UUID channelID) {
        this.msg = msg;
        this.newMsg = newMsg;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyEditMessage(msg, newMsg, channelID);
    }
}
