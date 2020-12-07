package common.interfaces.client;

import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.UUID;

public interface IIHMChannelToData {
    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    UserLite getUser(UUID id);

    User getLocalUser();
}
