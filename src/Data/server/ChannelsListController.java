package Data.server;

import Data.resourceHandle.FileHandle;
import common.sharedData.Channel;
import common.sharedData.Message;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelsListController {
    private List<Channel> channels;
    private FileHandle fileHandle;

    /**
     * SVP CREER LES FICHIER AVEC DES FONCTIONS
     */

    public ChannelsListController() {
        this.fileHandle = new FileHandle(System.getProperty("user.dir")+"/projet-lo23a20d1/resource/channels/");
        this.channels = createChannelListFromJSONFiles();
    }

    public List<Channel> createChannelListFromJSONFiles(){
        List<Channel> list = new ArrayList<>();
//        String [] pathnames;
//        try{
//            File f = new File(fileHandle.getPath());
//
//            pathnames = f.list();
//
//            for (String pathname : pathnames) {
//                pathname = pathname.substring(0, pathname.lastIndexOf('.'));
//                list.add((Channel) fileHandle.readJSONFileToObject(pathname,Channel.class));
//                System.out.println(list.size());
//            }
//        } catch (Exception e){
//           e.printStackTrace();
//        }
        return new ArrayList<>();
    }

    public List<Channel> searchChannelByName(String nom) {
        return null;
    }

    public Channel searchChannelById(UUID id) {
        for(Channel ch : channels){
            if (ch.getId().equals(id))
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

    public void addChannel(Channel channel) {
        if(searchChannelById(channel.getId())==null) {
            channels.add(channel);
            writeChannelDataToJSON(channel);
        }
    }

    public List<Channel> removeChannel(Channel channel) {
        channels.remove(channel);
        return this.channels;
    }

    public List<Channel> getChannels() {
        return this.channels;
    }

    public void writeChannelDataToJSON(Channel channel){
        this.fileHandle.writeJSONToFile(channel.getId().toString(),channel);
    }

    public Channel readJSONToChannelData(UUID idChannel){
        return (Channel) this.fileHandle.readJSONFileToObject("channels/" + idChannel, Channel.class);
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