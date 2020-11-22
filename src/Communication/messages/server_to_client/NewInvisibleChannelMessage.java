package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;
// TODO: verify with delete channel and kick user
public class NewInvisibleChannelMessage extends ServerToClientMessage {
    private Channel channel;
    private List<UserLite> users;

    public NewInvisibleChannelMessage(Channel channel){
        this.channel = channel;
        this.users = channel.getAcceptedPersons();
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<UserLite> getUsers() {
        return users;
    }

    public void setUsers(List<UserLite> users) {
        this.users = users;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        if (channel.getVisibility() == Visibility.PRIVATE) {
            //TODO : faire la méthode dans l'interface ICommunicationToData
        }

    }
}
