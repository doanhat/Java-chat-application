package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;
// TODO: verify with delete channel and kick user

/**
 * Classe indiquant le nouveau channel invisible au client
 * @implNote Cette classe n'est pas encore completement implementée, et ne doit pas être utilisée sans vérifier au préalable ce qu'elle fait de ce qu'elle ne fait pas encore
 */
public class NewInvisibleChannelMessage extends ServerToClientMessage {
    private final Channel channel;
    private final List<UserLite> users;

    public NewInvisibleChannelMessage(Channel channel){
        this.channel = channel;
        this.users = channel.getAcceptedPersons();
    }
/*
    public Channel getChannel() {
        return this.channel;
    }

    public List<UserLite> getUsers() {
        return users;
    }
*/
    @Override
    protected void handle(CommunicationClientController commClientController) {
        if (channel.getVisibility() == Visibility.PRIVATE) {
            //TODO : faire la méthode dans l'interface ICommunicationToData
        }

    }
}
