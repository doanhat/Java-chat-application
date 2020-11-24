package Data.server;

import common.sharedData.UserLite;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserListController {
    private List<UserLite> connectedUsers;

    public UserListController() {
        this.connectedUsers= new ArrayList<>();
    }

    public boolean userIsConnected(UUID userID){
        for(UserLite user : connectedUsers){
            if(user.getId().equals(userID))
                return true;
        }
        return false;
    }

    public void addConnectedUser(UserLite newUser){
        if(!userIsConnected(newUser.getId()))
            connectedUsers.add(newUser);
    }

    public void removeConnectedUser(UUID userID){
        connectedUsers.removeIf(user -> user.getId().equals(userID));
    }

    public List<UserLite> getConnectedUsers(){
        return this.connectedUsers;
    }
}