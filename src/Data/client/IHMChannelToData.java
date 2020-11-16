package Data.client;

import common.interfaces.client.IIHMChannelToData;

public class IHMChannelToData implements IIHMChannelToData {
    private DataClientController dataController;

    public IHMChannelToData() {
        dataController = new DataClientController();
    }
}
