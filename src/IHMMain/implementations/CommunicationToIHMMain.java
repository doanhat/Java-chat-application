package IHMMain.implementations;

import IHMMain.IHMMainController;
import common.interfaces.client.ICommunicationToIHMMain;
import common.shared_data.Channel;
import common.shared_data.ConnectionStatus;
import common.shared_data.User;
import common.shared_data.UserLite;
import javafx.application.Platform;

import java.util.List;
import java.util.UUID;

public class CommunicationToIHMMain implements ICommunicationToIHMMain {

    private IHMMainController ihmMainController;

    public CommunicationToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void setConnectionStatus(ConnectionStatus status) {
        /**
         * N'étant pas sur le thread principal il faut execute le load plus tard
         */
        Platform.runLater(() -> ihmMainController.loadIHMMainWindow(status));
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
        Platform.runLater(() -> {
            ihmMainController.getVisibleChannels().add(channel);
            ihmMainController.getMainWindowController().getIHMMainWindowController().viewChannel(channel);
        });
    }

    @Override
    public void channelAdded(Channel channel) {
        Platform.runLater(() -> ihmMainController.getVisibleChannels().add(channel));
    }

    @Override
    public void channelAddedAll(List<Channel> channels) {
        Platform.runLater(() -> ihmMainController.getVisibleChannels().addAll(channels));
    }

    @Override
    public void channelConnectedUsers(UUID channelID, List<UserLite> connectedUsers) {
        Platform.runLater(() -> ihmMainController.getConnectedUserByChannels().put(channelID,connectedUsers));
    }
}
