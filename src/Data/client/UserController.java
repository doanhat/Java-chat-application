package Data.client;

import Data.resourceHandle.FileHandle;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class UserController extends Controller {
    public UserController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }

    public boolean verificationAccount(String nickName, String password){
        List<User> listUserLogin = new FileHandle().readJSONFileToList("users",User.class);
        for (User user : listUserLogin){
            if (user.getNickName().equals(nickName) & user.getPassword().equals(password)){
                this.comClient.userConnect(user.getUserLite());
            }

        }
        return true;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    User getUser() {
        return null;
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
     *
     * @param user    the user
     * @param channel the channel
     */
    public void addUserToChannel(User user, Channel channel) {

    }

    /**
     * Disconnect user.
     *
     * @param user the user
     */
    public void disconnectUser(User user) {

    }

    /**
     * New connection user.
     *
     * @param user the user
     */
    public void newConnectionUser(User user) {

    }

    /**
     * Get all connected users
     *
     * @return List<UserLite> connected users
     */
    public List<UserLite> getConnectedUsers() {
        List<UserLite> users = new ArrayList<UserLite>();

        // TODO : get real data
        for (int i = 1 ; i <= 5 ; i++) {
            users.add(new UserLite(UUID.randomUUID(), "user " + i, "avatar"));
        }
        return users;
    }
}
