package Communication.messages.client_to_server.generic;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.SendHistoryMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

public class GetHistoryMessage extends ClientToServerMessage {
    private UUID channelID;
    private UserLite sender;

    public UUID getChannelID() {
        return channelID;
    }

    public void setChannelID(UUID channelID) {
        this.channelID = channelID;
    }

    public UserLite getSender() {
        return sender;
    }

    public void setSender(UserLite sender) {
        this.sender = sender;
    }

    public GetHistoryMessage(UUID channelID, UserLite user){
        this.channelID = channelID;
        this.sender = user;
    }

    /*
     * Méthode pour retourner la liste des messages postés dans un channel
     *
     * @param channel le channel pour lequel on veut retourner ces messages
     *
    List<Message> getHistory(Channel channel);*/

    @Override
    protected void handle(CommunicationServerController commController) {

        Channel channel = commController.getChannel(channelID);


        if(channel != null && commController.getHistoryMessage(channel, sender) != null){

            List<Message> history = commController.getHistoryMessage(channel, sender);
            commController.sendMessage(sender.getId(), new SendHistoryMessage(channel, history));
        }
/*

 */

    }
}
