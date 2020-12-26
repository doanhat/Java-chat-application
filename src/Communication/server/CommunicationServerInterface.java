package Communication.server;

import Communication.common.ChannelOperation;
import Communication.common.Parameters;
import Communication.common.info_packages.BanUserPackage;
import Communication.messages.client_to_server.channel_operation.ChannelOperationMessage;
import Communication.messages.server_to_client.channel_operation.proprietary_channels.InformOwnerChannelOperationMessage;
import common.interfaces.server.IServerDataToCommunication;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.UserLite;

public class CommunicationServerInterface implements IServerDataToCommunication {

    private final CommunicationServerController commController;

    public CommunicationServerInterface(CommunicationServerController commServerController) {
        this.commController = commServerController;
    }

    @Override
    public void setIP(String addressIP) {
        Parameters.SERVER_IP = addressIP;
    }

    @Override
    public void setPort(int port) {
        Parameters.PORT = port;
    }

    @Override
    public String getIP() {
        return Parameters.SERVER_IP;
    }

    @Override
    public int getPort() {
        return Parameters.PORT;
    }

    @Override
    public void start() {
        commController.start();
    }

    @Override
    public void stop() {
        commController.stop();
    }

    @Override
    public void informUsersBanRemoved(Channel channel, UserLite unbannedUser) {
        if (channel == null || unbannedUser == null) {
            return;
        }

        // init unban sequence
        BanUserPackage banUserPackage = new BanUserPackage();
        banUserPackage.userToBan = unbannedUser;
        banUserPackage.channelID = channel.getId();

        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(channel.getCreator().getId(), new InformOwnerChannelOperationMessage(ChannelOperation.UNBAN_USER, banUserPackage));
        }
        else {
            commController.handleChannelOperation(ChannelOperation.UNBAN_USER, banUserPackage);
        }
    }
}
