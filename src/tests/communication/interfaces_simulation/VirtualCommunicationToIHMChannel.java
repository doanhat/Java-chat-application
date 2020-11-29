package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;

import java.util.List;

public class VirtualCommunicationToIHMChannel implements ICommunicationToIHMChannel {
    @Override
    public void changeNickname(User user) {

    }

    @Override
    public void displayHistory(Channel channel, List<Message> history) {

    }

    @Override
    public void sendInvite(User sender, User receiver, Channel channel) {

    }
}
