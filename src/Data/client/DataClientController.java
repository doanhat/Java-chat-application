package Data.client;

public class DataClientController {
    private final ChannelController channelCtrl;
    private final UserController userCtrl;
    private final MessageController messageCtrl;

    public DataClientController() {
        this.channelCtrl = new ChannelController();
        this.userCtrl = new UserController();
        this.messageCtrl = new MessageController();
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
