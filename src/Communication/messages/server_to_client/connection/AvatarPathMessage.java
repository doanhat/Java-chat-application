package Communication.messages.server_to_client.connection;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

public class AvatarPathMessage extends ServerToClientMessage {
    private static final long     serialVersionUID = 1612256794569567L;
    private final        UserLite user;
    private final        String   path;

    public AvatarPathMessage(UserLite user, String path) {
        this.user = user;
        this.path = path;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.mainHandler().receiveAvatarPath(user, path);
    }
}
