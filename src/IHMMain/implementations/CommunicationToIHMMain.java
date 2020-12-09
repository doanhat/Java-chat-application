package IHMMain.implementations;

import IHMMain.IHMMainController;
import IHMMain.controllers.IHMMainWindowController;
import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.UserLite;
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
         * N'étant pas sur le thread principal il faut execute le load plus tard
         */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.getMainWindowController().loadIHMMainWindow();
            }
        });
    }

    @Override
    public void setConnectionStatus(int status) {
        /**
         * N'étant pas sur le thread principal il faut execute le load plus tard
         */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.loadIHMMainWindow(status);
            }
        });
    }

    @Override
    public void setConnectedUsers(List<UserLite> users) {
        Platform.runLater(() -> ihmMainController.getConnectedUsers().setAll(users));
    }

    @Override
    public void addConnectedUser(UserLite user) {
        Platform.runLater(() -> ihmMainController.getConnectedUsers().add(user));
    }

    @Override
    public void removeConnectedUser(UserLite user) {
        Platform.runLater(() -> ihmMainController.getConnectedUsers().remove(user));
    }

    @Override
    public void channelCreated(Channel channel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.getVisibleChannels().add(channel);
                ihmMainController.getMainWindowController().getIHMMainWindowController().viewChannel(channel);
            }
        });
    }


    @Override
    public void channelAdded(Channel channel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.getVisibleChannels().add(channel);
            }
        });
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController){
        this.ihmMainWindowController = ihmMainWindowController;
    }
}
