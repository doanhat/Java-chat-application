package Data.server;

import Data.resourceHandle.FileHandle;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelsListController {
    private List<Channel> channels;
    private FileHandle fileHandle;

    public ChannelsListController() {
        this.channels = new ArrayList<>();
        this.fileHandle = new FileHandle();
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

    public void writeChannelDataToJSON(Channel channel){
        this.fileHandle.writeJSONToFile("channels/"+channel.getId().toString(),channel);
    }

    public Channel readJSONToChannelData(UUID idChannel){
        Channel ch = (Channel) this.fileHandle.readJSONFileToObject("channels/" + idChannel, Channel.class);
        return ch;
    }

    /**
     * Enregistre un message et son parent dans l'historique d'un channel.
     *
     * @param channelId L'ID du channel.
     * @param message   Le message à enregistrer.
     * @param parent  Le parent du message, s'il existe.
     */
    public void writeMessageInChannel(UUID channelId, Message message, Message parent){
        Channel channel = this.readJSONToChannelData(channelId);

        if (channel != null) {
            List<Message> messages = channel.getMessages();
            messages.add(message);

            if (parent != null) {
                for (Message msg : messages) {
                    if (msg.getId().equals(parent.getId())) {
                        msg.addAnswers(message);
                        break;
                    }
                }
            }

            this.writeChannelDataToJSON(channel);
        }
    }
}