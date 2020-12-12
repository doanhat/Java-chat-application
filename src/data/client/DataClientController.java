package data.client;

import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;

public class DataClientController {
    private final ChannelController channelCtrl;
    private final UserController userCtrl;
    private final MessageController messageCtrl;

    private CommunicationToData commToData;
    private IHMChannelToData ihmChannelToData;
    private IHMMainToData ihmMainToData;


    public DataClientController(
            IDataToCommunication comInterface,
            IDataToIHMChannel channelInterface,
            IDataToIHMMain mainInterface) {
        this.channelCtrl = new ChannelController(comInterface,channelInterface,mainInterface);
        this.userCtrl = new UserController(comInterface,channelInterface,mainInterface);
        this.messageCtrl = new MessageController(comInterface,channelInterface,mainInterface);

        commToData = new CommunicationToData(this);
        ihmChannelToData = new IHMChannelToData(this);
        ihmMainToData = new IHMMainToData(this);
    }

    public void setCommToData(CommunicationToData commToData) {
        this.commToData = commToData;
    }

    public void setIhmChannelToData(IHMChannelToData ihmChannelToData) {
        this.ihmChannelToData = ihmChannelToData;
    }

    public void setIhmMainToData(IHMMainToData ihmMainToData) {
        this.ihmMainToData = ihmMainToData;
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

    public CommunicationToData getCommToData() {return commToData;}
    public IHMChannelToData getIhmChannelToData() {return ihmChannelToData;}
    public IHMMainToData getIhmMainToData() {return ihmMainToData;}
}
