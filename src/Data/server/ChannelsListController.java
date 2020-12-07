package Data.server;

import Data.resourceHandle.FileHandle;
import Data.resourceHandle.FileType;
import Data.resourceHandle.LocationType;
import common.sharedData.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChannelsListController {
    private List<Channel> sharedChannels;
    private List<Channel> ownedChannels;
    private FileHandle fileHandle;

    /**
     * SVP CREER LES FICHIER AVEC DES FONCTIONS
     */

    public ChannelsListController() {
        this.fileHandle = new FileHandle<Channel>(LocationType.server, FileType.channel);
        this.sharedChannels = createChannelListFromJSONFiles();
        ownedChannels = new ArrayList<>();
    }

    public List<Channel> createChannelListFromJSONFiles(){
        List<Channel> list = fileHandle.readAllJSONFilesToList(Channel.class);
        return list;
    }

    public List<Channel> searchChannelByName(String nom) {
        return null;
    }

    public Channel searchChannelById(UUID id) {
        /*Chercher dans les channels partagés*/
        for(Channel ch : sharedChannels){
            if (ch.getId().equals(id))
                return ch;
        }
        /*Chercher dans les channels partagés*/
        for(Channel ch : ownedChannels){
            if (ch.getId().equals(id))
                return ch;
        }
        return null;
    }

    public List<Message> getChannelMessages(UUID channelID){
        Channel channel = searchChannelById(channelID);

        if (channel == null) {
            return null;
        }

        return channel.getMessages();
    }

    public List<Channel> searchChannelByDesc(String description) {
        return null;
    }

    public List<Channel> searchChannelByUsers(List<String> users) {
        return null;
    }

    public void addChannel(Channel channel) {
        if(searchChannelById(channel.getId())==null) {
            if(channel.getType().equals(ChannelType.SHARED)) {
                sharedChannels.add(channel);
                writeChannelDataToJSON(channel);
            } else{ //Channel proprietaire
                ownedChannels.add(channel);
            }
        }
    }

    public void removeChannel(UUID channelID) {
        Channel ch = searchChannelById(channelID);
        if(ch!=null) {
            if(ch.getType().equals(ChannelType.SHARED)) {
                sharedChannels.remove(ch);
                fileHandle.deleteJSONFile(channelID.toString());
            } else{ //Channel proprietaire
                ownedChannels.remove(ch);
            }
        }
    }

    public List<Channel> getSharedChannels() {
        return this.sharedChannels;
    }

    public List<Channel> getOwnedChannels() {
        return this.ownedChannels;
    }

    public void writeChannelDataToJSON(Channel channel){
        this.fileHandle.writeJSONToFile(channel.getId().toString(),channel);
    }

    public Channel readJSONToChannelData(UUID idChannel){
        //TODO UPDATE (Espace)
        return (Channel) this.fileHandle.readJSONFileToObject(idChannel.toString(), Channel.class);
    }

    /**
     * Enregistre un message et son parent dans l'historique d'un channel.
     *
     * @param channel le channel.
     * @param message   Le message à enregistrer.
     * @param parent  Le parent du message, s'il existe.
     */
    public void writeMessageInChannel(Channel channel, Message message, Message parent){
        //Channel channel = this.readJSONToChannelData(channelId);

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

    public List<Channel> disconnectOwnedChannel(UserLite owner) {
        List<Channel> userOwnedChannels = new ArrayList<>();
        List<Channel> ownedChannels = getOwnedChannels();

        if (ownedChannels == null) {
            return null;
        }

        for (Channel channel: ownedChannels) {
            if (channel.getCreator().getId().equals(owner.getId())) {
                userOwnedChannels.add(channel);
                ownedChannels.remove(channel);
            }
        }

        return userOwnedChannels;
    }
}