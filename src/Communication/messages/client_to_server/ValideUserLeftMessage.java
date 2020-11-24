package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class ValideUserLeftMessage extends ClientToServerMessage {
    Channel channel;
    UserLite exMembers;
    List<UserLite> members;

    public ValideUserLeftMessage(Channel channel, UserLite exMembers, List<UserLite> members) {
        this.exMembers = exMembers;
        this.members = members;
    }

    public UserLite getExMembers() {
        return exMembers;
    }

    public void setExMembers(UserLite exMembers) {
        this.exMembers = exMembers;
    }

    public List<UserLite> getMembers() {
        return members;
    }

    public void setMembers(List<UserLite> members) {
        this.members = members;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        commServerController.notifyUserLeftChannel(channel, members, exMembers);
    }
}
