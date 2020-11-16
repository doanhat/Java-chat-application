package common.interfaces.server;

import common.sharedData.UserLite;

import java.util.List;

public class UserListController {
    private List<UserLite> connectedUsers;

    public List<UserLite> addConnectedUser(UserLite newUser){
        return null;
    }

    public List<UserLite> removeConnectedUser(UserLite user){
        return null;
    }

    public List<UserLite> getConnectedUsers(){
        return this.connectedUsers;
    }
}