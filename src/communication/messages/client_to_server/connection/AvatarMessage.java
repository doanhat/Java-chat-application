package communication.messages.client_to_server.connection;

import communication.messages.abstracts.ClientToServerMessage;
import communication.messages.server_to_client.connection.AvatarPathMessage;
import communication.server.CommunicationServerController;
import common.shared_data.UserLite;

public class AvatarMessage extends ClientToServerMessage {
    private static final long      serialVersionUID = -19653314074993L;
    private final        Operation operation;
    private final        UserLite  sender;
    private final        UserLite  user;
    private              String    encodedString;

    public AvatarMessage(Operation operation, UserLite user, UserLite sender, String encodedString) {
        this.operation     = operation;
        this.sender        = sender;
        this.user          = user;
        this.encodedString = encodedString;
    }

    public AvatarMessage(Operation operation, UserLite sender, UserLite user) {
        this.operation = operation;
        this.sender    = sender;
        this.user      = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        switch (operation) {
            case PUT:
                commController.setAvatar(user, encodedString);
                break;
            case GET:
                commController.sendMessage(sender.getId(),
                                           new AvatarPathMessage(user, commController.getAvatarPath(user)));
        }
    }

    public enum Operation {
        PUT,
        GET
    }
}
