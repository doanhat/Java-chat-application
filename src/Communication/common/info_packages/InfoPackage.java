package Communication.common.info_packages;

import common.shared_data.UserLite;

import java.io.Serializable;
import java.util.UUID;

public class InfoPackage implements Serializable {
    public UserLite user;
    public UUID channelID;
    public String nickname;
}
