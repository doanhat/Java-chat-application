package data.client;

import common.interfaces.client.IIHMChannelToData;
import common.shared_data.User;
import common.shared_data.UserLite;

import java.util.UUID;

public class IHMChannelToData implements IIHMChannelToData {

    private DataClientController dataController;

    public IHMChannelToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @Override
    public UserLite getUser(UUID id) {
        return null;
    }

    @Override
    public User getLocalUser() {return dataController.getUserController().getUser();}
}
