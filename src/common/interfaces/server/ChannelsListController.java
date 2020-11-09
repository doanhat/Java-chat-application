package common.interfaces.server;

import common.sharedData.Channel;

import java.util.List;

public class ChannelsListController {
    private List<Channel> channels;


    public List<Channel> searchChannelByName(String nom) {
        return null;
    }

    public List<Channel> searchChannelById(int id) {
        return null;
    }

    public List<Channel> searchChannelByDesc(String description) {
        return null;
    }

    public List<Channel> searchChannelByUsers(List<String> users) {
        return null;
    }

    public List<Channel> addChannel(Channel channel) {
        return null;
    }

    public List<Channel> removeChannel(Channel channel) {
        return null;
    }

    public List<Channel> getChannels() {
        return this.channels;
    }
}