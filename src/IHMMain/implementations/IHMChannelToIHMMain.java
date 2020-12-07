package IHMMain.implementations;

import IHMMain.IHMMainController;
import common.interfaces.client.IIHMChannelToIHMMain;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.ArrayList;
import java.util.List;

public class IHMChannelToIHMMain implements IIHMChannelToIHMMain {

    private IHMMainController ihmMainController;

    public IHMChannelToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void redirectToHomePage() {
        ihmMainController.getMainWindowController().getIHMMainWindowController().loadUserListView();
    }

    @Override
    public void closeChannel(Channel channel) {
        ihmMainController.getVisibleChannels().remove(channel);
    }

    @Override
    public void setOpenedChannelsList(List<Channel> openedChannels) {
        ihmMainController.getOpenedChannels().setAll(openedChannels);
    }

    @Override
    public void setCurrentVisibleChannel(Channel channel) {
        ihmMainController.getMainWindowController().getIHMMainWindowController().setViewChannelSelected(channel);
    }

    @Override
    public List<UserLite> getConnectedUsersList() {
        return ihmMainController.getConnectedUsers();
    }
}
