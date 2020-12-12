package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToIHMChannel;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.User;

import java.util.List;

public class VirtualCommunicationToIHMChannel implements ICommunicationToIHMChannel {
    @Override
    public void changeNickname(User user) {

    }

    @Override
    public void displayHistory(Channel channel, List<Message> history) {

    }
}
