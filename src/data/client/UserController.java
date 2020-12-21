package data.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.Channel;
import common.shared_data.User;
import common.shared_data.UserLite;

import java.util.*;


public class UserController extends Controller {
    public static final String FILENAME = "users";

    public UserController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient, DataClientController controller) {
        super(comClient, channelClient, mainClient);
        dataController = controller;
        fileHandle = new FileHandle<User>(LocationType.CLIENT, FileType.USER);
        localUserList = fileHandle.readJSONFileToList(FILENAME,User.class);
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
            List<User> listUserLogin = new FileHandle<User>(LocationType.CLIENT, FileType.USER).readJSONFileToList(FILENAME,User.class);
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
        localUserList.removeIf(u -> u.getId().equals(user.getId()));
        localUserList.add(user);
    }

    private User searchUserById(UUID userId){
        for (User u : localUserList){
            if (u.getId().equals(userId)){
                return u;
            }
        }
        return null;
    }
    public void editProfile(User user, String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
        User u = searchUserById(user.getId());
        if (nickName!=null) Objects.requireNonNull(u).setNickName(nickName);
        if (avatar!=null) Objects.requireNonNull(u).setAvatar(avatar);
        if (password!=null) Objects.requireNonNull(u).setPassword(nickName);
        if (lastName!=null) Objects.requireNonNull(u).setLastName(lastName);
        if (firstName!=null) Objects.requireNonNull(u).setFirstName(firstName);
        if (birthDate!=null) Objects.requireNonNull(u).setBirthDate(birthDate);
    }

    public String exportUserProfile(UUID userId) {
        User user = searchUserById(userId);
        if (user!=null){
            try {
                return fileHandle.serialize(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
