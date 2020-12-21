package data.client;

import common.interfaces.client.IIHMMainToData;
import common.shared_data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class IHMMainToData implements IIHMMainToData {

    private DataClientController dataController;

    public IHMMainToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }

    @Override
    public void disconnect() {

    }

    /**
     * Gets connected users.
     *
     * @return the connected users
     */
    @Override
    public List<UserLite> getConnectedUsers() {
        return this.dataController.getUserController().getConnectedUsers();
    }

    /**
     * Gets channels.
     *
     * @return the channels
     */
    @Override
    public List<Channel> getChannels() {
        return this.dataController.getChannelController().getChannelList();
    }

    /**
     * Create channel.
     *
     * @param name  the channel name
     * @param isShared the is shared
     * @param isPublic the is public
     * @param owner    the owner
     */
    @Override
    public void createChannel(String name, String description, boolean isShared, boolean isPublic, UserLite owner) {
        Channel channel;
        channel = new Channel(name, owner, description, isPublic ? Visibility.PUBLIC : Visibility.PRIVATE, isShared ? ChannelType.SHARED : ChannelType.OWNED);
        this.dataController.getChannelController().createChannel(channel);
    }

    /**
     * Search channel.
     *
     * @param name        the name
     * @param creator     the creator
     * @param description the description
     * @param visibility  the visibility
     */
    @Override
    public List<Channel> searchChannel(String name, UserLite creator, String description, Visibility visibility) {
        return new ArrayList<>();
    }

    /**
     * Edit profile.
     *
     * @param user    the user
     * @param nickName  the nick name
     * @param avatar    the avatar
     * @param password  the password
     * @param lastName  the last name
     * @param firstName the first name
     * @param birthDate the birth date
     */
    @Override
    public void editProfile(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate, User user) {
        dataController.getUserController().editProfile(user,nickName,avatar,password,lastName,firstName,birthDate);
    }

    /**
     * Local authentification.
     *
     * @param nickName the pseudo
     * @param password the password
     */
    @Override
    public boolean localAuthentification(String nickName, String password) {
        UserController userController = dataController.getUserController();
        return userController.verificationAccount(nickName,password);
    }

    /**
     * Create account.
     *
     * @param nickName  the nick name
     * @param avatar    the avatar
     * @param password  the password
     * @param lastName  the last name
     * @param firstName the first name
     * @param birthDate the birth date
     */
    @Override
    public boolean createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {
        return dataController.getUserController().createAccount(nickName,avatar,password,lastName,firstName,birthDate);
    }

    // TODO UPGRATE
    @Override
    public User getUser() {
        return dataController.getUserController().getUser();
    }

    @Override
    public String exportUserProfile(UUID userId) {
        return dataController.getUserController().exportUserProfile(userId);
    }
}
