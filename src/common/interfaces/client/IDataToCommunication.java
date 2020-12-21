package common.interfaces.client;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.List;
import java.util.UUID;

public interface IDataToCommunication
{
    /**
     * Connection utilisateur local
     *
     **/
    void userConnect(UserLite user);
}
