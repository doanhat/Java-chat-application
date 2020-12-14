package data.server;

import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.shared_data.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelsListController {
    private List<Channel> sharedChannels;
    private List<Channel> ownedChannels;
    private FileHandle fileHandle;

    public ChannelsListController() {
        this.fileHandle = new FileHandle<Channel>(LocationType.server, FileType.channel);
        this.sharedChannels = createChannelListFromJSONFiles();
        this.ownedChannels = new ArrayList<>();
    }

    public List<Channel> createChannelListFromJSONFiles(){
        List<Channel> list = fileHandle.readAllJSONFilesToList(Channel.class);
        for( Channel c : list){
            c.setJoinedPersons(new ArrayList<>()); //init pour éviter la liste null
        }
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
        return (Channel) this.fileHandle.readJSONFileToObject(idChannel.toString(), Channel.class);
    }

    /**
     * Enregistre un message et son parent dans l'historique d'un channel.
     *
     * @param channel   Le channel.
     * @param message   Le message à enregistrer.
     * @param parent    Le parent du message, s'il existe.
     */
    public void writeMessageInChannel(Channel channel, Message message, Message parent){
        if (channel != null) {
            List<Message> messages = channel.getMessages();

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

    /**
     * Enregistre un nouveau admin dans l'historique d'un channel.
     *
     * @param channel   Le channel.
     * @param user      L'admin à ajouter.
     */
    public void writeNewAdminInChannel(Channel channel, UserLite user) {
        if (channel.getType() == ChannelType.SHARED) {
            channel.addAdmin(user);
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