package data.server;

import common.interfaces.server.IServerCommunicationToData;
import common.shared_data.*;
import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ServerCommunicationToData implements IServerCommunicationToData {
    private UserListController userListController;
    private ChannelsListController channelsListController;

    public ServerCommunicationToData(UserListController userListController, ChannelsListController channelsListController) {
        this.userListController = userListController;
        this.channelsListController = channelsListController;
    }

    @Override
    public Channel requestChannelCreation(Channel channel, boolean isShared, boolean isPublic, UserLite owner) {
        channel.setVisibility(isPublic ? Visibility.PUBLIC : Visibility.PRIVATE);
        channel.setType(isShared ? ChannelType.SHARED : ChannelType.OWNED);
        channel.updateCreator(owner);

        channelsListController.addChannel(channel);

        return channel;
    }

    @Override
    public boolean requestChannelRemoval(UUID channelID, UserLite user) {
        Channel channel = channelsListController.searchChannelById(channelID);
        if(channel!=null && channel.userIsAdmin(user.getId())){
            channelsListController.saveDeletionIntoHistory(channelID);
            channelsListController.removeChannel(channelID);
            return true;
        }
        return false;
    }


    @Override
    public Channel updateChannel(UUID channelID, UUID userID, String name, String description, Visibility visibility) {
        Channel channel = channelsListController.searchChannelById(channelID);
        if(channel != null){
            if(channel.userIsAdmin(userID)){
                if (name!=null) channel.setName(name);
                if(description!=null)channel.setDescription(description);
                if(visibility!=null) channel.setVisibility(visibility);
                if(channel.getType().equals(ChannelType.SHARED)){
                    channelsListController.writeChannelDataToJSON(channel);
                }
            }
        }
        return channel;
    }


    @Override
    public boolean requestAddUser(Channel ch, UserLite user) {
        Channel channel = channelsListController.searchChannelById(ch.getId());
        if(channel != null && !channel.userIsBanned(user.getId())) {
            channel.addAuthorizedUser(user);

            return true;
        }

        return false;
    }

    @Override
    public void quitChannel(UUID channelID, UserLite user) {
        Channel channel = channelsListController.searchChannelById(channelID);
        if(channel!=null){
            if (channel.getType() == ChannelType.OWNED && user.getId().equals(channel.getCreator().getId())) {
                // TODO verify proprietary quit channel
                channelsListController.removeChannel(channel.getId());
            }

            channel.removeUserAuthorization(user.getId());
        }
    }

    @Override
    public void saveNewAdminIntoHistory(Channel ch, UserLite user) {
        Channel channel = this.channelsListController.searchChannelById(ch.getId());

        if (channel != null) {
            this.channelsListController.writeNewAdminInChannel(channel, user);
        }
    }

    @Override
    public void banUserFromChannel(UserLite user, LocalDate endDate, Boolean isPermanent, String explanation, UUID channelId) {
        Date date = null;

        if (endDate != null) {
            // NOTE if isPermanent is set to true => enddate will be null
             date = java.util.Date.from(endDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }

        channelsListController.banUserFromChannel(user,channelId,date,isPermanent,explanation);
    }

    @Override
    public void cancelUsersBanFromChannel(Channel ch, UserLite user) {
        List<Kick> kicked = ch.getKicked();
        kicked.removeIf(k -> k.getUser().getId().equals(user.getId()));
        ch.addAuthorizedUser(user);
        channelsListController.writeChannelDataToJSON(ch);
    }

    @Override
    public void saveMessageIntoHistory(Channel ch, Message ms, Message response) {
        Channel channel = this.channelsListController.searchChannelById(ch.getId());
        channel.addMessage(ms);

        if (channel.getType() == ChannelType.SHARED) {
            this.channelsListController.writeMessageInChannel(channel, ms, response);
        }
    }


    @Override
    public void editMessage(Channel channel, Message ms) {
        if (channel.getType() == ChannelType.SHARED) {
            this.channelsListController.writeEditMessage(channel.getId(), ms);
        }
    }


    @Override
    public void saveLikeIntoHistory(Channel ch, Message ms, UserLite user) {
        if (ch.getType() == ChannelType.SHARED && ch!=null && ms!=null && user!=null) {
            this.channelsListController.writeLikeIntoHistory(ch.getId(), ms, user);
        }
    }



    @Override
    public void saveRemovalMessageIntoHistory(Channel ch, Message ms, Boolean deletedByCreator) {
        this.channelsListController.writeRemovalMessageInChannel(ch, ms, deletedByCreator);
    }

    @Override
    public List<Message> getHistory(Channel ch) {
        return channelsListController.getChannelMessages(ch.getId());
    }


    @Override
    public List<Channel> getVisibleChannels(UserLite user) {
        List<Channel> visibleChannels = new ArrayList<>();
        List<Channel> sharedChannels = channelsListController.getSharedChannels();
        List<Channel> ownedChannels = channelsListController.getOwnedChannels();

        for (Channel channel: sharedChannels) {
            if ((channel.getVisibility() == Visibility.PUBLIC) || (channel.userIsAuthorized(user.getId()))) {
                if (!channel.userIsBanned(user.getId())) {
                    visibleChannels.add(channel);
                }
            }
        }

        for (Channel channel: ownedChannels) {
            if ((channel.getVisibility() == Visibility.PUBLIC) || (channel.userIsAuthorized(user.getId()))) {
                if (!channel.userIsBanned(user.getId())) {
                    visibleChannels.add(channel);
                }
            }
        }

        return visibleChannels;
    }

    @Override
    public void disconnectUser(UUID userID) {
        if(userID!=null && userListController.userIsConnected(userID)){
            userListController.removeConnectedUser(userID);
        }
    }


    @Override
    public void newConnection(UserLite user) {
        if(user.getId()!=null && !userListController.userIsConnected(user.getId())){
            userListController.addConnectedUser(user);
        }
    }

    @Override
    public List<UserLite> getConnectedUsers() {
        return userListController.getConnectedUsers();
    }

    @Override
    public void updateNickname(Channel ch, UserLite user, String newNickname) {
        userListController.updateNickname(ch, user, newNickname);
    }

    @Override
    public List<Message> getChannelMessages(UUID channelID) {
        return channelsListController.getChannelMessages(channelID);
    }

    @Override
    public boolean joinChannel(UUID ch, UserLite user) {
        Channel channel = channelsListController.searchChannelById(ch);
        if(channel!=null && !channel.userIsBanned(user.getId())){
            channel.addJoinedUser(user);
            channel.addAuthorizedUser(user);

            return true;
        }

        return false;
    }

    @Override
    public void leaveChannel(UUID ch, UserLite user) {
        Channel channel = channelsListController.searchChannelById(ch);
        if(channel!=null){
            if (channel.getType() == ChannelType.OWNED && user.getId().equals(channel.getCreator().getId())) {
                // remove Owned Channel from server after owner left
                channelsListController.removeChannel(channel.getId());
            }

            channel.removeUser(user.getId());
        }
    }

    @Override
    public Boolean checkAuthorization(Channel ch, UserLite user) {
        if (ch.userIsBanned(user.getId())) {
            return false;
        }

        if (ch.getVisibility() == Visibility.PRIVATE && !ch.userIsAuthorized(user.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public Channel getChannel(UUID channelID)
    {
       return channelsListController.searchChannelById(channelID);
    }

    @Override
    public List<Channel> disconnectOwnedChannel(UserLite owner) {
        //Remove channels from owned Channel List in server
        List <Channel> removedChannels = channelsListController.disconnectOwnedChannel(owner);

        //Remove active users from channels that have been removed
        for(Channel channel : removedChannels){
            userListController.removeActiveUsersFromChannel(channel.getId());
        }
        return removedChannels;
    }

    @Override
    public List<UUID> getChannelsWhereUser(UUID userID) {
        return channelsListController.getChannelsWhereUser(userID);
    }

    @Override
    public List<UUID> getChannelsWhereUserActive(UUID userID) {
        return userListController.getChannelsWhereUserActive(userID);
    }

    @Override
    public List<UserLite> getActiveUsersInChannel(UUID channelID) {
        return userListController.getActiveUsersInChannel(channelID);
    }


    @Override
    public void addOwnedChannelsToServerList(List<Channel> ownedChannels, UUID ownerID) {
        for (Channel channel: ownedChannels) {
            //Add channel to the server list of channels
            channelsListController.addChannel(channel);

            //Add active users for each ownedChannel when users are connected
            for (UserLite user: channel.getJoinedPersons()) {
                if(userListController.userIsConnected(user.getId())){
                    userListController.addActiveUser(user.getId(),channel.getId());
                }
            }
        }
    }

    /**
     * Envoyer une image encodée en string Base64 au server pour stocker
     *
     * @param user          utilisateur ayant l'image comme avatar
     * @param encodedString le string encodée en Base64
     */
    @Override
    public void saveAvatarToServer(UserLite user, String encodedString) {
        FileHandle fileHandle = new FileHandle(LocationType.SERVER, FileType.AVATAR);
        try {
            fileHandle.writeEncodedStringToFile(encodedString,user.getId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Récupérer le chemin vers l'avatar de l'utilisateur dans le serveur
     *
     * @param user utilisateur
     * @return
     */
    @Override
    public String getAvatarPath(UserLite user) {
        FileHandle fileHandle = new FileHandle(LocationType.SERVER, FileType.AVATAR);
        return fileHandle.getAvatarPath(user.getId().toString());
    }

    @Override
    public void requestRemoveAdmin(UUID channelID, UUID adminID) {
        Channel channel = channelsListController.searchChannelById(channelID);
        if(channel!=null){
            if(channel.userIsAdmin(adminID) && !channel.getCreator().getId().equals(adminID)){
                channel.removeAdmin(adminID);
                channelsListController.writeRemoveAdminInChannel(channel);
            }
        }
    }

}
