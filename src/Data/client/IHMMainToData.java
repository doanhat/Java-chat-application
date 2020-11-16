package Data.client;

import common.interfaces.client.IIHMMainToData;
import common.sharedData.*;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

public class IHMMainToData implements IIHMMainToData {
    private DataClientController dataController;

    public IHMMainToData() {
        dataController = new DataClientController();
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
        return this.dataController.getChannelController().getChannels();
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
    public void createChannel(String name, String description, Boolean isShared, Boolean isPublic, UserLite owner) {
        Channel channel;
        UID id = new UID();
        if(isShared) {
            channel = new SharedChannel(id, name, owner, description, isPublic ? Visibility.PUBLIC : Visibility.PRIVATE);
        } else {
            channel = new OwnedChannel(id, name, owner, description, isPublic ? Visibility.PUBLIC : Visibility.PRIVATE);
        }
    }

    /**
     * Edit profile.
     *
     * @param options the options
     * @param user    the user
     */
    @Override
    public void editProfile(String[] options, User user) {

    }
}
