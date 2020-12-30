package Communication.messages.server_to_client.channel_operation.proprietary_channels;

import Communication.client.CommunicationClientController;
import Communication.common.ChannelOperation;
import Communication.common.info_packages.InfoPackage;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.channel_operation.proprietary_channels.OwnerValidateChannelOperationMessage;

public class InformOwnerChannelOperationMessage extends ServerToClientMessage {

    private static final long             serialVersionUID = -287745780048758221L;
    private final        ChannelOperation operation;
    private final        InfoPackage      infoPackage;

    public InformOwnerChannelOperationMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation   = operation;
        this.infoPackage = infoPackage;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().saveChat(operation, infoPackage);

        // Send Confirmation back to Server
        commController.sendMessage(new OwnerValidateChannelOperationMessage(operation, infoPackage));
    }
}
