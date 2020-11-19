package Data.server;

import common.sharedData.UserLite;

import java.util.ArrayList;
import java.util.List;

public class UserListController {
    private List<UserLite> connectedUsers;

    public UserListController() {
        this.connectedUsers= new ArrayList<>();
    }


    public List<UserLite> addConnectedUser(UserLite newUser){
        //TODO validate if user already exists in the list
        connectedUsers.add(newUser);
        return connectedUsers;
    }

    public List<UserLite> removeConnectedUser(UserLite user){
        connectedUsers.remove(user);
        return connectedUsers;
    }

    public List<UserLite> getConnectedUsers(){
        return this.connectedUsers;
    }
}