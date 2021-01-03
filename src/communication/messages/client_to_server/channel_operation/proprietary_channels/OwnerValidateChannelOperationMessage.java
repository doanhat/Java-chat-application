package communication.messages.client_to_server.channel_operation.proprietary_channels;

import communication.common.ChannelOperation;
import communication.common.info_packages.InfoPackage;
import communication.messages.abstracts.ClientToServerMessage;
import communication.server.CommunicationServerController;

public class OwnerValidateChannelOperationMessage extends ClientToServerMessage {

    private static final long             serialVersionUID = 5135287100487841L;
    private final        ChannelOperation operation;
    private final        InfoPackage      infoPackage;

    /**
     * Message d'un proprietaire vers le serveur validant l'enregistrement d'une opération
     * @param operation Opération effectuée
     * @param infoPackage   Contenu de l'opération
     */
    public OwnerValidateChannelOperationMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation   = operation;
        this.infoPackage = infoPackage;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.handleChannelOperation(operation, infoPackage);
    }
}
