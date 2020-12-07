package IHMMain.implementations;

import IHMMain.IHMMainController;
import IHMMain.controllers.IHMMainWindowController;
import common.interfaces.client.ICommunicationToIHMMain;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.application.Platform;

import java.util.List;

public class CommunicationToIHMMain implements ICommunicationToIHMMain {

    private IHMMainController ihmMainController;

    private IHMMainWindowController ihmMainWindowController;

    public CommunicationToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void connectionAccepted() {
        /**
         * N'étant pas sur le threadprincipal il faut execute le load plus tard
         */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainWindowController = ihmMainController.getMainWindowController().getIHMMainWindowController();
                ihmMainWindowController.getMainWindowController().loadIHMMainWindow();
            }
        });
    }

    @Override
    public void setConnectedUsers(List<UserLite> users) {
        ihmMainController.getConnectedUsers().setAll(users);
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
        ihmMainController.getVisibleChannels().add(channel);
        ihmMainWindowController.loadIHMChannelWindow(channel);
    }


    @Override
    public void channelAdded(Channel channel) {
        ihmMainController.getVisibleChannels().add(channel);
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController){
        this.ihmMainWindowController = ihmMainWindowController;
    }
}
