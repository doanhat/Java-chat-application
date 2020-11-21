package Data.server;

import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelsListController {
    private List<Channel> channels;

    public ChannelsListController() {
        this.channels = new ArrayList<>();
    }

    public List<Channel> searchChannelByName(String nom) {
        return null;
    }

    public Channel searchChannelById(UUID id) {
        for(Channel ch : channels){
            if (ch.getId()==id)
                return ch;
        }
        return null;
    }

    public List<Message> getChannelMessages(UUID channelID){
        return searchChannelById(channelID).getMessages();
    }

    public List<Channel> searchChannelByDesc(String description) {
        return null;
    }

    public List<Channel> searchChannelByUsers(List<String> users) {
        return null;
    }

    public List<Channel> addChannel(Channel channel) {
        if(searchChannelById(channel.getId())==null)
            channels.add(channel);
        return this.channels;
    }

    public List<Channel> removeChannel(Channel channel) {
        channels.remove(channel);
        return this.channels;
    }

    public List<Channel> getChannels() {
        return this.channels;
    }
}