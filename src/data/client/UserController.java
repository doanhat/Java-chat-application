package data.client;

import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.Channel;
import common.shared_data.User;
import common.shared_data.UserLite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class UserController extends Controller {
    public UserController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient, DataClientController controller) {
        super(comClient, channelClient, mainClient);
        dataController = controller;
        fileHandle = new FileHandle<User>(LocationType.client, FileType.user);
        localUserList = fileHandle.readJSONFileToList("users",User.class);
    }
    private DataClientController dataController;
    private User localUser;
    private List<User> localUserList;
    private FileHandle<User> fileHandle;
    public User getLocalUser() {
        return localUser;
    }

    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }

    public List<User> getLocalUserList() {
        return localUserList;
    }

    public void setLocalUserList(List<User> localUserList) {
        this.localUserList = localUserList;
    }

    public boolean verificationAccount(String nickName, String password){

        try {
            List<User> listUserLogin = new FileHandle<User>(LocationType.client, FileType.user).readJSONFileToList("users",User.class);
            for (User user : listUserLogin){
                if (user.getNickName().equals(nickName) & user.getPassword().equals(password)){
                    this.localUser = user;
                    this.comClient.userConnect(user.getUserLite());
                    this.dataController.getChannelController().loadProprietaryChannels(user);
                    this.mainClient.addAllChannels(dataController.getChannelController().getChannelList());
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    User getUser() {
        return localUser;
    }

    /**
     * Update nickname.
     *
     * @param user        the user
     * @param channel     the channel
     * @param newNickname the new nickname
     */
    public void updateNickname(User user, Channel channel, String newNickname) {

    }

    /**
     * Save nickname into history.
     *
     * @param user        the user
     * @param channel     the channel
     * @param newNickname the new nickname
     */
    public void saveNicknameIntoHistory(User user, Channel channel, String newNickname) {

    }

    /**
     * Add user to channel.
     *  @param user    the user
     * @param channelId the channel
     */
    public void unbannedUserTochannel(UserLite user, UUID channelId) {
        channelClient.userBanCancelledNotification(user,channelClient.getChannel(channelId));
    }


    /**
     * Get all connected users
     *
     * @return List<UserLite> connected users
     */
    public List<UserLite> getConnectedUsers() {
        List<UserLite> users = new ArrayList<UserLite>();

        // TODO : get real data
        /*for (int i = 1 ; i <= 5 ; i++) {
            users.add(new UserLite("user " + i, "avatar"));
        }*/
        return users;
    }

    public boolean createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
        User user = new User(nickName,avatar,password,lastName,firstName,birthDate);
        addUserToLocalUsers(user);
        fileHandle.addObjectToFile("users",user,User.class);
        return true;
    }

    private void addUserToLocalUsers(User user) {
        for (UserLite userLite : localUserList){
            if (userLite.getId().equals(user.getId())){
                localUserList.remove(userLite);
            }
        }
        localUserList.add(user);
    }


}