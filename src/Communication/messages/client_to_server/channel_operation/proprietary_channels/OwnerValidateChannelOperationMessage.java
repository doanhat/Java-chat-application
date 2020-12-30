package Communication.messages.client_to_server.channel_operation.proprietary_channels;

import Communication.common.ChannelOperation;
import Communication.common.info_packages.InfoPackage;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;

public class OwnerValidateChannelOperationMessage extends ClientToServerMessage {

    private static final long             serialVersionUID = 5135287100487841L;
    private final        ChannelOperation operation;
    private final        InfoPackage      infoPackage;

    public OwnerValidateChannelOperationMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation   = operation;
        this.infoPackage = infoPackage;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.handleChannelOperation(operation, infoPackage);
    }
}
