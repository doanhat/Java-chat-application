package data.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.User;
import common.shared_data.UserLite;

import java.util.*;


public class UserController extends Controller {
    public static final String FILENAME = "users";
    private DataClientController dataController;
    private User localUser;
    private List<User> localUserList;
    private FileHandle<User> fileHandleClient;

    public UserController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient, DataClientController controller) {
        super(comClient, channelClient, mainClient);
        dataController = controller;
        fileHandleClient = new FileHandle<>(LocationType.CLIENT, FileType.USER);
        localUserList = fileHandleClient.readJSONFileToList(FILENAME,User.class);
    }

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
            for (User user : localUserList){
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
        channelClient.userBanCancelledNotification(user, channelId);
    }


    /**
     * Get all connected users
     *
     * @return List<UserLite> connected users
     */
    public List<UserLite> getConnectedUsers() {
        return new ArrayList<>();
    }

    public boolean createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
        User user = new User(nickName,avatar,password,lastName,firstName,birthDate);
        addUserToLocalUsers(user);
        fileHandleClient.addObjectToFile(FILENAME,user,User.class);
        return true;
    }

    private void addUserToLocalUsers(User user) {
        localUserList.removeIf(u -> u.getId().equals(user.getId()));
        localUserList.add(user);
    }

    public User searchUserById(UUID userId){
        for (User u : localUserList){
            if (u.getId().equals(userId)){
                return u;
            }
        }
        return null;
    }
    public void editProfile(User user, String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
        if (nickName!=null) Objects.requireNonNull(user).setNickName(nickName);
        if (avatar!=null) Objects.requireNonNull(user).setAvatar(avatar);
        if (password!=null) Objects.requireNonNull(user).setPassword(password);
        if (lastName!=null) Objects.requireNonNull(user).setLastName(lastName);
        if (firstName!=null) Objects.requireNonNull(user).setFirstName(firstName);
        if (birthDate!=null) Objects.requireNonNull(user).setBirthDate(birthDate);
        fileHandleClient.writeJSONToFile(FILENAME, localUserList);
    }

    public String exportUserProfile(UUID userId) {
        User user = searchUserById(userId);
        if (user!=null){
            try {
                return fileHandleClient.serialize(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
