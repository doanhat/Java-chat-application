package Data.client;

import common.interfaces.client.IIHMMainToData;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.Date;
import java.util.List;

public class IHMMainToData implements IIHMMainToData {
    @Override
    public List<UserLite> getConnectedUsers() {
        return null;
    }

    @Override
    public List<Channel> getChannels() {
        return null;
    }

    @Override
    public void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner) {

    }

    @Override
    public List<Channel> searchChannel(String name, UserLite creator, String description, Visibility visibility) {
        return null;
    }

    @Override
    public void editProfile(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate, User user) {

    }

    @Override
    public void localAuthentification(String nickName, String password) {

    }

    @Override
    public void createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {

    }
}
