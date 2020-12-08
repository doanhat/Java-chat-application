package data.server;

import common.shared_data.UserLite;
import common.shared_data.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserListController {
    private List<UserLite> connectedUsers;
    private ChannelsListController channelsListController;
    private List<ActiveUser> activeUserList;

    public UserListController(ChannelsListController channelsListController) {
        this.connectedUsers= new ArrayList<>();
        this.channelsListController= channelsListController;
        activeUserList = new ArrayList<>();
    }

    public boolean userIsConnected(UUID userID){
        for(UserLite user : connectedUsers){
            if(user.getId().equals(userID))
                return true;
        }
        return false;
    }

    public UserLite getConnectedUserInfo(UUID userID){
        for (UserLite user: connectedUsers) {
            if (user.getId().equals(userID))
                return user;
        }
        return null;
    }

    public void addConnectedUser(UserLite newUser){
        //Add user to connected users list
        if(!userIsConnected(newUser.getId()))
            connectedUsers.add(newUser);

        //Add user to active users list (for each channel)
        List <UUID> channelsWhereUser = channelsListController.getChannelsWhereUser(newUser.getId());
        for (UUID channelID: channelsWhereUser) {
            ActiveUser au = new ActiveUser(channelID,newUser.getId());
            activeUserList.add(au);
        }
    }

    public List<UUID> getChannelsWhereUserActive(UUID userID){
        List <UUID> res = new ArrayList<>();
        for (ActiveUser au: activeUserList) {
            if(au.getUserID().equals(userID))
                res.add(au.getChannelID());
        }
        return res;
    }

    public List<UserLite> getActiveUsersInChannel(UUID channelID){
        List <UserLite> res = new ArrayList<>();
        for (ActiveUser au: activeUserList) {
            if(au.getChannelID().equals(channelID)) {
                UserLite userInfo = getConnectedUserInfo(au.getUserID());
                if(userInfo != null)
                    res.add(getConnectedUserInfo(au.getUserID()));
            }
        }
        return res;
    }

    public void removeConnectedUser(UUID userID){
        connectedUsers.removeIf(user -> user.getId().equals(userID));
        //Remove user from active users
        activeUserList.removeIf(au -> (au.getUserID().equals(userID)));
    }

    public void addActiveUser(UUID userID, UUID channelID){
        activeUserList.add(new ActiveUser(channelID,userID));
    }

    public List<UserLite> getConnectedUsers(){
        return this.connectedUsers;
    }
}