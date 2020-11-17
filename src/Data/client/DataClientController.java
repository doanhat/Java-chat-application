package Data.client;

import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;

public class DataClientController {
    private final ChannelController channelCtrl;
    private final UserController userCtrl;
    private final MessageController messageCtrl;

    public DataClientController(
            IDataToCommunication comInterface,
            IDataToIHMChannel channelInterface,
            IDataToIHMMain mainInterface) {
        this.channelCtrl = new ChannelController();
        this.userCtrl = new UserController();
        this.messageCtrl = new MessageController(comInterface,channelInterface,mainInterface);
    }

    public ChannelController getChannelController() {
        return this.channelCtrl;
    }

    public UserController getUserController() {
        return this.userCtrl;
    }

    public MessageController getMessageController() {
        return this.messageCtrl;
    }

}
