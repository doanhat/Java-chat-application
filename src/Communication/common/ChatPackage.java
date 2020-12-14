package Communication.common;

import common.shared_data.Message;
import common.shared_data.UserLite;

import java.io.Serializable;
import java.util.UUID;

public class ChatPackage implements Serializable {
    public UserLite user;
    public UUID channelID;
    public Message message;
    public Message messageResponseTo;
    public Message editedMessage;
    public String nickname;
}
