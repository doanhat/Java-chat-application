package common.interfaces.client;

import common.shared_data.User;
import common.shared_data.UserLite;

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
