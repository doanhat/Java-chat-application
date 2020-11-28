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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class UserController extends Controller {
    public UserController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }
    private User localUser;

    public boolean verificationAccount(String nickName, String password){

        /**
         * ON TEST EN UN USER EN DUR POUR L'INTEGRATION
         */

        try {
            List<User> listUserLogin = new FileHandle<User>(LocationType.CLIENT, FileType.USER).readJSONFileToList("users",User.class);
            for (User user : listUserLogin){
                if (user.getNickName().equals(nickName) & user.getPassword().equals(password)){
                    this.localUser = user;
                    this.comClient.userConnect(user.getUserLite());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
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
    public void addUserToChannel(UserLite user, UUID channelId) {
        //channelClient.userBanCancelledNotification(user,channelClient.getChannel(channelId));
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
            users.add(new UserLite("user " + i, "avatar"));
        }
        return users;
    }
}
