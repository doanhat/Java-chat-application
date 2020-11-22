package IHMMain;

import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class CommunicationToIHMMain implements ICommunicationToIHMMain {

    private IHMMainController ihmMainController;

    private IHMMainWindowController ihmMainWindowController;

    @Override
    public void connectionAccepted() {
        ihmMainWindowController.getMainWindowController().loadIHMMainWindow();
    }

    @Override
    public void setConnectedUsers(List<UserLite> users) {
        ihmMainController.setConnectedUsers(users);
    }

    @Override
    public void addConnectedUser(UserLite user) {
        ihmMainController.getConnectedUsers().add(user);
    }

    @Override
    public void removeConnectedUser(UserLite user) {
        ihmMainController.getConnectedUsers().remove(user);
    }

    @Override
    public void channelCreated(Channel channel) {
        ihmMainWindowController.addChannel(channel);
        //TODO : décommenter cela lorsque le code sera merge pour pouvoir load le channel dans la vue
        //ihmMainWindowController.loadIHMChannelWindow(channel);
    }


    @Override
    public void channelAdded(Channel channel) {
        ihmMainWindowController.addChannel(channel);
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController){
        this.ihmMainWindowController = ihmMainWindowController;
    }
}
