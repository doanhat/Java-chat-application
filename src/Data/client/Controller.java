package Data.client;

import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;

public class Controller {
    protected final IDataToCommunication comClient;
    protected final IDataToIHMChannel channelClient;
    protected final IDataToIHMMain mainClient;

    public Controller(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        this.comClient = comClient;
        this.channelClient = channelClient;
        this.mainClient = mainClient;
    }

    public IDataToCommunication getComClient() {
        return comClient;
    }

    public IDataToIHMChannel getChannelClient() {
        return channelClient;
    }

    public IDataToIHMMain getMainClient() {
        return mainClient;
    }
}
