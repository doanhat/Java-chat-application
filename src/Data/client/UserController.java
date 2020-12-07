package Data.client;

import Data.resourceHandle.FileHandle;
import Data.resourceHandle.FileType;
import Data.resourceHandle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class UserController extends Controller {
    public static final String FILENAME = "users";

    public UserController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
        fileHandle = new FileHandle<>(LocationType.CLIENT, FileType.USER);
        localUserList = fileHandle.readJSONFileToList(FILENAME,User.class);
    }
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
            List<User> listUserLogin = new FileHandle<User>(LocationType.CLIENT, FileType.USER).readJSONFileToList(FILENAME,User.class);
            for (User user : listUserLogin){
                if (user.getNickName().equals(nickName) & user.getPassword().equals(password)){
                    this.localUser = user;
                    this.comClient.userConnect(user.getUserLite());
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
        throw new UnsupportedOperationException();
    }

    /**
     * Save nickname into history.
     *
     * @param user        the user
     * @param channel     the channel
     * @param newNickname the new nickname
     */
    public void saveNicknameIntoHistory(User user, Channel channel, String newNickname) {
        throw new UnsupportedOperationException();
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
        List<UserLite> users = new ArrayList<>();

        // TODO : get real data
        /*for (int i = 1 ; i <= 5 ; i++) {
            users.add(new UserLite("user " + i, "avatar"));
        }*/
        return users;
    }

    public boolean createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
        User user = new User(nickName,avatar,password,lastName,firstName,birthDate);
        addUserToLocalUsers(user);
        fileHandle.addObjectToFile(FILENAME,user,User.class);
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
